package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.ExcelUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.danhmuc.entity.KenhBanHang;
import com.flowiee.app.danhmuc.entity.TrangThaiDonHang;
import com.flowiee.app.danhmuc.service.TrangThaiDonHangService;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.common.action.DonHangAction;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.entity.DonHangChiTiet;
import com.flowiee.app.sanpham.entity.KhachHang;
import com.flowiee.app.sanpham.model.DonHangRequest;
import com.flowiee.app.sanpham.repository.DonHangRepository;
import com.flowiee.app.sanpham.services.BienTheSanPhamService;
import com.flowiee.app.sanpham.services.ChiTietDonHangService;
import com.flowiee.app.sanpham.services.DonHangService;
import com.flowiee.app.sanpham.services.ItemsService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
public class DonHangServiceImpl implements DonHangService {
    private static final Logger logger = LoggerFactory.getLogger(DonHangServiceImpl.class);
    private static final String module = SystemModule.SAN_PHAM.name();

    @Autowired
    private DonHangRepository donHangRepository;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;
    @Autowired
    private ChiTietDonHangService chiTietDonHangService;
    @Autowired
    private TrangThaiDonHangService trangThaiDonHangService;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private ItemsService itemsService;

    @Override
    public List<DonHang> findAll() {
        return donHangRepository.findAll();
    }

    @Override
    public List<DonHang> findAll(String searchTxt, String thoiGianDatHang,
                                 int kenhBanHangId, int trangThaiDonHangId) {
        return donHangRepository.findAll(searchTxt, kenhBanHangId, trangThaiDonHangId);
    }

    @Override
    public List<DonHang> findByTrangThai(int trangThaiDonHangId) {
        return donHangRepository.findByTrangThaiDonHang(trangThaiDonHangService.findById(trangThaiDonHangId));
    }

    @Override
    public List<DonHang> search() {
        return null;
    }

    @Override
    public List<DonHang> findByKhachHangId(int khachHangId) {
        return donHangRepository.findByKhachHangId(khachHangId);
    }

    @Override
    public List<DonHang> findByNhanVienId(int nhanVienId) {
        return donHangRepository.findByNhanvienId(nhanVienId);
    }

    @Override
    public DonHang findById(Integer id) {
        return donHangRepository.findById(id).orElse(null);
    }

    @Override
    public String save(DonHang entity) {
        if (entity == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        donHangRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    @Transactional
    public String save(DonHangRequest request) {
        try {
            DonHang donHang = new DonHang();
            donHang.setMaDonHang(FlowieeUtil.getMaDonHang());
            donHang.setKhachHang(new KhachHang(request.getKhachHang()));
            donHang.setKenhBanHang(new KenhBanHang(request.getKenhBanHang()));
            donHang.setNhanVienBanHang(new Account(request.getNhanVienBanHang()));
            donHang.setGhiChu(request.getGhiChu());
            donHang.setThoiGianDatHang(request.getThoiGianDatHang());
            donHang.setTrangThaiDonHang(new TrangThaiDonHang(request.getTrangThaiDonHang()));
            donHang.setTongTienDonHang(0D);
            donHangRepository.save(donHang);

            DonHang donHangSaved = donHangRepository.findDonHangMoiNhat().get(0);

            Double totalMoneyOfDonHang = 0D;
            for (int idBienTheSP : request.getListBienTheSanPham()) {
                int soLuongSanPhamInCart = itemsService.findSoLuongByBienTheSanPhamId(idBienTheSP);
                DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
                donHangChiTiet.setDonHang(donHangSaved);
                donHangChiTiet.setBienTheSanPham(bienTheSanPhamService.findById(idBienTheSP));
                donHangChiTiet.setGhiChu("");
                donHangChiTiet.setSoLuong(soLuongSanPhamInCart);
                donHangChiTiet.setTrangThai(true);
                chiTietDonHangService.save(donHangChiTiet);
                totalMoneyOfDonHang += bienTheSanPhamService.getGiaBan(idBienTheSP);
                //Update lại số lượng trong kho của sản phẩm
                bienTheSanPhamService.updateSoLuong(soLuongSanPhamInCart, idBienTheSP);
            }
            donHangSaved.setTongTienDonHang(totalMoneyOfDonHang);
            donHangRepository.save(donHangSaved);

            systemLogService.writeLog(module, DonHangAction.CREATE_DONHANG.name(), "Thêm mới đơn hàng: " + donHang.toString());
            logger.info(DonHangServiceImpl.class.getName() + ": Thêm mới đơn hàng " + donHang.toString());

            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String update(DonHang donHang, Integer id) {
        if (id <= 0 || this.findById(id) == null) {
            throw new NotFoundException();
        }
        donHang.setId(id);
        systemLogService.writeLog(module, DonHangAction.UPDATE_DONHANG.name(), "Cập nhật đơn hàng: " + donHang.toString());
        logger.info(DonHangServiceImpl.class.getName() + ": Cập nhật đơn hàng " + donHang.toString());
        donHangRepository.save(donHang);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer id) {
        DonHang donHang = this.findById(id);
        if (id <= 0 || donHang == null) {
            throw new NotFoundException();
        }
        donHangRepository.deleteById(id);
        systemLogService.writeLog(module, DonHangAction.DELETE_DONHANG.name(), "Xóa đơn hàng: " + donHang.toString());
        logger.info(DonHangServiceImpl.class.getName() + ": Xóa đơn hàng " + donHang.toString());
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public ResponseEntity<?> exportDanhSachDonHang() {
        String rootPath = "src/main/resources/static/templates/excel/";
        String filePathOriginal = rootPath + "Template_Export_DanhSachDonHang.xlsx";
        String filePathTemp = rootPath + "Template_Export_DanhSachDonHang_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(Files.copy(Path.of(filePathOriginal),
                                                                Path.of(filePathTemp),
                                                                StandardCopyOption.REPLACE_EXISTING).toFile());
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<DonHang> listData = this.findAll();
            for (int i = 0; i < listData.size(); i++) {
                XSSFRow row = sheet.createRow(i + 4);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(listData.get(i).getMaDonHang());
                row.createCell(2).setCellValue(listData.get(i).getThoiGianDatHang());
                row.createCell(3).setCellValue(listData.get(i).getKenhBanHang().getTenLoai());
                row.createCell(4).setCellValue(listData.get(i).getTongTienDonHang());
                row.createCell(5).setCellValue("");
                row.createCell(6).setCellValue(listData.get(i).getKhachHang().getTenKhachHang());
                row.createCell(7).setCellValue(listData.get(i).getKhachHang().getDiaChi());
                row.createCell(8).setCellValue(listData.get(i).getGhiChu());
                for (int j = 0; j <= 8; j++) {
                    row.getCell(j).setCellStyle(ExcelUtil.setBorder(workbook.createCellStyle()));
                }
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Template_Export_DanhSachDonHang.xlsx.xlsx");
            workbook.write(stream);
            workbook.close();
            //Xóa file temp
            File fileToDelete = new File(Path.of(filePathTemp).toUri());
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()), header, HttpStatus.CREATED);
        } catch (Exception e) {
            File fileToDelete = new File(Path.of(filePathTemp).toUri());
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("redirect:/don-hang");
        }
    }
}