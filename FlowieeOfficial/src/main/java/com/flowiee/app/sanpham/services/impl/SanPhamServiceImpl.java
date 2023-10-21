package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.CurrencyUtil;
import com.flowiee.app.common.utils.ExcelUtil;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.common.action.SanPhamAction;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.sanpham.entity.SanPham;
import com.flowiee.app.sanpham.repository.SanPhamRepository;
import com.flowiee.app.sanpham.services.SanPhamService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Clock;
import java.time.Instant;
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
    @Autowired
    private EntityManager entityManager;

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
    public SanPham findById(Integer id) {
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
            sanPham.setCreatedBy(FlowieeUtil.ACCOUNT_ID + "");
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
    public String update(SanPham sanPham, Integer id) {
        if (id <= 0 || this.findById(id) == null) {
            throw new NotFoundException();
        }
        SanPham sanPhamBefore = this.findById(id);
        try {
            sanPham.setId(id);
            sanPham.setLastUpdatedBy(FlowieeUtil.ACCOUNT_ID + "");
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
    public String delete(Integer id) {
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

    @Override
    public byte[] exportData(List<Integer> listSanPhamId) {
        StringBuilder strSQL = new StringBuilder("SELECT ");
        strSQL.append("lsp.TEN_LOAI as LOAI_SAN_PHAM, ").append("spbt.MA_SAN_PHAM, ").append("spbt.TEN_BIEN_THE, ").append("sz.TEN_LOAI as KICH_CO, ").append("cl.TEN_LOAI as MAU_SAC, ")
              .append("(SELECT spg.GIA_BAN FROM san_pham_gia spg WHERE spg.BIEN_THE_ID = spbt.ID AND spg.TRANG_THAI = 1) as GIA_BAN, ")
              .append("spbt.SO_LUONG_KHO, ").append("spbt.DA_BAN ");
        strSQL.append("FROM san_pham sp ");
        strSQL.append("LEFT JOIN san_pham_bien_the spbt ").append("on sp.ID = spbt.SAN_PHAM_ID ");
        strSQL.append("LEFT JOIN dm_loai_san_pham lsp ").append("on sp.LOAI_SAN_PHAM = lsp.ID ");
        strSQL.append("LEFT JOIN dm_loai_kich_co sz ").append(" on spbt.KICH_CO_ID = sz.ID ");
        strSQL.append("LEFT JOIN dm_loai_mau_sac cl on ").append(" spbt.MAU_SAC_ID = cl.ID ");
        strSQL.append("WHERE spbt.ID > 0 ");
        if (listSanPhamId != null) {
            strSQL.append("AND sp.ID IN (sp.ID)");
        } else {
            strSQL.append("AND  sp.ID IN (sp.ID)");
        }
        Query result = entityManager.createNativeQuery(strSQL.toString());
        List<Object[]> listData = result.getResultList();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        String filePathOriginal = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + FileUtil.TEMPLATE_E_SANPHAM + ".xlsx";
        String filePathTemp = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + FileUtil.TEMPLATE_E_SANPHAM + "_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
        File fileDeleteAfterExport = new File(Path.of(filePathTemp).toUri());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(Files.copy(Path.of(filePathOriginal),
                                                     Path.of(filePathTemp),
                                                     StandardCopyOption.REPLACE_EXISTING).toFile());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 0; i < listData.size(); i++) {
                XSSFRow row = sheet.createRow(i + 3);

                String loaiSanPham = listData.get(i)[0] != null ? String.valueOf(listData.get(i)[0]) : "";
                String maSanPham = listData.get(i)[1] != null ? String.valueOf(listData.get(i)[1]) : "";
                String tenSanPham = listData.get(i)[2] != null ? String.valueOf(listData.get(i)[2]) : "";
                String kichCo = listData.get(i)[3] != null ? String.valueOf(listData.get(i)[3]) : "";
                String mauSac = listData.get(i)[4] != null ? String.valueOf(listData.get(i)[4]) : "";
                Double giaBan = listData.get(i)[5] != null ? Double.parseDouble(String.valueOf(listData.get(i)[5])) : 0;
                String soLuong = listData.get(i)[6] != null ? String.valueOf(listData.get(i)[6]) : "0";
                String daBan = listData.get(i)[7] != null ? String.valueOf(listData.get(i)[7]) : "";

                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(loaiSanPham);
                row.createCell(2).setCellValue(maSanPham);
                row.createCell(3).setCellValue(tenSanPham);
                row.createCell(4).setCellValue(kichCo);
                row.createCell(5).setCellValue(mauSac);
                row.createCell(6).setCellValue(CurrencyUtil.formatToVND(giaBan));
                row.createCell(7).setCellValue(soLuong);
                row.createCell(8).setCellValue(daBan);

                for (int j = 0; j <= 8; j++) {
                    row.getCell(j).setCellStyle(ExcelUtil.setBorder(workbook.createCellStyle()));
                }
            }
            workbook.write(stream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileDeleteAfterExport.exists()) {
                fileDeleteAfterExport.delete();
            }
        }
        return stream.toByteArray();
    }
}