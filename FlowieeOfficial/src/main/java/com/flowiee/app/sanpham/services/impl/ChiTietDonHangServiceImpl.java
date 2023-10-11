package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.action.DonHangAction;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.sanpham.entity.DonHangChiTiet;
import com.flowiee.app.sanpham.repository.ChiTietDonHangRepository;
import com.flowiee.app.sanpham.services.ChiTietDonHangService;
import com.flowiee.app.sanpham.services.DonHangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {
    private static final Logger logger = LoggerFactory.getLogger(ChiTietDonHangServiceImpl.class);
    private static final String module = SystemModule.SAN_PHAM.name();

    @Autowired
    private DonHangService donHangService;
    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<DonHangChiTiet> findAll() {
        return chiTietDonHangRepository.findAll();
    }

    @Override
    public DonHangChiTiet findById(int id) {
        return chiTietDonHangRepository.findById(id).orElse(null);
    }

    @Override
    public List<DonHangChiTiet> findByDonHangId(int donHangId) {
        if (donHangId <= 0 || donHangService.findById(donHangId) == null) {
            throw new NotFoundException();
        }
        return chiTietDonHangRepository.findByDonHangId(donHangService.findById(donHangId));
    }

    @Override
    @Transactional
    public String save(DonHangChiTiet donHangChiTiet) {
        if (donHangChiTiet.getDonHang() == null) {
            throw new NotFoundException();
        }
        try {
            chiTietDonHangRepository.save(donHangChiTiet);
            systemLogService.writeLog(module, DonHangAction.UPDATE_DONHANG.name(), "Thêm mới item vào đơn hàng: " + donHangChiTiet.toString());
            logger.info(DonHangServiceImpl.class.getName() + ": Thêm mới item vào đơn hàng " + donHangChiTiet.toString());
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(DonHangChiTiet donHangChiTiet, int id) {
        if (id <=0 || this.findById(id) == null || donHangChiTiet.getDonHang() == null) {
            throw new NotFoundException();
        }
        try {
            donHangChiTiet.setId(id);
            chiTietDonHangRepository.save(donHangChiTiet);
            systemLogService.writeLog(module, DonHangAction.UPDATE_DONHANG.name(), "Cập nhật item of đơn hàng: " + donHangChiTiet.toString());
            logger.info(DonHangServiceImpl.class.getName() + ": Cập nhật item of đơn hàng " + donHangChiTiet.toString());
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String delete(int id) {
        DonHangChiTiet donHangChiTiet = this.findById(id);
        if (id <= 0 || donHangChiTiet == null) {
            throw new NotFoundException();
        }
        try {
            chiTietDonHangRepository.deleteById(id);
            systemLogService.writeLog(module, DonHangAction.UPDATE_DONHANG.name(), "Xóa item of đơn hàng: " + donHangChiTiet.toString());
            logger.info(DonHangServiceImpl.class.getName() + ": Xóa item of đơn hàng " + donHangChiTiet.toString());
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }
}