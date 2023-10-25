package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.sanpham.entity.GoodsImport;
import com.flowiee.app.sanpham.repository.GoodsImportRepository;
import com.flowiee.app.sanpham.services.GoodsImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsImportServiceImpl implements GoodsImportService {
    @Autowired
    private GoodsImportRepository goodsImportRepository;
    @Autowired
    private EntityManager entityManager;

    private static String STATUS_DRAFT = "DRAFT";
    private static String STATUS_APPROVING = "APPROVING";
    private static String STATUS_APPROVED = "APPROVED";
    private static String STATUS_REJECT = "REJECT";

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
    public List<GoodsImport> search(Integer materialId, Integer supplierId) {
        List<GoodsImport> listData = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
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
        goodsImport.setStatus(STATUS_DRAFT);
        goodsImport.setCreatedBy(String.valueOf(FlowieeUtil.ACCOUNT_ID));
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