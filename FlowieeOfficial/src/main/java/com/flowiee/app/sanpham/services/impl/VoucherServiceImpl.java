package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.Voucher;
import com.flowiee.app.sanpham.entity.VoucherDetail;
import com.flowiee.app.sanpham.entity.VoucherSanPham;
import com.flowiee.app.sanpham.model.VoucherResponse;
import com.flowiee.app.sanpham.repository.VoucherRepository;
import com.flowiee.app.sanpham.services.BienTheSanPhamService;
import com.flowiee.app.sanpham.services.VoucherDetailService;
import com.flowiee.app.sanpham.services.VoucherSanPhamService;
import com.flowiee.app.sanpham.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherDetailService voucherDetailService;
    @Autowired
    private VoucherSanPhamService voucherSanPhamService;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;

    @Override
    public List<VoucherResponse> findAll() {
        List<Voucher> listVoucher = voucherRepository.findAll();
        List<VoucherResponse> listVoucherResponse = new ArrayList<>();
        for (Voucher voucher : listVoucher) {
            VoucherResponse voucherResponse = new VoucherResponse();
            voucherResponse.setId(voucher.getId());
            voucherResponse.setTitle(voucher.getTitle());
            voucherResponse.setDescription(voucher.getDescription());
            voucherResponse.setDoiTuongApDung(voucher.getDoiTuongApDung());
            voucherResponse.setVoucherType(voucher.getVoucherType());
            voucherResponse.setQuantity(voucher.getQuantity());
            voucherResponse.setLengthOfKey(voucher.getLengthOfKey());
            voucherResponse.setDiscount(voucher.getDiscount());
            voucherResponse.setMaxPriceDiscount(voucher.getMaxPriceDiscount());
            voucherResponse.setStatus(voucher.isStatus());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            voucherResponse.setStartTime(dateFormat.format(voucher.getStartTime()));
            voucherResponse.setEndTime(dateFormat.format(voucher.getEndTime()));

            List<VoucherDetail> listVoucherDetail = voucherDetailService.findByVoucherId(voucher.getId());
            voucherResponse.setListVoucherDetail(listVoucherDetail);

            List<VoucherSanPham> listVoucherSanPham = voucherSanPhamService.findByVoucherId(voucher.getId());
            List<BienTheSanPham> listSanPhamApDung = new ArrayList<>();
            for (VoucherSanPham vSanPham : listVoucherSanPham) {
                 BienTheSanPham sanPhamApDung = bienTheSanPhamService.findById(vSanPham.getSanPhamId());
                 if (sanPhamApDung != null) {
                     listSanPhamApDung.add(sanPhamApDung);
                 }
            }
            voucherResponse.setListSanPhamApDung(listSanPhamApDung);

            listVoucherResponse.add(voucherResponse);
        }
        return listVoucherResponse;
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
            for (int sanPhamId : listSanPhamApDung) {
                VoucherSanPham voucherSanPham = new VoucherSanPham();
                voucherSanPham.setVoucherId(voucher.getId());
                voucherSanPham.setSanPhamId(sanPhamId);
                voucherSanPhamService.save(voucherSanPham);
            }
            //Gen list voucher detail
            List<String> listKeyVoucher = new ArrayList<>();
            while (listKeyVoucher.size() < voucher.getQuantity()) {
                String randomKey = generateRandomKeyVoucher(voucher.getLengthOfKey(), voucher.getVoucherType());
                if (!listKeyVoucher.contains(randomKey)) {
                    listKeyVoucher.add(randomKey);
                    //Lưu key vào DB
                    VoucherDetail voucherDetail = new VoucherDetail();
                    voucherDetail.setKey(randomKey);
                    voucherDetail.setVoucher(voucher);
                    voucherDetail.setStatus(false);
                    voucherDetailService.save(voucherDetail);
                }
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

    private String generateRandomKeyVoucher(int lengthOfKey, String voucherType) {
        String characters = "";
        if (voucherType.equals("NUMBER")) {
            characters = "0123456789";
        } else if (voucherType.equals("TEXT")) {
            characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        } else if (voucherType.equals("TEXT")) {
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