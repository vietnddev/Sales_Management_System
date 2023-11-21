package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.Order;
import com.flowiee.app.entity.OrderDetail;
import com.flowiee.app.model.product.OrderRequest;
import com.flowiee.app.category.Category;
import com.flowiee.app.common.action.DonHangAction;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.repository.OrderRepository;
import com.flowiee.app.service.product.*;
import com.flowiee.app.service.system.SystemLogService;

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
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private static final String module = SystemModule.SAN_PHAM.name();

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private CustomerContactService customerContactService;

    @Override
    public List<Order> findAll() {
        List<Order> listOrder = new ArrayList<>();
        for (Order order : orderRepository.findAll()) {
            order.getCustomer().setPhoneDefault(customerContactService.findPhoneUseDefault(order.getCustomer().getId()));
            order.getCustomer().setEmailDefault(customerContactService.findEmailUseDefault(order.getCustomer().getId()));
            order.getCustomer().setAddressDefault(customerContactService.findAddressUseDefault(order.getCustomer().getId()));
            listOrder.add(order);
        }
        return listOrder;
    }

    @Override
    public List<Order> findAll(String searchTxt, String thoiGianDatHang,
                               int kenhBanHangId, int trangThaiDonHangId) {
        return orderRepository.findAll(searchTxt, kenhBanHangId, trangThaiDonHangId);
    }

    @Override
    public List<Order> findByTrangThai(int trangThaiDonHangId) {
        return orderRepository.findByTrangThaiDonHang(trangThaiDonHangId);
    }

    @Override
    public List<Order> search() {
        return null;
    }

    @Override
    public List<Order> findByKhachHangId(int khachHangId) {
        return orderRepository.findByKhachHangId(khachHangId);
    }

    @Override
    public List<Order> findByNhanVienId(int nhanVienId) {
        return orderRepository.findByNhanvienId(nhanVienId);
    }

    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public String save(Order entity) {
        if (entity == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        orderRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    @Transactional
    public String save(OrderRequest request) {
        try {
            Order order = new Order();
            order.setMaDonHang(FlowieeUtil.getMaDonHang());
            order.setCustomer(new Customer(request.getKhachHang()));
            order.setKenhBanHang(new Category(request.getKenhBanHang(), null));
            order.setNhanVienBanHang(new Account(request.getNhanVienBanHang()));
            order.setGhiChu(request.getGhiChu());
            order.setThoiGianDatHang(request.getThoiGianDatHang());
            order.setTrangThaiDonHang(new Category(request.getTrangThaiDonHang(), null));
            order.setTongTienDonHang(0D);
            orderRepository.save(order);

            Order orderSaved = orderRepository.findDonHangMoiNhat().get(0);

            Double totalMoneyOfDonHang = 0D;
            for (int idBienTheSP : request.getListBienTheSanPham()) {
                int soLuongSanPhamInCart = itemsService.findSoLuongByBienTheSanPhamId(idBienTheSP);
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(orderSaved);
                orderDetail.setProductVariant(productVariantService.findById(idBienTheSP));
                orderDetail.setGhiChu("");
                orderDetail.setSoLuong(soLuongSanPhamInCart);
                orderDetail.setTrangThai(true);
                orderDetailService.save(orderDetail);
                totalMoneyOfDonHang += productVariantService.getGiaBan(idBienTheSP);
                //Update lại số lượng trong kho của sản phẩm
                //productVariantService.updateSoLuong(soLuongSanPhamInCart, idBienTheSP);
            }
            orderSaved.setTongTienDonHang(totalMoneyOfDonHang);
            orderRepository.save(orderSaved);

            systemLogService.writeLog(module, DonHangAction.CREATE_DONHANG.name(), "Thêm mới đơn hàng: " + order.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Thêm mới đơn hàng " + order.toString());

            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public List<Order> findBySalesChannel(Integer salesChannelId) {
        return orderRepository.findBySalesChannel(salesChannelId);
    }

    @Override
    public List<Order> findByOrderStatus(Integer orderStatusId) {
        return orderRepository.findByOrderStatus(orderStatusId);
    }

    @Override
    public String update(Order order, Integer id) {
        order.setId(id);
        orderRepository.save(order);
        systemLogService.writeLog(module, DonHangAction.UPDATE_DONHANG.name(), "Cập nhật đơn hàng: " + order.toString());
        logger.info(OrderServiceImpl.class.getName() + ": Cập nhật đơn hàng " + order.toString());
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer id) {
        Order order = this.findById(id);
        orderRepository.deleteById(id);
        systemLogService.writeLog(module, DonHangAction.DELETE_DONHANG.name(), "Xóa đơn hàng: " + order.toString());
        logger.info(OrderServiceImpl.class.getName() + ": Xóa đơn hàng " + order.toString());
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
            List<Order> listData = this.findAll();
            for (int i = 0; i < listData.size(); i++) {
                XSSFRow row = sheet.createRow(i + 4);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(listData.get(i).getMaDonHang());
                row.createCell(2).setCellValue(listData.get(i).getThoiGianDatHang());
                row.createCell(3).setCellValue(listData.get(i).getKenhBanHang().getName());
                row.createCell(4).setCellValue(listData.get(i).getTongTienDonHang());
                row.createCell(5).setCellValue("");
                row.createCell(6).setCellValue(listData.get(i).getCustomer().getTenKhachHang());
                row.createCell(7).setCellValue("");//listData.get(i).getKhachHang().getDiaChi()
                row.createCell(8).setCellValue(listData.get(i).getGhiChu());
                for (int j = 0; j <= 8; j++) {
                    row.getCell(j).setCellStyle(FileUtil.setBorder(workbook.createCellStyle()));
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