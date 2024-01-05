package com.flowiee.app.service.impl;

import com.flowiee.app.entity.*;
import com.flowiee.app.dto.VoucherInfoDTO;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.exception.DataInUseException;
import com.flowiee.app.repository.VoucherInfoRepository;
import com.flowiee.app.service.*;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<VoucherInfoDTO> findAll(String status, Date startTime, Date endTime, String title) {
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            if (startTime != null & endTime == null) {
                endTime = formatDate.parse("2100-12-31");
            }
            if (startTime == null & endTime != null) {
                startTime = formatDate.parse("1900-01-01");
            }
            return this.extractDataQuery(voucherInfoRepository.findAll(null, null, status, startTime, endTime, title));
        } catch (ParseException e) {
            throw new BadRequestException("");
        }
    }

    @Override
    public VoucherInfoDTO findById(Integer voucherId) {
        return this.extractDataQuery(voucherInfoRepository.findAll(null, voucherId, null, null, null, null)).get(0);
    }

    @Override
    public List<VoucherInfoDTO> findByIds(List<Integer> voucherIds, String status) {
        String inId = "";
        if (voucherIds != null) {
                for (int id : voucherIds) {
                    inId += id + ",";
                }
                if (!inId.isEmpty()) {
                    inId = inId.substring(0, inId.length() - 1);
                }
            }
        return this.extractDataQuery(voucherInfoRepository.findAll(inId, null, status, null, null, null));
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
            dto.setCreatedAt(CommonUtil.convertStringToDate(String.valueOf(data[12]), "yyyy-MM-dd"));
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
}