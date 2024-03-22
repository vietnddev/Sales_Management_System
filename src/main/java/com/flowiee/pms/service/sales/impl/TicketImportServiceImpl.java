package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.entity.product.Material;
import com.flowiee.pms.entity.product.MaterialTemp;
import com.flowiee.pms.entity.sales.TicketImport;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.repository.sales.MaterialTempRepository;
import com.flowiee.pms.repository.product.ProductDetailTempRepository;
import com.flowiee.pms.service.product.MaterialService;
import com.flowiee.pms.service.product.ProductQuantityService;
import com.flowiee.pms.service.product.ProductVariantService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        return ticketImportRepo.save(entity);
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

//    @Override
//    public List<TicketImportGoods> search(String text, Integer supplierId, Integer paymentMethod, String payStatus, String importStatus) {
//        List<TicketImportGoods> listData = new ArrayList<>();
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT i.ID, i.TITLE, s.NAME as SUPPLIER_NAME, pm.TEN_LOAI as PAYMENT_METHOD_NAME, i.PAY_STATUS, a.FULLNAME as RECEIVED_NAME, i.STATUS, ");
//        sql.append("       s.ID as SUPPLIER_ID, pm.ID as PAYMENT_METHOD_ID, a.ID as RECEIVED_ID, ");
//        sql.append("       i.ORDER_TIME, i.RECEIVED_TIME ");
//        sql.append("FROM goods_import i ");
//        sql.append("LEFT JOIN supplier s ON s.ID = i.SUPPLIER_ID ");
//        sql.append("LEFT JOIN dm_hinh_thuc_thanh_toan pm ON pm.ID = i.PAYMENT_METHOD ");
//        sql.append("LEFT JOIN account a ON a.ID = i.RECEIVED_BY ");
//        sql.append("WHERE i.TITLE LIKE '%").append(text != null ? text : "").append("%' ");
//        if (supplierId != null) {
//            sql.append("AND s.ID = ? ");
//        }
//        if (paymentMethod != null) {
//            sql.append("AND pm.ID = ? ");
//        }
//        if (payStatus != null) {
//            sql.append("i.PAY_STATUS = ? ");
//        }
//        if (importStatus != null) {
//            sql.append("i.STATUS = ?");
//        }
//        System.out.println(sql.toString());
//        Query query = entityManager.createNativeQuery(sql.toString());
//        int i = 1;
//        if (supplierId != null) {
//            query.setParameter(i++, supplierId);
//        }
//        if (paymentMethod != null) {
//            query.setParameter(i++, paymentMethod);
//        }
//        if (payStatus != null) {
//            query.setParameter(i++, payStatus);
//        }
//        if (importStatus != null) {
//            query.setParameter(i++, importStatus);
//        }
//        @SuppressWarnings("unchecked")
//		List<Object[]> data = query.getResultList();
//        for (Object[] o : data) {
//            TicketImportGoods ticketImportGoods = new TicketImportGoods();
//            ticketImportGoods.setId(Integer.parseInt(o[0].toString()));
//            ticketImportGoods.setTitle(String.valueOf(o[1]));
//            ticketImportGoods.setSupplier(new Supplier(Integer.parseInt(String.valueOf(o[7])), String.valueOf(o[2])));
//            ticketImportGoods.setPaymentMethod(new Category(Integer.parseInt(String.valueOf(o[8])), String.valueOf(o[3])));
//            ticketImportGoods.setPaidStatus(String.valueOf(o[4]));
//            ticketImportGoods.setReceivedBy(new Account(Integer.parseInt(String.valueOf(o[9])), null, String.valueOf(o[5])));
//            ticketImportGoods.setStatus(String.valueOf(o[6]));
//            ticketImportGoods.setOrderTime(CommonUtil.convertStringToDate(String.valueOf(o[10])));
//            ticketImportGoods.setReceivedTime(CommonUtil.convertStringToDate(String.valueOf(o[11])));
//            listData.add(ticketImportGoods);
//        }
//        entityManager.close();
//        return listData;
//    }

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
        ticketImport = ticketImportRepo.save(ticketImport);
        return ticketImport;
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