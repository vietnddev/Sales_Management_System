package com.flowiee.app.service.impl;

import com.flowiee.app.entity.*;
import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.exception.DataInUseException;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.repository.OrderDetailRepository;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.entity.Category;
import com.flowiee.app.model.role.SystemAction.ProductAction;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.repository.OrderRepository;
import com.flowiee.app.service.*;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.DateUtils;

import com.flowiee.app.utils.MessageUtils;
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

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;
    private final SystemLogService systemLogService;
    private final CartService cartService;
    private final PriceService priceService;
    private final VoucherTicketService voucherTicketService;
    private final FileStorageService fileStorageService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, ProductService productService, SystemLogService systemLogService, CartService cartService, PriceService priceService, VoucherTicketService voucherTicketService, FileStorageService fileStorageService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productService = productService;
        this.systemLogService = systemLogService;
        this.cartService = cartService;
        this.priceService = priceService;
        this.voucherTicketService = voucherTicketService;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public List<OrderDTO> findAllOrder() {
        return this.extractDataQuery(orderRepository.findAll((Integer) null));
    }

    @Override
    public List<OrderDTO> findAllOrder(Integer orderId) {
        return this.extractDataQuery(orderRepository.findAll(orderId));
    }

    @Override
    public List<OrderDetail> findOrderDetailsByOrderId(Integer orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public OrderDTO findOrderById(Integer orderId) {
        List<OrderDTO> orders = findAllOrder(orderId);
        if (orders.isEmpty()) {
            throw new NotFoundException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "order " + orderId));
        }
        List<OrderDetail> listOrderDetail = this.findOrderDetailsByOrderId(orderId);
        int totalProduct = 0;
        double totalAmount = 0;
        for (OrderDetail d : listOrderDetail) {
            totalProduct += d.getSoLuong();
            totalAmount += d.getPrice() * d.getSoLuong();
        }
        OrderDTO dto = orders.get(0);
        dto.setListOrderDetail(listOrderDetail);
        dto.setTotalProduct(totalProduct);
        dto.setTotalAmount(totalAmount);
        dto.setTotalAmountDiscount(dto.getTotalAmount() - dto.getAmountDiscount());
        return orders.get(0);
    }

    @Override
    public String saveOrder(OrderDTO request) {
        try {
            //Insert order
            Order order = new Order();
            order.setMaDonHang(CommonUtils.getMaDonHang());
            order.setCustomer(new Customer(request.getCustomerId()));
            order.setKenhBanHang(new Category(request.getSalesChannelId(), null));
            order.setNhanVienBanHang(new Account(request.getCashierId()));
            order.setGhiChu(request.getNote());
            order.setThoiGianDatHang(request.getOrderTime());
            order.setTrangThaiDonHang(new Category(request.getOrderStatusId(), null));

            if (request.getOrderTimeStr() != null) {
                Date orderTime = DateUtils.convertStringToDate("dd/MM/yyyy hh:mm a", "dd/MM/yyyy HH:mm:ss", request.getOrderTimeStr());
                order.setThoiGianDatHang(orderTime);
            } else {
                order.setThoiGianDatHang(new Date());
            }
            order.setPaymentStatus(false);

            Order orderSaved = orderRepository.save(order);

            //QRCode
            fileStorageService.saveQRCodeOfOrder(orderSaved.getId());

            //Insert items detail
            for (Items items : cartService.findCartById(request.getCartId()).getListItems()) {
                ProductVariant productVariant = productService.findProductVariantById(items.getProductVariant().getId());
                Price price = priceService.findGiaHienTai(productVariant.getId());
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(orderSaved);
                orderDetail.setProductVariant(productVariant);
                orderDetail.setSoLuong(cartService.findSoLuongByBienTheSanPhamId(items.getOrderCart().getId() , productVariant.getId()));
                orderDetail.setTrangThai(true);
                orderDetail.setGhiChu(items.getGhiChu());
                orderDetail.setPrice(price != null ? Float.parseFloat(String.valueOf(price.getGiaBan())) : 0);
                orderDetail.setPriceOriginal(price != null ? Float.parseFloat(String.valueOf(price.getGiaBan())) : 0);
                this.saveOrderDetail(orderDetail);
            }

            //Update voucher ticket status
            VoucherTicket voucherTicket = voucherTicketService.findByCode(request.getVoucherUsedCode());
            if (voucherTicket != null) {
                String statusCode = voucherTicketService.checkTicketToUse(request.getVoucherUsedCode());
                if (AppConstants.VOUCHER_STATUS.ACTIVE.name().equals(statusCode)) {
                    orderSaved.setVoucherUsedCode(request.getVoucherUsedCode());
                    orderSaved.setAmountDiscount(request.getAmountDiscount());
                }

                voucherTicket.setCustomer(orderSaved.getCustomer());
                voucherTicket.setActiveTime(new Date());
                voucherTicket.setStatus(true);
                voucherTicketService.update(voucherTicket, voucherTicket.getId());
            }
            orderRepository.save(orderSaved);

            //Sau khi đã lưu đơn hàng thì xóa all items
            cartService.deleteAllItems();

            //Log
            systemLogService.writeLog(module, ProductAction.PRO_ORDERS_CREATE.name(), "Thêm mới đơn hàng: " + order.toString());
            logger.info("Insert new order success! insertBy=" + CommonUtils.getCurrentAccountUsername());

            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            logger.error("Insert new order fail! order=" + request, e);
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String saveOrderDetail(OrderDetail orderDetail) {
        try {
            orderDetailRepository.save(orderDetail);
            systemLogService.writeLog(module, ProductAction.PRO_ORDERS_CREATE.name(), "Thêm mới item vào đơn hàng: " + orderDetail.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Thêm mới item vào đơn hàng " + orderDetail.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
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
    public String updateOrderDetail(OrderDetail orderDetail, Integer orderDetailId) {
        try {
            orderDetail.setId(orderDetailId);
            orderDetailRepository.save(orderDetail);
            systemLogService.writeLog(module, ProductAction.PRO_ORDERS_UPDATE.name(), "Cập nhật item of đơn hàng: " + orderDetail.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Cập nhật item of đơn hàng " + orderDetail.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String deleteOrder(Integer id) {
        OrderDTO order = this.findOrderById(id);
        if (order.isPaymentStatus()) {
            throw new DataInUseException(MessageUtils.ERROR_LOCKED);
        }
        if ("DE".equals(order.getOrderStatus().getCode()) || "DO".equals(order.getOrderStatus().getCode())) {
            throw new DataInUseException(MessageUtils.ERROR_LOCKED);
        }
        orderRepository.deleteById(id);
        systemLogService.writeLog(module, ProductAction.PRO_ORDERS_DELETE.name(), "Xóa đơn hàng: " + order.toString());
        logger.info(OrderServiceImpl.class.getName() + ": Xóa đơn hàng " + order.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String deleteOrderDetail(Integer orderDetailId) {
        OrderDetail orderDetail = this.findOrderDetailById(orderDetailId);
        try {
            orderDetailRepository.deleteById(orderDetailId);
            systemLogService.writeLog(module, ProductAction.PRO_ORDERS_DELETE.name(), "Xóa item of đơn hàng: " + orderDetail.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Xóa item of đơn hàng " + orderDetail.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Transactional
    @Override
    public String doPay(Integer orderId, Date paymentTime, Integer paymentMethod, String note) {
        orderRepository.updatePaymentStatus(orderId, paymentTime, paymentMethod, note);
        return "Thanh toán thành công!";
    }

    @Override
    public List<Order> findByStaffId(Integer customerId) {
        return orderRepository.findByNhanvienId(customerId);
    }

    @Override
    public List<Order> findOrdersBySalesChannelId(Integer salesChannelId) {
        return orderRepository.findBySalesChannel(salesChannelId);
    }

    @Override
    public List<Order> findOrdersByStatus(Integer orderStatusId) {
        return orderRepository.findByOrderStatus(orderStatusId);
    }

    @Override
    public List<Order> findOrdersByCustomerId(Integer customerId) {
        return orderRepository.findByKhachHangId(customerId);
    }

    @Override
    public List<Order> findOrdersByPaymentMethodId(Integer paymentMethodId) {
        return orderRepository.findByPaymentMethodId(paymentMethodId);
    }

    @Override
    public List<Order> findOrdersToday() {
        return orderRepository.findOrdersToday();
    }

    @Override
    public OrderDetail findOrderDetailById(Integer orderDetailId) {
        return orderDetailRepository.findById(orderDetailId).orElse(null);
    }

    @Override
    public Double findRevenueToday() {
        return orderRepository.findRevenueToday();
    }

    @Override
    public Double findRevenueThisMonth() {
        return 0D;//orderRepository.findRevenueThisMonth();
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
                row.createCell(4).setCellValue(listData.get(i).getTotalAmountDiscount());
                row.createCell(5).setCellValue("");
                row.createCell(6).setCellValue(listData.get(i).getCustomerName());
                row.createCell(7).setCellValue("");//listData.get(i).getKhachHang().getDiaChi()
                row.createCell(8).setCellValue(listData.get(i).getNote());
                for (int j = 0; j <= 8; j++) {
                    row.getCell(j).setCellStyle(CommonUtils.setBorder(workbook.createCellStyle()));
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
            order.setOrderTime(DateUtils.convertStringToDate(String.valueOf(data[2]), "yyyy-MM-dd HH:mm:ss.SSSSSS"));
            order.setOrderTimeStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getOrderTime()));
            order.setReceiveAddress(String.valueOf(data[3]) != null ? String.valueOf(data[3]) : "-");
            order.setReceivePhone(String.valueOf(data[4]) != null ? String.valueOf(data[4]) : "-");
            order.setReceiveName(String.valueOf(data[5]));
            order.setReceiveEmail(String.valueOf(data[19]));
            order.setOrderBy(new Customer(Integer.parseInt(String.valueOf(data[6])), String.valueOf(data[7])));
            order.setAmountDiscount(data[8] != null ? Double.parseDouble(String.valueOf(data[8])) : 0);
            order.setSalesChannel(new Category(Integer.parseInt(String.valueOf(data[9])), String.valueOf(data[10])));
            order.setSalesChannelId(Integer.parseInt(String.valueOf(data[9])));
            order.setSalesChannelName(String.valueOf(data[10]));
            order.setNote(String.valueOf(data[11]) != null ? String.valueOf(data[11]) : "-");
            order.setOrderStatus(new Category(Integer.parseInt(String.valueOf(data[12])), String.valueOf(data[13])));
            order.setPayMethodName(String.valueOf(data[15]));
            if (data[22] != null) {
                order.setPaymentTime(DateUtils.convertStringToDate(String.valueOf(data[22]), "yyyy-MM-dd HH:mm:ss.SSSSSS"));
            }
            order.setPaymentTimeStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getPaymentTime()));
            order.setCreatedBy(new Account(Integer.parseInt(String.valueOf(data[16]))));
            order.setCreatedAt(DateUtils.convertStringToDate(String.valueOf(data[17]), "yyyy-MM-dd HH:mm:ss.SSSSSS"));
            order.setCreatedAtStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getCreatedAt()));
            order.setQrCode(String.valueOf(data[18]));
            order.setVoucherUsedCode(data[20] != null ? String.valueOf(data[20]) : "-");
            order.setPaymentStatus(data[21] != null && Boolean.parseBoolean(String.valueOf(data[21])));
            dataResponse.add(order);
        }
        return dataResponse;
    }
}