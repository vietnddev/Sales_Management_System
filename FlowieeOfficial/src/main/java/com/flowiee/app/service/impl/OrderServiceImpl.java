package com.flowiee.app.service.impl;

import com.flowiee.app.entity.*;
import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.FlowieeUtil;
import com.flowiee.app.model.request.OrderRequest;
import com.flowiee.app.entity.Category;
import com.flowiee.app.model.role.DonHangAction;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.repository.OrderRepository;
import com.flowiee.app.service.*;
import com.flowiee.app.service.SystemLogService;

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

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    @Autowired
    private EntityManager entityManager;

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
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        orderRepository.save(entity);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
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

            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.SERVICE_RESPONSE_FAIL;
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
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer id) {
        Order order = this.findById(id);
        orderRepository.deleteById(id);
        systemLogService.writeLog(module, DonHangAction.DELETE_DONHANG.name(), "Xóa đơn hàng: " + order.toString());
        logger.info(OrderServiceImpl.class.getName() + ": Xóa đơn hàng " + order.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
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
                    row.getCell(j).setCellStyle(FlowieeUtil.setBorder(workbook.createCellStyle()));
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

    private List<OrderDTO> findData() {
        List<OrderDTO> dataResponse = new ArrayList<>();
        StringBuilder strSQL = new StringBuilder("SELECT ");
        strSQL.append("o.ID as ORDER_ID_0, o.MA_DON_HANG as MA_DON_HANG_1, o.THOI_GIAN_DAT_HANG as ORDER_TIME_2, o.RECEIVER_ADDRESS as RECEIVER_ADDRESS_3,");
        strSQL.append("o.RECEIVER_PHONE as RECEIVER_PHONE_4,o.RECEIVER_NAME as RECEIVER_NAME_5, c.ID as ORDERBY_ID_6, c.TEN_KHACH_HANG as ORDER_BY_NAME_7,");
        strSQL.append("o.TONG_TIEN_DON_HANG as TOTAL_AMOUNT_8, sc.ID as SALES_CHANNEL_ID_9, sc.NAME as SALES_CHANNEL_NAME_10, o.GHI_CHU as NOTE_11, ");
        strSQL.append("os.ID as ORDER_STATUS_ID_12, os.NAME as ORDER_STATUS_NAME_13, op.ID as ORDER_PAY_ID_14, op.PAYMENT_STATUS as ORDER_PAY_STATUS_15, ");
        strSQL.append("pm.ID as PAYMENT_METHOD_ID_16, pm.NAME as PAYMENT_METHOD_NAME_17, acc.ID as CASHIER_ID_18, acc.HO_TEN as CASHIER_NAME_19, ");
        strSQL.append("o.CREATED_BY as CREATED_BY_ID_20, o.CREATED_AT as CREATED_AT_21");
        strSQL.append("FROM PRO_DON_HANG o ");
        strSQL.append("LEFT JOIN PRO_CUSTOMER c ON c.ID = o.CUSTOMER_ID ");
        strSQL.append("LEFT JOIN PRO_DON_HANG_THANH_TOAN op ON op.DON_HANG_ID = o.ID ");
        strSQL.append("LEFT JOIN SYS_ACCOUNT acc ON acc.ID = op.CASHIER ");
        strSQL.append("LEFT JOIN (SELECT * FROM CATEGORY WHERE TYPE = 'SALESCHANNEL') sc ON sc.ID = o.KENH_BAN_HANG ");
        strSQL.append("LEFT JOIN (SELECT * FROM CATEGORY WHERE TYPE = 'ORDERSTATUS') os ON os.ID = o.TRANG_THAI_DON_HANG ");
        strSQL.append("LEFT JOIN (SELECT * FROM CATEGORY WHERE TYPE = 'PAYMETHOD') pm ON pm.ID = op.HINH_THUC_THANH_TOAN");
        logger.info("[SQL findData]: " + strSQL.toString());
        Query query = entityManager.createNativeQuery(strSQL.toString());
        List<Object[]> listData = query.getResultList();
        for (Object[] data : listData) {
            OrderDTO order = new OrderDTO();
            order.setOrderId(Integer.parseInt(String.valueOf(data[0])));
            order.setOrderCode(String.valueOf(data[1]));
            order.setOrderTime(FlowieeUtil.convertStringToDate(String.valueOf(data[2])));
            order.setReceiverAddress(String.valueOf(data[3]));
            order.setReceiverPhone(String.valueOf(data[4]));
            order.setReceiverName(String.valueOf(data[5]));
            order.setOrderBy(new Customer(Integer.parseInt(String.valueOf(data[6])), String.valueOf(data[7])));
            order.setTotalAmount(Double.parseDouble(String.valueOf(data[8])));
            order.setSalesChannel(new Category(Integer.parseInt(String.valueOf(data[9])), String.valueOf(data[10])));
            order.setNote(String.valueOf(data[11]));
            order.setOrderStatus(new Category(Integer.parseInt(String.valueOf(data[12])), String.valueOf(data[13])));
            order.setOrderPay(new OrderPay(Integer.parseInt(String.valueOf(data[14])), Boolean.parseBoolean(String.valueOf(data[15]))));
            order.setPayMethod(new Category(Integer.parseInt(String.valueOf(data[16])), String.valueOf(data[17])));
            order.setCashier(new Account(Integer.parseInt(String.valueOf(data[18])), null, String.valueOf(data[19])));
            order.setCreatedBy(new Account(Integer.parseInt(String.valueOf(data[20]))));
            order.setCreatedAt(FlowieeUtil.convertStringToDate(String.valueOf(data[21])));
            dataResponse.add(order);
        }
        return dataResponse;
    }
}