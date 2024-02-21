package com.flowiee.app.service.impl;

import com.flowiee.app.entity.*;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.repository.MaterialTempRepository;
import com.flowiee.app.repository.ProductVariantTempRepository;
import com.flowiee.app.service.MaterialService;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.repository.TicketImportRepository;
import com.flowiee.app.service.TicketImportService;

import com.flowiee.app.utils.MessageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketImportServiceImpl implements TicketImportService {
    @Autowired private TicketImportRepository ticketImportRepo;
    @Autowired private ProductService productService;
    @Autowired private ProductVariantTempRepository productVariantTempRepo;
    @Autowired private MaterialService materialService;
    @Autowired private MaterialTempRepository materialTempRepo;

    @Override
    public List<TicketImport> findAll() {
        return ticketImportRepo.findAll();
    }

    @Override
    public Page<TicketImport> findAll(int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        return ticketImportRepo.findAll(pageable);
    }

    @Override
    public List<TicketImport> findAll(String text, Integer supplierId, Integer paymentMethod, String payStatus, String importStatus) {
        return ticketImportRepo.findAll(text, supplierId, paymentMethod, payStatus, importStatus);
    }

    @Override
    public TicketImport findById(Integer entityId) {
        return ticketImportRepo.findById(entityId).orElse(null);
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
        TicketImport ticketImportToUpdate = this.findById(entityId);
        if (ticketImportToUpdate == null) {
            throw new BadRequestException();
        }
        if ("COMPLETED".equals(ticketImportToUpdate.getStatus()) || "CANCEL".equals(ticketImportToUpdate.getStatus())) {
            throw new BadRequestException(MessageUtils.ERROR_DATA_LOCKED);
        }
        ticketImport.setId(entityId);
        TicketImport ticketImportUpdated = ticketImportRepo.save(ticketImport);
        if (ObjectUtils.isNotEmpty(ticketImport.getListProductVariantTemp())) {
            for (ProductVariantTemp p : ticketImport.getListProductVariantTemp()) {
                productService.updateProductVariantQuantity(p.getQuantity(), p.getProductVariantId(), "I");
            }
        }
        if (ObjectUtils.isNotEmpty(ticketImport.getListMaterialTemp())) {
            for (MaterialTemp m : ticketImport.getListMaterialTemp()) {
                materialService.updateQuantity(m.getQuantity(), m.getMaterialId(), "I");
            }
        }
        return ticketImportUpdated;
    }

    @Override
    public String delete(Integer entityId) {
        try {
            ticketImportRepo.deleteById(entityId);
            return MessageUtils.DELETE_SUCCESS;
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "ticket import"), ex);
            throw new AppException();
        }
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
        ticketImport.setCreatedBy(CommonUtils.getCurrentAccountId());
        ticketImport.setImporter(CommonUtils.getCurrentAccountUsername());
        ticketImport.setImportTime(new Date());
        ticketImport = ticketImportRepo.save(ticketImport);
        return ticketImport;
    }

    @Override
    public TicketImport updateStatus(Integer entityId, String status) {
        if (entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        TicketImport ticketImport = this.findById(entityId);
        if (ticketImport == null) {
            throw new BadRequestException();
        }
        ticketImport.setStatus(status);
        return ticketImportRepo.save(ticketImport);
    }

    @Override
    public List<ProductVariantTemp> addProductToTicket(Integer ticketImportId, List<Integer> productVariantIds) {
        List<ProductVariantTemp> listAdded = new ArrayList<>();
        for (Integer productVariantId : productVariantIds) {
            ProductVariant productVariant =  productService.findProductVariantById(productVariantId);
            if (ObjectUtils.isEmpty(productVariant)) {
                continue;
            }
            ProductVariantTemp temp = productVariantTempRepo.findProductVariantInGoodsImport(ticketImportId, productVariant.getId());
            if (temp != null) {
                productVariantTempRepo.updateQuantityIncrease(temp.getId(), 1);
            } else {
                ProductVariantTemp productVariantTemp = new ProductVariantTemp();
                productVariantTemp.setTicketImport(new TicketImport(ticketImportId));
                productVariantTemp.setProductVariantId(productVariantId);
                productVariantTemp.setName(productVariant.getTenBienThe());
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
            Material material = materialService.findById(materialId);
            if (ObjectUtils.isEmpty(material)) {
                continue;
            }
            MaterialTemp temp = materialTempRepo.findMaterialInGoodsImport(ticketImportId, material.getId());
            if (temp != null) {
                materialTempRepo.updateQuantityIncrease(temp.getId(), 1);
            } else {
                MaterialTemp materialTemp = new MaterialTemp();
                materialTemp.setTicketImport(new TicketImport(ticketImportId));
                materialTemp.setMaterialId(materialId);
                materialTemp.setName(material.getName());
                materialTemp.setQuantity(1);
                materialTemp.setNote(null);
                MaterialTemp materialTempAdded = materialTempRepo.save(materialTemp);
                listAdded.add(materialTempAdded);
            }
        }
        return listAdded;
    }
}