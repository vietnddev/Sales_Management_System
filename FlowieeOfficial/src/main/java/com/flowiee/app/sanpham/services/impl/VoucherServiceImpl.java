package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.sanpham.entity.Voucher;
import com.flowiee.app.sanpham.entity.VoucherDetail;
import com.flowiee.app.sanpham.repository.VoucherRepository;
import com.flowiee.app.sanpham.services.VoucherDetailService;
import com.flowiee.app.sanpham.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherDetailService voucherDetailService;

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher findById(Integer voucherId) {
        return null;
    }

    @Override
    public String save(Voucher voucher, List<Integer> listSanPhamApDung) {
        if (voucher != null) {
            voucherRepository.save(voucher);
            //
            for (int i = 0; i < voucher.getQuantity(); i++) {
                String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                SecureRandom random = new SecureRandom();
                StringBuilder keyVoucher = new StringBuilder();
                for (int j = 0; j < voucher.getLengthOfKey(); j++) {
                    int randomIndex = random.nextInt(characters.length());
                    char randomChar = characters.charAt(randomIndex);
                    keyVoucher.append(randomChar);
                }
                //
                VoucherDetail voucherDetail = new VoucherDetail();
                voucherDetail.setKey(keyVoucher.toString());
                voucherDetail.setVoucher(voucher);
                voucherDetail.setStatus(false);
                voucherDetailService.save(voucherDetail);
            }
            return "OK";
        }
        return "NOK";
    }

    @Override
    public String update(Voucher voucher, Integer voucherId) {
        return null;
    }

    @Override
    public String detele(Integer voucherId) {
        return null;
    }
}