package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.entity.product.Material;
import com.flowiee.pms.entity.product.MaterialTemp;
import com.flowiee.pms.entity.sales.TicketImport;
import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.AccountRole;
import com.flowiee.pms.entity.system.GroupAccount;
import com.flowiee.pms.entity.system.Notification;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.repository.sales.MaterialTempRepository;
import com.flowiee.pms.repository.product.ProductDetailTempRepository;
import com.flowiee.pms.service.product.MaterialService;
import com.flowiee.pms.service.product.ProductQuantityService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.system.AccountService;
import com.flowiee.pms.service.system.GroupAccountService;
import com.flowiee.pms.service.system.NotificationService;
import com.flowiee.pms.service.system.RoleService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.repository.sales.TicketImportRepository;
import com.flowiee.pms.service.sales.TicketImportService;

import com.flowiee.pms.utils.MessageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketImportServiceImpl implements TicketImportService {
    @Autowired
    private TicketImportRepository ticketImportRepo;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private ProductDetailTempRepository productVariantTempRepo;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialTempRepository materialTempRepo;
    @Autowired
    private ProductQuantityService productQuantityService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private GroupAccountService groupAccountService;
    @Autowired
    private AccountService accountService;

    @Override
    public List<TicketImport> findAll() {
        return this.findAll(-1, -1, null, null, null,null, null).getContent();
    }

    @Override
    public Page<TicketImport> findAll(int pageSize, int pageNum, String pText, Integer pSupplierId, Integer pPaymentMethod, String pPayStatus, String pImportStatus) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        }
        return ticketImportRepo.findAll(pText, pSupplierId, pPaymentMethod, pPayStatus, pImportStatus, pageable);
    }

    @Override
    public Optional<TicketImport> findById(Integer entityId) {
        return ticketImportRepo.findById(entityId);
    }

    @Override
    public TicketImport save(TicketImport entity) {
        if (entity == null) {
            throw new BadRequestException();
        }
        TicketImport ticketImportSaved = ticketImportRepo.save(entity);
        Storage storage = ticketImportSaved.getStorage();
        if (storage.getHoldableQty() != null && storage.getHoldWarningPercent() != null) {
            int productQty = 0;
            int materialQty = 0;
            if (ObjectUtils.isNotEmpty(ticketImportSaved.getListProductVariantTemps())) {
                productQty = ticketImportSaved.getListProductVariantTemps().size();
            }
            if (ObjectUtils.isNotEmpty(ticketImportSaved.getListMaterialTemps())) {
                materialQty = ticketImportSaved.getListMaterialTemps().size();
            }
            int totalGoodsImport = productQty + materialQty;
            int totalGoodsHolding = 0;
            if ((totalGoodsImport + totalGoodsHolding) / storage.getHoldableQty() * 100 >= storage.getHoldWarningPercent()) {
                List<AccountRole> listOfStorageManagersRight = roleService.findByAction(ACTION.STG_STORAGE);
                if (ObjectUtils.isNotEmpty(listOfStorageManagersRight)) {
                    Set<Account> stgManagersReceiveNtfs = new HashSet<>();
                    for (AccountRole storageManagerRight : listOfStorageManagersRight) {
                        Optional<GroupAccount> groupAccount = groupAccountService.findById(storageManagerRight.getGroupId());
                        if (groupAccount.isPresent()) {
                            for (Account acc : groupAccount.get().getListAccount()) {
                                stgManagersReceiveNtfs.add(acc);
                            }
                        }
                        Optional<Account> account = accountService.findById(storageManagerRight.getAccountId());
                        if (account.isPresent()) {
                            stgManagersReceiveNtfs.add(account.get());
                        }
                    }
                    for (Account a : stgManagersReceiveNtfs) {
                        Notification ntf = new Notification();
                        ntf.setSend(0);
                        ntf.setReceive(a.getId());
                        ntf.setType("WARNING");
                        ntf.setTitle("Sức chứa của kho " + storage.getName() + " đã chạm mốc cảnh báo!");
                        ntf.setContent("Số lượng hàng hóa hiện tại " + totalGoodsHolding + ", Số lượng nhập thêm: " + totalGoodsImport + ", Số lượng sau khi nhập: " + totalGoodsImport + totalGoodsHolding + "/" + storage.getHoldableQty());
                        ntf.setReaded(false);
                        ntf.setImportId(ticketImportSaved.getId());
                        notificationService.save(ntf);
                    }
                }
            }
        }
        return ticketImportSaved;
    }

    @Override
    public TicketImport update(TicketImport ticketImport, Integer entityId) {
        Optional<TicketImport> ticketImportToUpdate = this.findById(entityId);
        if (ticketImportToUpdate.isEmpty()) {
            throw new BadRequestException();
        }
        if ("COMPLETED".equals(ticketImportToUpdate.get().getStatus()) || "CANCEL".equals(ticketImportToUpdate.get().getStatus())) {
            throw new BadRequestException(MessageUtils.ERROR_DATA_LOCKED);
        }
        ticketImport.setId(entityId);
        TicketImport ticketImportUpdated = ticketImportRepo.save(ticketImport);
        if ("COMPLETED".equals(ticketImportUpdated.getStatus())) {
            if (ObjectUtils.isNotEmpty(ticketImport.getListProductVariantTemps())) {
                for (ProductVariantTemp p : ticketImport.getListProductVariantTemps()) {
                    productQuantityService.updateProductVariantQuantityIncrease(p.getQuantity(), p.getProductVariantId());
                }
            }
            if (ObjectUtils.isNotEmpty(ticketImport.getListMaterialTemps())) {
                for (MaterialTemp m : ticketImport.getListMaterialTemps()) {
                    materialService.updateQuantity(m.getQuantity(), m.getMaterialId(), "I");
                }
            }
        }
        return ticketImportUpdated;
    }

    @Override
    public String delete(Integer entityId) {
        if (this.findById(entityId).isEmpty()) {
            throw new BadRequestException("Ticket import not found!");
        }
        ticketImportRepo.deleteById(entityId);
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public TicketImport findDraftImportPresent(Integer createdBy) {
        return ticketImportRepo.findDraftGoodsImportPresent("DRAFT", createdBy);
    }

    @Override
    public TicketImport createDraftTicketImport(String title) {
        TicketImport ticketImport = new TicketImport();
        ticketImport.setTitle(title);
        ticketImport.setStatus("DRAFT");
        ticketImport.setCreatedBy(CommonUtils.getUserPrincipal().getId());
        ticketImport.setImporter(CommonUtils.getUserPrincipal().getUsername());
        ticketImport.setImportTime(new Date());
        return this.save(ticketImport);
    }

    @Override
    public TicketImport updateStatus(Integer entityId, String status) {
        if (entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        Optional<TicketImport> ticketImport = this.findById(entityId);
        if (ticketImport.isEmpty()) {
            throw new BadRequestException();
        }
        ticketImport.get().setStatus(status);
        return ticketImportRepo.save(ticketImport.get());
    }

    @Override
    public List<ProductVariantTemp> addProductToTicket(Integer ticketImportId, List<Integer> productVariantIds) {
        List<ProductVariantTemp> listAdded = new ArrayList<>();
        for (Integer productVariantId : productVariantIds) {
            Optional<ProductVariantDTO> productDetail =  productVariantService.findById(productVariantId);
            if (productDetail.isEmpty()) {
                continue;
            }
            ProductVariantTemp temp = productVariantTempRepo.findProductVariantInGoodsImport(ticketImportId, productDetail.get().getId());
            if (temp != null) {
                productVariantTempRepo.updateQuantityIncrease(temp.getId(), 1);
            } else {
                ProductVariantTemp productVariantTemp = new ProductVariantTemp();
                productVariantTemp.setTicketImport(new TicketImport(ticketImportId));
                productVariantTemp.setProductVariantId(productVariantId);
                productVariantTemp.setQuantity(1);
                productVariantTemp.setNote(null);
                ProductVariantTemp productVariantTempAdded = productVariantTempRepo.save(productVariantTemp);
                listAdded.add(productVariantTempAdded);
            }
        }
        return listAdded;
    }

    @Override
    public List<MaterialTemp> addMaterialToTicket(Integer ticketImportId, List<Integer> materialIds) {
        List<MaterialTemp> listAdded = new ArrayList<>();
        for (Integer materialId : materialIds) {
            Optional<Material> material = materialService.findById(materialId);
            if (material.isEmpty()) {
                continue;
            }
            MaterialTemp temp = materialTempRepo.findMaterialInGoodsImport(ticketImportId, material.get().getId());
            if (temp != null) {
                materialTempRepo.updateQuantityIncrease(temp.getId(), 1);
            } else {
                MaterialTemp materialTemp = new MaterialTemp();
                materialTemp.setTicketImport(new TicketImport(ticketImportId));
                materialTemp.setMaterialId(materialId);
                materialTemp.setQuantity(1);
                materialTemp.setNote(null);
                MaterialTemp materialTempAdded = materialTempRepo.save(materialTemp);
                listAdded.add(materialTempAdded);
            }
        }
        return listAdded;
    }
}