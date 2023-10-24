package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.sanpham.entity.GoodsImport;
import com.flowiee.app.sanpham.repository.GoodsImportRepository;
import com.flowiee.app.sanpham.services.GoodsImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsImportServiceImpl implements GoodsImportService {
    @Autowired
    private GoodsImportRepository goodsImportRepository;

    private static String STATUS_DRAFT = "DRAFT";
    private static String STATUS_NONAPPROVE = "DRAFT";
    private static String STATUS_APPROVE = "DRAFT";

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
        return null;
    }

    @Override
    public String delete(Integer entityId) {
        return null;
    }

    @Override
    public List<GoodsImport> search(Integer materialId, Integer supplierId) {
        return null;
    }

    @Override
    public List<GoodsImport> findByMaterialId(Integer materialId) {
        return null;
    }

    @Override
    public List<GoodsImport> findBySupplierId(Integer supplierId) {
        return null;
    }

    @Override
    public List<GoodsImport> findByPaymentMethod(String paymentMethod) {
        return null;
    }

    @Override
    public List<GoodsImport> findByPaidStatus(String paidStatus) {
        return null;
    }

    @Override
    public List<GoodsImport> findByAccountId(Integer accountId) {
        return null;
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
}