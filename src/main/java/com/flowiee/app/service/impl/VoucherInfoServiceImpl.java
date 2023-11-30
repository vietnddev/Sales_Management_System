package com.flowiee.app.service.impl;

import com.flowiee.app.entity.*;
import com.flowiee.app.dto.VoucherInfoDTO;
import com.flowiee.app.repository.VoucherInfoRepository;
import com.flowiee.app.service.*;

import com.flowiee.app.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
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
    private ProductVariantService productVariantService;
    @Autowired
    private ProductService productService;

    @Override
    public List<VoucherInfoDTO> findAll() {
        List<VoucherInfo> listVoucherInfo = voucherInfoRepository.findAll();
        List<VoucherInfoDTO> listVoucherInfoDTO = new ArrayList<>();
        for (VoucherInfo voucherInfo : listVoucherInfo) {
            VoucherInfoDTO voucherInfoDTO = VoucherInfoDTO.fromVoucherInfo(voucherInfo);
            voucherInfoDTO.setListVoucherTicket(null);

            List<VoucherApply> listVoucherApply = voucherApplyService.findByVoucherId(voucherInfo.getId());
            List<Product> listSanPhamApDung = new ArrayList<>();
            for (VoucherApply vSanPham : listVoucherApply) {
                 Product productApplied = productService.findById(vSanPham.getSanPhamId());
                 if (productApplied != null) {
                     listSanPhamApDung.add(productApplied);
                 }
            }
            voucherInfoDTO.setListSanPhamApDung(listSanPhamApDung);

            listVoucherInfoDTO.add(voucherInfoDTO);
        }
        return listVoucherInfoDTO;
    }

    @Override
    public VoucherInfo findById(Integer voucherId) {
        return voucherInfoRepository.findById(voucherId).orElse(null);
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
                        listKeyVoucher.add(randomKey);
                        //Lưu ticket vào DB
                        VoucherTicket voucherTicket = new VoucherTicket();
                        voucherTicket.setCode(randomKey);
                        voucherTicket.setVoucherInfo(voucherInfo);
                        voucherTicket.setStatus(false);
                        voucherTicketService.save(voucherTicket);
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
        VoucherInfo voucherInfo = this.findById(voucherId);
        if (voucherInfo == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
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
}