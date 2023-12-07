package com.flowiee.app.service.impl;

import com.flowiee.app.dto.VoucherApplyDTO;
import com.flowiee.app.entity.VoucherApply;
import com.flowiee.app.repository.VoucherApplyRepository;
import com.flowiee.app.service.VoucherApplyService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherApplyServiceImpl implements VoucherApplyService {
    @Autowired
    private VoucherApplyRepository voucherApplyRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<VoucherApplyDTO> findAll(Integer voucherInfoId , Integer productId) {
        List<VoucherApplyDTO> listResponse = new ArrayList<>();
        String strSQL = "SELECT va.ID as VOUCHER_APPLY_ID_0, vi.ID as VOUCHER_INFO_ID_1, vi.TITLE as VOUCHER_INFO_TITLE_2, " +
                        "p.ID as PRODUCT_ID_3, p.TEN_SAN_PHAM as PRODUCT_NAME_4, va.CREATED_AT as APPLIED_AT_5, va.CREATED_BY as APPLIED_BY_6 " +
                        "FROM PRO_VOUCHER_APPLY va " +
                        "LEFT JOIN PRO_VOUCHER_INFO vi ON vi.ID = va.VOUCHER_ID " +
                        "LEFT JOIN PRO_PRODUCT p ON p.ID = va.SAN_PHAM_ID";
        Query query = entityManager.createNativeQuery(strSQL);
        List<Object[]> listData = query.getResultList();
        for (Object[] data : listData) {
            VoucherApplyDTO voucherApplyDTO = new VoucherApplyDTO();
            voucherApplyDTO.setVoucherApplyId(Integer.parseInt(String.valueOf(data[0])));
            voucherApplyDTO.setVoucherInfoId(Integer.parseInt(String.valueOf(data[1])));
            voucherApplyDTO.setVoucherInfoTitle(String.valueOf(data[2]));
            voucherApplyDTO.setProductId(Integer.parseInt(String.valueOf(data[3])));
            voucherApplyDTO.setProductName(String.valueOf(data[4]));
            voucherApplyDTO.setAppliedAt((String.valueOf(data[5])).substring(0, 10));
            voucherApplyDTO.setAppliedBy(Integer.parseInt(String.valueOf(data[6])));
            listResponse.add(voucherApplyDTO);
        }
        return listResponse;
    }

    @Override
    public List<VoucherApplyDTO> findByProductId(Integer productId) {
        List<VoucherApplyDTO> listResponse = new ArrayList<>();
        String strSQL = "SELECT va.ID as VOUCHER_APPLY_ID_0, vi.ID as VOUCHER_INFO_ID_1, vi.TITLE as VOUCHER_INFO_TITLE_2, " +
                        "p.ID as PRODUCT_ID_3, p.TEN_SAN_PHAM as PRODUCT_NAME_4, va.CREATED_AT as APPLIED_AT_5, va.CREATED_BY as APPLIED_BY_6 " +
                        "FROM PRO_VOUCHER_APPLY va " +
                        "LEFT JOIN PRO_VOUCHER_INFO vi ON vi.ID = va.VOUCHER_ID " +
                        "LEFT JOIN PRO_PRODUCT p ON p.ID = va.SAN_PHAM_ID " +
                        "WHERE p.ID = " + productId;
        Query query = entityManager.createNativeQuery(strSQL);
        List<Object[]> listData = query.getResultList();
        for (Object[] data : listData) {
            VoucherApplyDTO voucherApplyDTO = new VoucherApplyDTO();
            voucherApplyDTO.setVoucherApplyId(Integer.parseInt(String.valueOf(data[0])));
            voucherApplyDTO.setVoucherInfoId(Integer.parseInt(String.valueOf(data[1])));
            voucherApplyDTO.setVoucherInfoTitle(String.valueOf(data[2]));
            voucherApplyDTO.setProductId(Integer.parseInt(String.valueOf(data[3])));
            voucherApplyDTO.setProductName(String.valueOf(data[4]));
            voucherApplyDTO.setAppliedAt(String.valueOf(data[5]).substring(0, 10));
            voucherApplyDTO.setAppliedBy(Integer.parseInt(String.valueOf(data[6])));
            listResponse.add(voucherApplyDTO);
        }
        return listResponse;
    }

    @Override
    public VoucherApplyDTO findOneByProductId(Integer productId) {
        VoucherApplyDTO dataResponse = new VoucherApplyDTO();
        String strSQL = "SELECT va.ID as VOUCHER_APPLY_ID_0, vi.ID as VOUCHER_INFO_ID_1, vi.TITLE as VOUCHER_INFO_TITLE_2, " +
                        "p.ID as PRODUCT_ID_3, p.TEN_SAN_PHAM as PRODUCT_NAME_4, va.CREATED_AT as APPLIED_AT_5, va.CREATED_BY as APPLIED_BY_6 " +
                        "FROM PRO_VOUCHER_APPLY va " +
                        "LEFT JOIN PRO_VOUCHER_INFO vi ON vi.ID = va.VOUCHER_ID " +
                        "LEFT JOIN PRO_PRODUCT p ON p.ID = va.SAN_PHAM_ID " +
                        "WHERE p.ID = " + productId + " AND ROWNUM = 1";
        Query query = entityManager.createNativeQuery(strSQL);
        Object[] data = (Object[]) query.getResultList().get(0);
        if (data != null) {
            dataResponse.setVoucherApplyId(Integer.parseInt(String.valueOf(data[0])));
            dataResponse.setVoucherInfoId(Integer.parseInt(String.valueOf(data[1])));
            dataResponse.setVoucherInfoTitle(String.valueOf(data[2]));
            dataResponse.setProductId(Integer.parseInt(String.valueOf(data[3])));
            dataResponse.setProductName(String.valueOf(data[4]));
            dataResponse.setAppliedAt((String.valueOf(data[5])).substring(0, 10));
            dataResponse.setAppliedBy(Integer.parseInt(String.valueOf(data[6])));
        }
        return dataResponse;
    }

    @Override
    public List<VoucherApply> findAll() {
        return voucherApplyRepository.findAll();
    }

    @Override
    public List<VoucherApply> findByVoucherId(Integer voucherId) {
        return voucherApplyRepository.findByVoucherId(voucherId);
    }

    @Override
    public VoucherApply findById(Integer id) {
        return voucherApplyRepository.findById(id).orElse(null);
    }

    @Override
    public String save(VoucherApply voucherApply) {
        voucherApplyRepository.save(voucherApply);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(VoucherApply voucherApply, Integer id) {
        if (this.findById(id) != null) {
            voucherApply.setId(id);
            voucherApplyRepository.save(voucherApply);
        }
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (this.findById(entityId) != null) {
            voucherApplyRepository.deleteById(entityId);
        }
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}