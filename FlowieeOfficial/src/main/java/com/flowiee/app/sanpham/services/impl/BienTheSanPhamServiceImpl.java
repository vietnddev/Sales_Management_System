package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.exception.DataExistsException;
import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.hethong.entity.SystemLog;
import com.flowiee.app.hethong.model.action.SanPhamAction;
import com.flowiee.app.hethong.model.module.SystemModule;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.repository.BienTheSanPhamRepository;
import com.flowiee.app.sanpham.services.BienTheSanPhamService;
import com.flowiee.app.sanpham.services.GiaSanPhamService;
import com.flowiee.app.sanpham.services.SanPhamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BienTheSanPhamServiceImpl implements BienTheSanPhamService {
    private static final Logger logger = LoggerFactory.getLogger(BienTheSanPhamServiceImpl.class);
    private static final String module = SystemModule.SAN_PHAM.name();

    @Autowired
    private BienTheSanPhamRepository bienTheSanPhamRepository;
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private GiaSanPhamService giaSanPhamService;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private AccountService accountService;

    @Override
    public List<BienTheSanPham> findAll() {
        return bienTheSanPhamRepository.findAll();
    }

    @Override
    public BienTheSanPham findById(int id) {
        return bienTheSanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public List<BienTheSanPham> getListVariantOfProduct(int sanPhamId) {
        List<BienTheSanPham> listReturn = new ArrayList<>();
        bienTheSanPhamRepository.findListBienTheOfsanPham(sanPhamId).forEach(bienTheSanPham -> {
            bienTheSanPham.setGiaSanPham(giaSanPhamService.findGiaHienTaiModel(bienTheSanPham.getId()));
            listReturn.add(bienTheSanPham);
        });
        return listReturn;
    }

    @Override
    public Double getGiaBan(int id) {
        if (id <= 0 || this.findById(id) == null) {
            throw new NotFoundException();
        }
        return giaSanPhamService.findGiaHienTai(id);
    }

    @Override
    public String save(BienTheSanPham bienTheSanPham) {
        if (bienTheSanPham.getLoaiMauSac() == null || bienTheSanPham.getLoaiKichCo() == null) {
            throw new BadRequestException();
        }
        if (bienTheSanPhamRepository.findByMauSacAndKichCo(bienTheSanPham.getSanPham().getId(),
                                                           bienTheSanPham.getLoaiMauSac().getId(),
                                                           bienTheSanPham.getLoaiKichCo().getId()) != null)
        {
            throw new DataExistsException();
        }
        try {
            bienTheSanPhamRepository.save(bienTheSanPham);
            SystemLog systemLog = new SystemLog();
            systemLog.setModule(SystemModule.SAN_PHAM.name());
            systemLog.setAction(SanPhamAction.CREATE_SANPHAM.name());
            systemLog.setNoiDung(bienTheSanPham.toString());
            systemLog.setAccount(accountService.getCurrentAccount());
            systemLog.setIp(accountService.getIP());
            systemLogService.writeLog(systemLog);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(BienTheSanPham bienTheSanPham, int id) {
        BienTheSanPham bienTheSanPhamBefore = this.findById(id);
        if (this.findById(id) == null) {
            throw new NotFoundException();
        }
        try {
            bienTheSanPham.setId(id);
            bienTheSanPhamRepository.save(bienTheSanPham);
            SystemLog systemLog = new SystemLog();
            systemLog.setModule(SystemModule.SAN_PHAM.name());
            systemLog.setAction(SanPhamAction.UPDATE_SANPHAM.name());
            systemLog.setNoiDung(bienTheSanPhamBefore.toString());
            systemLog.setNoiDungCapNhat(bienTheSanPham.toString());
            systemLog.setAccount(accountService.getCurrentAccount());
            systemLog.setIp(accountService.getIP());
            systemLogService.writeLog(systemLog);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String detele(int bienTheSanPhamId) {
        BienTheSanPham bienTheSanPhamToDelete = this.findById(bienTheSanPhamId);
        if (bienTheSanPhamToDelete == null) {
            throw new NotFoundException();
        }
        try {
            bienTheSanPhamRepository.deleteById(bienTheSanPhamId);
            SystemLog systemLog = new SystemLog();
            systemLog.setModule(SystemModule.SAN_PHAM.name());
            systemLog.setAction(SanPhamAction.DELETE_SANPHAM.name());
            systemLog.setNoiDung(bienTheSanPhamToDelete.toString());
            systemLog.setAccount(accountService.getCurrentAccount());
            systemLog.setIp(accountService.getIP());
            systemLogService.writeLog(systemLog);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String updateSoLuong(Integer soLuong, Integer id) {
        if (id < 0) {
            throw new BadRequestException();
        }
        BienTheSanPham bienTheSanPham = this.findById(id);
        if (bienTheSanPham == null) {
            throw new BadRequestException();
        }
        bienTheSanPham.setSoLuongKho(bienTheSanPham.getSoLuongKho() - soLuong);
        try {
            bienTheSanPhamRepository.save(bienTheSanPham);
            systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Cập nhật lại số lượng sản phẩm khi tạo đơn hàng", null);
            return "OK";
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật số lượng sản phẩm!", bienTheSanPham);
            return "NOK";
        }
    }
}