package com.flowiee.app.service.impl;

import com.flowiee.app.category.Category;
import com.flowiee.app.common.utils.DateUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.GoodsImport;
import com.flowiee.app.entity.Supplier;
import com.flowiee.app.model.product.GoodsImportRequest;
import com.flowiee.app.repository.product.GoodsImportRepository;
import com.flowiee.app.service.product.GoodsImportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsImportServiceImpl implements GoodsImportService {
    @Autowired
    private GoodsImportRepository goodsImportRepository;
    @Autowired
    private EntityManager entityManager;

    private static String STATUS_DRAFT = "DRAFT";
//    private static String STATUS_APPROVING = "APPROVING";
//    private static String STATUS_APPROVED = "APPROVED";
//    private static String STATUS_REJECT = "REJECT";

    @Override
    public List<GoodsImport> findAll() {
        return goodsImportRepository.findAll();
    }

    @Override
    public GoodsImport findById(Integer entityId) {
        return goodsImportRepository.findById(entityId).get();
    }

    @Override
    public String save(GoodsImport entity) {
        if (entity == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        goodsImportRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(GoodsImport entity, Integer entityId) {
        if (entity == null || entityId == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        if (entityId <= 0 || this.findById(entityId) == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        goodsImportRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        GoodsImport goodsImport = this.findById(entityId);
        if (goodsImport == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        goodsImportRepository.deleteById(entityId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }
;

    @Override
    public String saveDraft(GoodsImportRequest request) {
        if (request == null || request.getId() == null || request.getId() <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        GoodsImport goodsImport = this.findById(request.getId());
        if (goodsImport == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        goodsImport.setId(request.getId());
        goodsImport.setTitle(request.getTitle());
        if (request.getSupplierId() != null && request.getSupplierId() > 0) {
            goodsImport.setSupplier(new Supplier(request.getSupplierId(), null));
        }
        if (request.getPaymentMethodId() != null && request.getPaymentMethodId() > 0) {
            goodsImport.setPaymentMethod(new Category(request.getPaymentMethodId(), null));
        }
        if (request.getReceivedBy() != null && request.getReceivedBy() > 0) {
            goodsImport.setReceivedBy(new Account(request.getReceivedBy()));
        }
        goodsImport.setDiscount(request.getDiscount());
        goodsImport.setPaidAmount(request.getPaidAmount());
        goodsImport.setPaidStatus(request.getPaidStatus());
        if (request.getOrderTime() != null) {
            goodsImport.setOrderTime(request.getOrderTime());
        }
        if (request.getReceivedTime() != null) {
            goodsImport.setReceivedTime(request.getReceivedTime());
        }
        goodsImport.setNote(request.getNote());
        goodsImport.setStatus(STATUS_DRAFT);
        goodsImportRepository.save(goodsImport);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public List<GoodsImport> search(String text, Integer supplierId, Integer paymentMethod, String payStatus, String importStatus) {
        List<GoodsImport> listData = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i.ID, i.TITLE, s.NAME as SUPPLIER_NAME, pm.TEN_LOAI as PAYMENT_METHOD_NAME, i.PAY_STATUS, a.HO_TEN as RECEIVED_NAME, i.STATUS, ");
        sql.append("       s.ID as SUPPLIER_ID, pm.ID as PAYMENT_METHOD_ID, a.ID as RECEIVED_ID, ");
        sql.append("       i.ORDER_TIME, i.RECEIVED_TIME ");
        sql.append("FROM goods_import i ");
        sql.append("LEFT JOIN supplier s ON s.ID = i.SUPPLIER_ID ");
        sql.append("LEFT JOIN dm_hinh_thuc_thanh_toan pm ON pm.ID = i.PAYMENT_METHOD ");
        sql.append("LEFT JOIN account a ON a.ID = i.RECEIVED_BY ");
        sql.append("WHERE i.TITLE LIKE '%").append(text != null ? text : "").append("%' ");
        if (supplierId != null) {
            sql.append("AND s.ID = ? ");
        }
        if (paymentMethod != null) {
            sql.append("AND pm.ID = ? ");
        }
        if (payStatus != null) {
            sql.append("i.PAY_STATUS = ? ");
        }
        if (importStatus != null) {
            sql.append("i.STATUS = ?");
        }
        System.out.println(sql.toString());
        Query query = entityManager.createNativeQuery(sql.toString());
        int i = 1;
        if (supplierId != null) {
            query.setParameter(i++, supplierId);
        }
        if (paymentMethod != null) {
            query.setParameter(i++, paymentMethod);
        }
        if (payStatus != null) {
            query.setParameter(i++, payStatus);
        }
        if (importStatus != null) {
            query.setParameter(i++, importStatus);
        }
        @SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
        for (Object[] o : data) {
            GoodsImport goodsImport = new GoodsImport();
            goodsImport.setId(Integer.parseInt(o[0].toString()));
            goodsImport.setTitle(String.valueOf(o[1]));
            goodsImport.setSupplier(new Supplier(Integer.parseInt(String.valueOf(o[7])), String.valueOf(o[2])));
            goodsImport.setPaymentMethod(new Category(Integer.parseInt(String.valueOf(o[8])), String.valueOf(o[3])));
            goodsImport.setPaidStatus(String.valueOf(o[4]));
            goodsImport.setReceivedBy(new Account(Integer.parseInt(String.valueOf(o[9])), null, String.valueOf(o[5])));
            goodsImport.setStatus(String.valueOf(o[6]));
            goodsImport.setOrderTime(DateUtil.convertStringToDate(String.valueOf(o[10])));
            goodsImport.setReceivedTime(DateUtil.convertStringToDate(String.valueOf(o[11])));
            listData.add(goodsImport);
        }
        entityManager.close();
        return listData;
    }

    @Override
    public List<GoodsImport> findByMaterialId(Integer materialId) {
        List<GoodsImport> listData = new ArrayList<>();
        if (materialId != null && materialId >= 0) {
            //listData = goodsImportRepository.findByMaterialId(materialId);
        }
        return listData;
    }

    @Override
    public List<GoodsImport> findBySupplierId(Integer supplierId) {
        List<GoodsImport> listData = new ArrayList<>();
        if (supplierId != null && supplierId >= 0) {
            listData = goodsImportRepository.findBySupplierId(supplierId);
        }
        return listData;
    }

    @Override
    public List<GoodsImport> findByPaymentMethod(String paymentMethod) {
        List<GoodsImport> listData = new ArrayList<>();
        if (paymentMethod != null) {
            listData = goodsImportRepository.findByPaymentMethod(paymentMethod);
        }
        return listData;
    }

    @Override
    public List<GoodsImport> findByPaidStatus(String paidStatus) {
        List<GoodsImport> listData = new ArrayList<>();
        if (paidStatus != null && FlowieeUtil.getPaymentStatusCategory().containsKey(paidStatus)) {
            listData = goodsImportRepository.findByPaidStatus(paidStatus);
        }
        return listData;
    }

    @Override
    public List<GoodsImport> findByAccountId(Integer accountId) {
        List<GoodsImport> listData = new ArrayList<>();
        if (accountId != null && accountId > 0) {
            listData = goodsImportRepository.findByReceiveBy(accountId);
        }
        return listData;
    }

    @Override
    public GoodsImport findDraftImportPresent(Integer createdBy) {
        return goodsImportRepository.findDraftGoodsImportPresent(STATUS_DRAFT, String.valueOf(createdBy));
    }

    @Override
    public GoodsImport createDraftImport() {
        GoodsImport goodsImport = new GoodsImport();
        goodsImport.setTitle("Title");
        goodsImport.setStatus(STATUS_DRAFT);
        goodsImport.setCreatedBy(FlowieeUtil.ACCOUNT_ID);
        goodsImport = goodsImportRepository.save(goodsImport);
        return goodsImport;
    }

    @Override
    public String updateStatus(Integer entityId, String status) {
        if (entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        GoodsImport goodsImport = this.findById(entityId);
        if (goodsImport == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        goodsImport.setStatus(status);
        goodsImportRepository.save(goodsImport);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }
}