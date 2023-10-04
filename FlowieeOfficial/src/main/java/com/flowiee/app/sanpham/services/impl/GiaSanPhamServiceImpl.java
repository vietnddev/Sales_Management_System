package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.hethong.entity.SystemLog;
import com.flowiee.app.hethong.model.action.KhachHangAction;
import com.flowiee.app.hethong.model.action.SanPhamAction;
import com.flowiee.app.hethong.model.module.SystemModule;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.GiaSanPham;
import com.flowiee.app.sanpham.repository.GiaSanPhamRepository;
import com.flowiee.app.sanpham.services.BienTheSanPhamService;
import com.flowiee.app.sanpham.services.GiaSanPhamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiaSanPhamServiceImpl implements GiaSanPhamService {
    private static final Logger logger = LoggerFactory.getLogger(GiaSanPhamServiceImpl.class);
    private static final String module = SystemModule.SAN_PHAM.name();

    @Autowired
    private GiaSanPhamRepository giaSanPhamRepository;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<GiaSanPham> findAll() {
        return giaSanPhamRepository.findAll();
    }

    @Override
    public List<GiaSanPham> findByBienTheSanPhamId(int bienTheSanPhamId) {
        return giaSanPhamRepository.findListGiaBanOfSP(bienTheSanPhamService.findById(bienTheSanPhamId));
    }

    @Override
    public GiaSanPham findById(int id) {
        return giaSanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public Double findGiaHienTai(int bienTheSanPhamId) {
        return giaSanPhamRepository.findGiaBanHienTai(bienTheSanPhamService.findById(bienTheSanPhamId), true);
    }

    @Override
    public GiaSanPham findGiaHienTaiModel(int bienTheSanPhamId) {
        return giaSanPhamRepository.findGiaBanHienTaiModel(bienTheSanPhamService.findById(bienTheSanPhamId), true);
    }

    @Override
    public String save(GiaSanPham giaSanPham) {
        try {
            giaSanPhamRepository.save(giaSanPham);
            systemLogService.writeLog(module, SanPhamAction.UPDATE_PRICE_SANPHAM.name(), "Thêm mới giá sản phẩm: " + giaSanPham.toString());
            logger.info(GiaSanPhamServiceImpl.class.getName() + ": Thêm mới giá sản phẩm " + giaSanPham.toString());
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(GiaSanPham giaSanPham, int bienTheSanPhamId, int giaSanPhamId) {
        try {
            if (bienTheSanPhamId <= 0 || bienTheSanPhamService.findById(bienTheSanPhamId) == null) {
                throw new NotFoundException();
            }
            if (giaSanPhamId <= 0 || this.findById(giaSanPhamId) == null) {
                throw new NotFoundException();
            }
            //Chuyển trạng thái giá hiện tại về false
            GiaSanPham disableGiaCu = this.findById(giaSanPhamId);
            disableGiaCu.setTrangThai(false);
            giaSanPhamRepository.save(disableGiaCu);
            //Thêm giá mới
            giaSanPham.setId(0);
            giaSanPham.setBienTheSanPham(bienTheSanPhamService.findById(bienTheSanPhamId));
            giaSanPham.setTrangThai(true);
            giaSanPhamRepository.save(giaSanPham);
            //Lưu log
            String noiDung = "Giá cũ:  " + disableGiaCu.getGiaBan();
            String noiDungCapNhat = "Giá mới: " + giaSanPham.getGiaBan();
            systemLogService.writeLog(module, SanPhamAction.UPDATE_PRICE_SANPHAM.name(), "Cập nhật giá sản phẩm: " + noiDung.toString(), "Giá sau khi cập nhật: " + noiDungCapNhat.toString());
            logger.info(GiaSanPhamServiceImpl.class.getName() + ": Cập nhật giá sản phẩm " + giaSanPham.toString());
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }
    @Override
    public String delete(int id) {
        GiaSanPham giaSanPham = this.findById(id);
        if (id <= 0 || giaSanPham == null) {
            throw new NotFoundException();
        }
        giaSanPhamRepository.deleteById(id);
        systemLogService.writeLog(module, SanPhamAction.UPDATE_PRICE_SANPHAM.name(), "Xóa giá sản phẩm: " + giaSanPham.toString());
        logger.info(GiaSanPhamServiceImpl.class.getName() + ": Xóa giá sản phẩm " + giaSanPham.toString());
        return "OK";
    }
}