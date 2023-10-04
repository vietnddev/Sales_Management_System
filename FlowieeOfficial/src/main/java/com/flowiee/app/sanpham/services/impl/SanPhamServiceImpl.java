package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.hethong.model.action.SanPhamAction;
import com.flowiee.app.hethong.model.module.SystemModule;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.sanpham.entity.SanPham;
import com.flowiee.app.sanpham.repository.SanPhamRepository;
import com.flowiee.app.sanpham.services.SanPhamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    private static final Logger logger = LoggerFactory.getLogger(SanPhamServiceImpl.class);
    private static final String module = SystemModule.SAN_PHAM.name();

    @Autowired
    private SanPhamRepository productsRepository;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FileStorageService fileService;

    @Override
    public List<SanPham> findAll() {
        List<SanPham> listSanPham = productsRepository.findAll();
        for (int i = 0; i < listSanPham.size(); i++) {
            FileStorage imageActive = fileService.findImageActiveOfSanPham(listSanPham.get(i).getId());
            if (imageActive != null) {
                listSanPham.get(i).setImageActive(imageActive);
            } else {
                listSanPham.get(i).setImageActive(new FileStorage());
            }
        }
        return listSanPham;
    }

    @Override
    public SanPham findById(int id) {
        SanPham sanPham = new SanPham();
        if (id > 0) {
            sanPham = productsRepository.findById(id).orElse(null);
        }
        if (sanPham != null) {
            return sanPham;
        } else {
            logger.error("Lỗi khi findById sản phẩm!", new NotFoundException());
            return new SanPham();
        }
    }

    @Override
    public String save(SanPham sanPham) {
        if (sanPham.getLoaiSanPham() == null ||
            sanPham.getTenSanPham() == null) {
            throw new BadRequestException();
        }
        try {
            sanPham.setCreatedBy(accountService.getCurrentAccount().getId() + "");
            productsRepository.save(sanPham);
            systemLogService.writeLog(module, SanPhamAction.CREATE_SANPHAM.name(), "Thêm mới sản phẩm: " + sanPham.toString());
            logger.info(SanPhamServiceImpl.class.getName() + ": Thêm mới sản phẩm " + sanPham.toString());
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(SanPhamServiceImpl.class.getName() + ": Lỗi khi thêm mới sản phẩm", e.getCause());
            return "NOK";
        }
    }

    @Transactional
    @Override
    public String update(SanPham sanPham, int id) {
        if (id <= 0 || this.findById(id) == null) {
            throw new NotFoundException();
        }
        SanPham sanPhamBefore = this.findById(id);
        try {
            sanPham.setId(id);
            sanPham.setLastUpdatedBy(accountService.getCurrentAccount().getId() + "");
            productsRepository.save(sanPham);
            String noiDungLog = "";
            String noiDungLogUpdate = "";
            if (sanPhamBefore.toString().length() > 1950) {
                noiDungLog = sanPhamBefore.toString().substring(0, 1950);
            } else {
                noiDungLog = sanPhamBefore.toString();
            }
            if (sanPham.toString().length() > 1950) {
                noiDungLogUpdate = sanPham.toString().substring(0, 1950);
            } else {
                noiDungLogUpdate = sanPham.toString();
            }
            systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Cập nhật sản phẩm: " + noiDungLog, "Sản phẩm sau khi cập nhật: " + noiDungLogUpdate);
            logger.info(SanPhamServiceImpl.class.getName() + ": Cập nhật sản phẩm " + sanPhamBefore.toString());
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(SanPhamServiceImpl.class.getName() + ": Lỗi khi cập nhật sản phẩm!", e.getCause());
            return "NOK";
        }
    }

    @Transactional
    @Override
    public String delete(int id) {
        if (id <= 0) {
            logger.error("Lỗi khi xóa sản phẩm!", new NotFoundException());
            throw new NotFoundException();
        }
        SanPham sanPhamToDelete = this.findById(id);
        if (sanPhamToDelete == null) {
            logger.error(SanPhamServiceImpl.class.getName() + ": Lỗi khi xóa sản phẩm", new NotFoundException());
            throw new NotFoundException();
        }
        try {
            productsRepository.deleteById(id);
            logger.info(SanPhamServiceImpl.class.getName() + ": Xóa sản phẩm " + sanPhamToDelete.toString());
            systemLogService.writeLog(module, SanPhamAction.DELETE_SANPHAM.name(), "Xóa sản phẩm: " + sanPhamToDelete.toString());
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(SanPhamServiceImpl.class.getName() + ": Lỗi khi xóa sản phẩm", e.getCause());
            return "NOK";
        }
    }
}