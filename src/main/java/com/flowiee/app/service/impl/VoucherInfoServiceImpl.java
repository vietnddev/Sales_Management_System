package com.flowiee.app.service.impl;

import com.flowiee.app.entity.*;
import com.flowiee.app.dto.VoucherInfoDTO;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.exception.DataInUseException;
import com.flowiee.app.repository.VoucherInfoRepository;
import com.flowiee.app.service.*;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.DateUtils;
import com.flowiee.app.utils.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VoucherInfoServiceImpl implements VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherInfoServiceImpl.class);

    @Autowired
    private VoucherInfoRepository voucherInfoRepository;
    @Autowired
    private VoucherTicketService voucherTicketService;
    @Autowired
    private VoucherApplyService voucherApplyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<VoucherInfoDTO> findAll(String status, Date startTime, Date endTime, String title) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        if (endTime == null) {
            endTime = DateUtils.convertStringToDate("2100-12-31", "YYYY-MM-dd");
        }
        if (startTime == null) {
            startTime = DateUtils.convertStringToDate("1900-01-01", "YYYY-MM-dd");
        }
        //return this.extractDataQuery(voucherInfoRepository.findAll(null, null, status, startTime, endTime, title));
        return this.findData(null, null, status, startTime, endTime, title);
    }

    @Override
    public VoucherInfoDTO findById(Integer voucherId) {
        return this.findData(voucherId, null, null, null, null, null).get(0);
        //return this.extractDataQuery(voucherInfoRepository.findAll(null, voucherId, null, null, null, null)).get(0);
    }

    @Override
    public List<VoucherInfoDTO> findByIds(List<Integer> voucherIds, String status) {
//        String inId = "";
//        if (voucherIds != null && !voucherIds.isEmpty()) {
//            inId += "(";
//            for (int id : voucherIds) {
//                inId += "'" + id + "',";
//            }
//            inId = inId.substring(0, inId.length() - 1);
//            inId += ")";
//        }
//        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + inId);
        //return this.extractDataQuery(voucherInfoRepository.findAll(voucherIds, null, status, null, null, null));
        return this.findData(null, voucherIds, status, null, null, null);
    }

    @Override
    public String save(VoucherInfo voucherInfo, List<Integer> listProductToApply) {
        try {
            if (voucherInfo != null) {
                voucherInfo = voucherInfoRepository.save(voucherInfo);
                //
                for (int sanPhamId : listProductToApply) {
                    VoucherApply voucherApply = new VoucherApply();
                    voucherApply.setVoucherId(voucherInfo.getId());
                    voucherApply.setSanPhamId(sanPhamId);
                    voucherApplyService.save(voucherApply);
                }
                //Gen list voucher detail
                List<String> listKeyVoucher = new ArrayList<>();
                while (listKeyVoucher.size() < voucherInfo.getQuantity()) {
                    String randomKey = generateRandomKeyVoucher(voucherInfo.getLengthOfKey(), voucherInfo.getVoucherType());
                    if (!listKeyVoucher.contains(randomKey)) {             
                        VoucherTicket voucherTicket = new VoucherTicket();
                        voucherTicket.setCode(randomKey);
                        voucherTicket.setVoucherInfo(voucherInfo);
                        voucherTicket.setStatus(false);
                        if (AppConstants.SERVICE_RESPONSE_SUCCESS.equals(voucherTicketService.save(voucherTicket))) {
                        	listKeyVoucher.add(randomKey);
                        }
                    }
                }
                return AppConstants.SERVICE_RESPONSE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return AppConstants.SERVICE_RESPONSE_FAIL;
    }

    @Override
    public String update(VoucherInfo voucherInfo, Integer voucherId) {
        if (voucherInfo == null || voucherId == null || voucherId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        voucherInfo.setId(voucherId);
        voucherInfoRepository.save(voucherInfo);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String detele(Integer voucherId) {
        if (voucherId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        VoucherInfoDTO voucherInfo = this.findById(voucherId);
        if (voucherInfo == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        if (!voucherApplyService.findByVoucherId(voucherId).isEmpty()) {
            throw new DataInUseException(ErrorMessages.ERROR_LOCKED);
        }
        voucherInfoRepository.deleteById(voucherId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    private String generateRandomKeyVoucher(int lengthOfKey, String voucherType) {
        String characters = "";
        if (AppConstants.VOUCHER_TYPE.NUMBER.name().equals(voucherType)) {
            characters = "0123456789";
        }
        if (AppConstants.VOUCHER_TYPE.TEXT.name().equals(voucherType)) {
            characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        if (AppConstants.VOUCHER_TYPE.BOTH.name().equals(voucherType)) {
            characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        }

        SecureRandom random = new SecureRandom();
        StringBuilder keyVoucher = new StringBuilder();
        for (int i = 0; i < lengthOfKey; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            keyVoucher.append(randomChar);
        }
        return keyVoucher.toString();
    }

    private List<VoucherInfoDTO> extractDataQuery(List<Object[]> objects) {
        List<VoucherInfoDTO> listVoucherInfoDTO = new ArrayList<>();
        for (Object[] data : objects) {
            VoucherInfoDTO dto = new VoucherInfoDTO();
            dto.setId(Integer.parseInt(String.valueOf(data[0])));
            dto.setTitle(String.valueOf(data[1]));
            dto.setDescription(String.valueOf(data[2]));
            dto.setDoiTuongApDung(String.valueOf(data[3]));
            dto.setDiscount(Integer.parseInt(String.valueOf(data[4])));
            dto.setMaxPriceDiscount(Float.parseFloat(String.valueOf(data[5])));
            dto.setQuantity(Integer.parseInt(String.valueOf(data[6])));
            dto.setVoucherType(String.valueOf(data[7]));
            dto.setLengthOfKey(Integer.parseInt(String.valueOf(data[8])));
            dto.setStartTime(String.valueOf(data[9]).substring(0, 10));
            dto.setEndTime(String.valueOf(data[10]).substring(0, 10));
            if (AppConstants.VOUCHER_STATUS.ACTIVE.name().equals(String.valueOf(data[11]))) {
                dto.setStatus(AppConstants.VOUCHER_STATUS.ACTIVE.getLabel());
            } else if (AppConstants.VOUCHER_STATUS.INACTIVE.name().equals(String.valueOf(data[11]))) {
                dto.setStatus(AppConstants.VOUCHER_STATUS.INACTIVE.getLabel());
            }
            dto.setCreatedAt(DateUtils.convertStringToDate(String.valueOf(data[12]), "yyyy-MM-dd"));
            dto.setCreatedBy(Integer.parseInt(String.valueOf(data[13])));
            dto.setListVoucherTicket(null);

            List<VoucherApply> listVoucherApply = voucherApplyService.findByVoucherId(dto.getId());
            List<Product> listSanPhamApDung = new ArrayList<>();
            for (VoucherApply vSanPham : listVoucherApply) {
                Product productApplied = productService.findById(vSanPham.getSanPhamId());
                if (productApplied != null) {
                    listSanPhamApDung.add(productApplied);
                }
            }
            dto.setListSanPhamApDung(listSanPhamApDung);

            listVoucherInfoDTO.add(dto);
        }
        return listVoucherInfoDTO;
    }

    private List<VoucherInfoDTO> findData(Integer voucherId, List<Integer> voucherIds, String status, Date startTime, Date endTime, String title) {
        List<VoucherInfoDTO> listVoucherInfoDTO = new ArrayList<>();
        try {
            String _VOUCHER_STATUS = "(CASE WHEN ((TRUNC(START_TIME) <= TRUNC(CURRENT_DATE)) AND (TRUNC(END_TIME) >= TRUNC(CURRENT_DATE))) THEN '" + AppConstants.VOUCHER_STATUS.ACTIVE.name() + "' ELSE '" + AppConstants.VOUCHER_STATUS.INACTIVE.name() + "' END) ";
            String strSQL = "SELECT v.ID as ID_0, v.TITLE as TITLE_1, v.DESCRIPTION as DESCRIPTION_2, v.DOI_TUONG_AP_DUNG as DOI_TUONG_AP_DUNG_3, " +
                    "v.DISCOUNT as DISCOUNT_PERCENT_4, v.MAX_PRICE_DISCOUNT as DISCOUNT_MAX_PRICE_5, v.SO_LUONG as QUANTITY_6, " +
                    "v.TYPE as CODE_TYPE_7, v.LENGTH_OF_KEY as CODE_LENGTH_8, v.START_TIME as START_TIME_9, v.END_TIME as END_TIME_10, " +
                    _VOUCHER_STATUS + " AS STATUS_11, " +
                    "v.CREATED_AT as CREATED_AT_12, v.CREATED_BY as CREATED_BY_13 " +
                    "FROM PRO_VOUCHER_INFO v " +
                    "WHERE 1=1 ";
            if (voucherId != null) {
                strSQL += "AND v.ID = " + voucherId + " ";
            }
            if (voucherIds != null) {
                String inId = "";
                for (int id : voucherIds) {
                    inId += id + ",";
                }
                if (!inId.isEmpty()) {
                    inId = inId.substring(0, inId.length() - 1);
                }
                strSQL += "AND v.ID IN (" + inId + ") ";
            }
            if (AppConstants.VOUCHER_STATUS.ACTIVE.name().equals(status)) {
                strSQL += "AND " + _VOUCHER_STATUS + " = '" + AppConstants.VOUCHER_STATUS.ACTIVE.name() + "' ";
            } else if (AppConstants.VOUCHER_STATUS.INACTIVE.name().equals(status)) {
                strSQL += "AND " + _VOUCHER_STATUS + " = '" + AppConstants.VOUCHER_STATUS.INACTIVE.name() + "' ";
            }
            if (title != null) {
                strSQL += "AND v.TITLE LIKE '%" + title + "%' ";
            }
            //strSQL += "AND (TRUNC(START_TIME) >= TO_DATE('" + startTime + "', 'YYYY-MM-dd') OR TRUNC(END_TIME) <= TO_DATE('" + startTime + "', 'YYYY-MM-dd')) ";
            logger.info("[SQL findData]: " + strSQL);
            Query query = entityManager.createNativeQuery(strSQL);
            List<Object[]> rawData = query.getResultList();
            for (Object[] data : rawData) {
                VoucherInfoDTO dto = new VoucherInfoDTO();
                dto.setId(Integer.parseInt(String.valueOf(data[0])));
                dto.setTitle(String.valueOf(data[1]));
                dto.setDescription(String.valueOf(data[2]));
                dto.setDoiTuongApDung(String.valueOf(data[3]));
                dto.setDiscount(Integer.parseInt(String.valueOf(data[4])));
                dto.setMaxPriceDiscount(Float.parseFloat(String.valueOf(data[5])));
                dto.setQuantity(Integer.parseInt(String.valueOf(data[6])));
                dto.setVoucherType(String.valueOf(data[7]));
                dto.setLengthOfKey(Integer.parseInt(String.valueOf(data[8])));
                dto.setStartTime(String.valueOf(data[9]).substring(0, 10));
                dto.setEndTime(String.valueOf(data[10]).substring(0, 10));
                if (AppConstants.VOUCHER_STATUS.ACTIVE.name().equals(String.valueOf(data[11]))) {
                    dto.setStatus(AppConstants.VOUCHER_STATUS.ACTIVE.getLabel());
                } else if (AppConstants.VOUCHER_STATUS.INACTIVE.name().equals(String.valueOf(data[11]))) {
                    dto.setStatus(AppConstants.VOUCHER_STATUS.INACTIVE.getLabel());
                }
                dto.setCreatedAt(DateUtils.convertStringToDate(String.valueOf(data[12]), "yyyy-MM-dd"));
                dto.setCreatedBy(Integer.parseInt(String.valueOf(data[13])));
                dto.setListVoucherTicket(null);

                List<VoucherApply> listVoucherApply = voucherApplyService.findByVoucherId(dto.getId());
                List<Product> listSanPhamApDung = new ArrayList<>();
                for (VoucherApply vSanPham : listVoucherApply) {
                    Product productApplied = productService.findById(vSanPham.getSanPhamId());
                    if (productApplied != null) {
                        listSanPhamApDung.add(productApplied);
                    }
                }
                dto.setListSanPhamApDung(listSanPhamApDung);

                listVoucherInfoDTO.add(dto);
            }
        } catch (Exception e) {
            logger.error("Load voucher fail!", e);
            e.printStackTrace();
        }
        return listVoucherInfoDTO;
    }
}