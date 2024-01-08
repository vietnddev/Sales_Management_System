package com.flowiee.app.service.impl;

import com.flowiee.app.entity.*;
import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.exception.DataInUseException;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.model.request.OrderRequest;
import com.flowiee.app.entity.Category;
import com.flowiee.app.model.role.SystemAction.ProductAction;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.repository.OrderRepository;
import com.flowiee.app.service.*;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.ErrorMessages;

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
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private static final String module = SystemModule.PRODUCT.name();

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderPayService orderPayService;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private VoucherTicketService voucherTicketService;
    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public List<OrderDTO> findAllOrder() {
        return this.extractDataQuery(orderRepository.findAll((Integer) null));
    }

    @Override
    public List<OrderDTO> findAllOrder(Integer orderId) {
        return this.extractDataQuery(orderRepository.findAll(orderId));
    }

    @Override
    public List<Order> findByTrangThai(int orderStatusId) {
        return orderRepository.findByTrangThaiDonHang(orderStatusId);
    }

    @Override
    public List<Order> findByKhachHangId(int customerId) {
        return orderRepository.findByKhachHangId(customerId);
    }

    @Override
    public List<Order> findByNhanVienId(int customerId) {
        return orderRepository.findByNhanvienId(customerId);
    }

    @Override
    public OrderDTO findOrderById(Integer orderId) {
        List<OrderDTO> orders = findAllOrder(orderId);
        if (orders.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessages.SEARCH_ERROR_OCCURRED, "order " + orderId));
        }
        return orders.get(0);
    }

    @Override
    public String saveOrder(Order entity) {
        if (entity == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        orderRepository.save(entity);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    @Transactional
    public String saveOrder(OrderRequest request) {
        try {
            Order order = new Order();
            order.setMaDonHang(CommonUtil.getMaDonHang());
            order.setCustomer(new Customer(request.getKhachHang()));
            order.setKenhBanHang(new Category(request.getKenhBanHang(), null));
            order.setNhanVienBanHang(new Account(request.getNhanVienBanHang()));
            order.setGhiChu(request.getGhiChu());
            order.setThoiGianDatHang(request.getThoiGianDatHang());
            order.setTrangThaiDonHang(new Category(request.getTrangThaiDonHang(), null));
            order.setTotalAmount(0D);
            order.setTotalAmountDiscount(null);
            Order orderSaved = orderRepository.save(order);

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
                if (productVariantService.getGiaBan(idBienTheSP) != null) {
                    totalMoneyOfDonHang += productVariantService.getGiaBan(idBienTheSP);
                }
                //Update lại số lượng trong kho của sản phẩm
                //productVariantService.updateSoLuong(soLuongSanPhamInCart, idBienTheSP);
            }
            orderSaved.setTotalAmount(totalMoneyOfDonHang);
            orderSaved.setTotalAmountDiscount(null);
            
            VoucherTicket voucherTicket = voucherTicketService.findByCode(request.getVoucherUsedCode());
            if (voucherTicket != null) {
            	String statusCode = voucherTicketService.checkTicketToUse(request.getVoucherUsedCode());
            	if (AppConstants.VOUCHER_STATUS.INACTIVE.name().equals(statusCode)) {
            		orderSaved.setVoucherUsedCode(request.getVoucherUsedCode());
            		orderSaved.setAmountDiscount(request.getAmountDiscount());
            		orderSaved.setTotalAmountDiscount(totalMoneyOfDonHang - request.getAmountDiscount());
            	}

                voucherTicket.setCustomer(orderSaved.getCustomer());
                voucherTicket.setActiveTime(new Date());
                voucherTicket.setStatus(true);
                voucherTicketService.update(voucherTicket, voucherTicket.getId());
            }
            orderRepository.save(orderSaved);

            //Create QRCode
            fileStorageService.saveQRCodeOfOrder(orderSaved.getId());

            systemLogService.writeLog(module, ProductAction.PRO_ORDERS_CREATE.name(), "Thêm mới đơn hàng: " + order.toString());
            logger.info("Insert new order success! insertBy=" + CommonUtil.getCurrentAccountUsername());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
        	logger.error("Insert new order fail! order=" + request, e);
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
    public List<Order> findByCustomer(Integer customerId) {
        return orderRepository.findByCustomer(customerId);
    }

    @Override
    public Double findRevenueToday() {
        return orderRepository.findRevenueToday();
    }

    @Override
    public Double findRevenueThisMonth() {
        return orderRepository.findRevenueThisMonth();
    }

    @Override
    public List<Order> findOrdersToday() {
        return orderRepository.findOrdersToday();
    }

    @Override
    public String updateOrder(Order order, Integer id) {
        order.setId(id);
        orderRepository.save(order);
        systemLogService.writeLog(module, ProductAction.PRO_ORDERS_UPDATE.name(), "Cập nhật đơn hàng: " + order.toString());
        logger.info(OrderServiceImpl.class.getName() + ": Cập nhật đơn hàng " + order.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer id) {
        OrderDTO order = this.findOrderById(id);
        orderPayService.findByOrder(id).forEach(orderPay -> {
            if (orderPay.getPaymentStatus()) {
                throw new DataInUseException(ErrorMessages.ERROR_LOCKED);
            }
        });
        if ("DE".equals(order.getOrderStatus().getCode()) || "DO".equals(order.getOrderStatus().getCode())) {
            throw new DataInUseException(ErrorMessages.ERROR_LOCKED);
        }
        orderRepository.deleteById(id);
        systemLogService.writeLog(module, ProductAction.PRO_ORDERS_DELETE.name(), "Xóa đơn hàng: " + order.toString());
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
            List<OrderDTO> listData = this.findAllOrder();
            for (int i = 0; i < listData.size(); i++) {
                XSSFRow row = sheet.createRow(i + 4);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(listData.get(i).getOrderCode());
                row.createCell(2).setCellValue(listData.get(i).getOrderTime());
                row.createCell(3).setCellValue(listData.get(i).getSalesChannelName());
                row.createCell(4).setCellValue(listData.get(i).getTotalAmountAfterDiscount());
                row.createCell(5).setCellValue("");
                row.createCell(6).setCellValue(listData.get(i).getCustomerName());
                row.createCell(7).setCellValue("");//listData.get(i).getKhachHang().getDiaChi()
                row.createCell(8).setCellValue(listData.get(i).getNote());
                for (int j = 0; j <= 8; j++) {
                    row.getCell(j).setCellStyle(CommonUtil.setBorder(workbook.createCellStyle()));
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

    private List<OrderDTO> extractDataQuery(List<Object[]> objects) {
        List<OrderDTO> dataResponse = new ArrayList<>();
        for (Object[] data : objects) {
            OrderDTO order = new OrderDTO();
            order.setOrderId(Integer.parseInt(String.valueOf(data[0])));
            order.setOrderCode(String.valueOf(data[1]));
            order.setOrderTime(CommonUtil.convertStringToDate(String.valueOf(data[2]), "yyyy-MM-dd HH:mm:ss.SSSSSS"));
            order.setReceiverAddress(String.valueOf(data[3]) != null ? String.valueOf(data[3]) : "-");
            order.setReceiverPhone(String.valueOf(data[4]) != null ? String.valueOf(data[4]) : "-");
            order.setReceiverName(String.valueOf(data[5]));
            order.setReceiverEmail(String.valueOf(data[23]));
            order.setOrderBy(new Customer(Integer.parseInt(String.valueOf(data[6])), String.valueOf(data[7])));
            order.setTotalAmount(Double.parseDouble(String.valueOf(data[8])));
            order.setSalesChannel(new Category(Integer.parseInt(String.valueOf(data[9])), String.valueOf(data[10])));
            order.setSalesChannelId(Integer.parseInt(String.valueOf(data[9])));
            order.setSalesChannelName(String.valueOf(data[10]));
            order.setNote(String.valueOf(data[11]) != null ? String.valueOf(data[11]) : "-");
            order.setOrderStatus(new Category(Integer.parseInt(String.valueOf(data[12])), String.valueOf(data[13])));
            order.setOrderPay(new OrderPay(Integer.parseInt(String.valueOf(data[14])), Boolean.parseBoolean(String.valueOf(data[15]))));
            order.setPayMethod(new Category(Integer.parseInt(String.valueOf(data[16])), String.valueOf(data[17])));
            order.setCashier(new Account(Integer.parseInt(String.valueOf(data[18])), null, String.valueOf(data[19])));
            order.setCreatedBy(new Account(Integer.parseInt(String.valueOf(data[20]))));
            order.setCreatedAt(CommonUtil.convertStringToDate(String.valueOf(data[21])));
            order.setQrCode(String.valueOf(data[22]));
            dataResponse.add(order);
        }
        return dataResponse;
    }
}