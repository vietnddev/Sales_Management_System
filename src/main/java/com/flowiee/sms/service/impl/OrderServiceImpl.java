package com.flowiee.sms.service.impl;

import com.flowiee.sms.core.exception.AppException;
import com.flowiee.sms.core.exception.BadRequestException;
import com.flowiee.sms.model.ACTION;
import com.flowiee.sms.model.MODULE;
import com.flowiee.sms.model.dto.OrderDetailDTO;
import com.flowiee.sms.entity.*;
import com.flowiee.sms.model.dto.OrderDTO;
import com.flowiee.sms.core.exception.DataInUseException;
import com.flowiee.sms.core.exception.NotFoundException;
import com.flowiee.sms.model.OrderDetailRpt;
import com.flowiee.sms.repository.OrderDetailRepository;
import com.flowiee.sms.repository.OrderHistoryRepository;
import com.flowiee.sms.utils.*;
import com.flowiee.sms.entity.Category;
import com.flowiee.sms.repository.OrderRepository;
import com.flowiee.sms.service.*;
import com.flowiee.sms.service.SystemLogService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Clock;
import java.time.Instant;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private static final String module = MODULE.PRODUCT.name();

    private final OrderRepository orderRepo;
    private final OrderDetailRepository orderDetailRepo;
    private final ProductService productService;
    private final SystemLogService systemLogService;
    private final CartService cartService;
    private final VoucherService voucherService;
    private final FileStorageService fileStorageService;
    private final OrderHistoryRepository orderHistoryRepo;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepo, OrderDetailRepository orderDetailRepo, ProductService productService,
                            SystemLogService systemLogService, CartService cartService, VoucherService voucherService,
                            FileStorageService fileStorageService, OrderHistoryRepository orderHistoryRepo) {
        this.orderRepo = orderRepo;
        this.orderDetailRepo = orderDetailRepo;
        this.productService = productService;
        this.systemLogService = systemLogService;
        this.cartService = cartService;
        this.voucherService = voucherService;
        this.fileStorageService = fileStorageService;
        this.orderHistoryRepo = orderHistoryRepo;
    }

    @Override
    public List<OrderDTO> findAllOrder() {
        return OrderDTO.fromOrders(orderRepo.findAll((Integer) null, null, Pageable.unpaged()).getContent());
    }

    @Override
    public Page<OrderDTO> findAllOrder(int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("orderTime").descending());
        Page<Order> orders = orderRepo.findAll((Integer) null,null, pageable);
        return new PageImpl<>(OrderDTO.fromOrders(orders.getContent()), pageable, orders.getTotalElements());
    }

    @Override
    public List<OrderDTO> findAllOrder(Integer orderId) {
        return OrderDTO.fromOrders(orderRepo.findAll(orderId, null, Pageable.unpaged()).getContent());
    }

    @Override
    public List<OrderDetail> findOrderDetailsByOrderId(Integer orderId) {
        return orderDetailRepo.findByOrderId(orderId);
    }

    @Override
    public OrderDTO findOrderById(Integer orderId) {
        List<OrderDTO> orders = findAllOrder(orderId);
        if (orders.isEmpty()) {
            throw new NotFoundException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "order " + orderId));
        }
        List<OrderDetail> listOrderDetail = this.findOrderDetailsByOrderId(orderId);
        int totalProduct = 0;
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderDetail d : listOrderDetail) {
            totalProduct += d.getQuantity();
            totalAmount = totalAmount.add(d.getPrice().multiply(BigDecimal.valueOf(d.getQuantity())));
        }
        OrderDTO dto = orders.get(0);
        dto.setListOrderDetailDTO(OrderDetailDTO.fromOrderDetails(listOrderDetail));
        dto.setTotalProduct(totalProduct);
        dto.setTotalAmount(totalAmount);
        dto.setTotalAmountDiscount(dto.getTotalAmount().subtract(dto.getAmountDiscount()));
        return orders.get(0);
    }

    @Transactional
    @Override
    public OrderDTO saveOrder(OrderDTO request) {
        try {
            //Insert order
            Order order = new Order();
            order.setCode(CommonUtils.getMaDonHang());
            order.setCustomer(new Customer(request.getCustomerId()));
            order.setKenhBanHang(new Category(request.getSalesChannelId(), null));
            order.setNhanVienBanHang(new Account(request.getCashierId()));
            order.setNote(request.getNote());
            if (request.getOrderTime() != null) {
                order.setOrderTime(request.getOrderTime());
            } else {
                order.setOrderTime(new Date());
            }
            order.setTrangThaiDonHang(new Category(request.getOrderStatusId(), null));
            order.setReceiverName(request.getReceiverName());
            order.setReceiverPhone(request.getReceiverPhone());
            order.setReceiverEmail(request.getReceiverEmail());
            order.setReceiverAddress(request.getReceiverAddress());
            if (request.getPayMethodId() != null) {
                order.setPaymentMethod(new Category(request.getPayMethodId(), null));
            }
            order.setPaymentStatus(false);

            if (ObjectUtils.isNotEmpty(request.getVoucherUsedCode())) {
                order.setVoucherUsedCode(request.getVoucherUsedCode());
            }
            if (ObjectUtils.isNotEmpty(request.getAmountDiscount())) {
                order.setAmountDiscount(request.getAmountDiscount());
            }

            Order orderSaved = orderRepo.save(order);

            //QRCode
            fileStorageService.saveQRCodeOfOrder(orderSaved.getId());

            //Insert items detail
            for (Items items : cartService.findCartById(request.getCartId()).getListItems()) {
                ProductDetail productDetail = productService.findProductVariantById(items.getProductDetail().getId());
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(orderSaved);
                orderDetail.setProductDetail(productDetail);
                orderDetail.setQuantity(cartService.findSoLuongByBienTheSanPhamId(items.getOrderCart().getId() , productDetail.getId()));
                orderDetail.setStatus(true);
                orderDetail.setGhiChu(items.getNote());
                orderDetail.setPrice(productDetail.getDiscountPrice());
                orderDetail.setPriceOriginal(productDetail.getOriginalPrice());
                this.saveOrderDetail(orderDetail);
            }

            //Update voucher ticket status
            VoucherTicket voucherTicket = voucherService.findTicketByCode((request.getVoucherUsedCode()));
            if (voucherTicket != null) {
//                String statusCode = voucherService.checkTicketToUse(request.getVoucherUsedCode());
//                if (AppConstants.VOUCHER_STATUS.ACTIVE.name().equals(statusCode)) {
//                    orderSaved.setVoucherUsedCode(request.getVoucherUsedCode());
//                    orderSaved.setAmountDiscount(request.getAmountDiscount());
//                }
                voucherTicket.setCustomer(orderSaved.getCustomer());
                voucherTicket.setActiveTime(new Date());
                voucherTicket.setStatus(true);
                voucherService.updateTicket(voucherTicket, voucherTicket.getId());
            }
            //orderRepo.save(orderSaved);

            //Sau khi đã lưu đơn hàng thì xóa all items
            cartService.deleteAllItems();

            //Log
            systemLogService.writeLog(module, ACTION.PRO_ORDERS_CREATE.name(), "Thêm mới đơn hàng: " + order.toString());
            logger.info("Insert new order success! insertBy=" + CommonUtils.getCurrentAccountUsername());

            return OrderDTO.fromOrder(orderSaved);
        } catch (Exception e) {
            logger.error("Insert new order fail! order=" + request, e);
            throw new AppException();
        }
    }

    @Override
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        try {
            OrderDetail orderDetailSaved = orderDetailRepo.save(orderDetail);
            systemLogService.writeLog(module, ACTION.PRO_ORDERS_CREATE.name(), "Thêm mới item vào đơn hàng: " + orderDetail.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Thêm mới item vào đơn hàng " + orderDetail.toString());
            return orderDetailSaved;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException();
        }
    }

    @Override
    public String updateOrder(Order order, Integer id) {
        Order orderToUpdate = orderRepo.findById(id).orElse(null);
        if (orderToUpdate == null) {
            throw new BadRequestException();
        }
        for (Map.Entry<String, String> entry : orderToUpdate.compareTo(order).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            orderHistoryRepo.save(new OrderHistory(id, null, "Update order", key, value.substring(0, value.indexOf("#")), value.substring(value.indexOf("#") + 1)));
        }
        order.setId(id);
        orderRepo.save(order);
        systemLogService.writeLog(module, ACTION.PRO_ORDERS_UPDATE.name(), "Cập nhật đơn hàng: " + order.toString());
        logger.info(OrderServiceImpl.class.getName() + ": Cập nhật đơn hàng " + order.toString());
        return MessageUtils.UPDATE_SUCCESS;
    }

    @Override
    public String updateOrderDetail(OrderDetail orderDetail, Integer orderDetailId) {
        try {
            orderDetail.setId(orderDetailId);
            orderDetailRepo.save(orderDetail);
            systemLogService.writeLog(module, ACTION.PRO_ORDERS_UPDATE.name(), "Cập nhật item of đơn hàng: " + orderDetail.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Cập nhật item of đơn hàng " + orderDetail.toString());
            return MessageUtils.UPDATE_SUCCESS;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new AppException(ex);
        }
    }

    @Override
    public String deleteOrder(Integer id) {
        OrderDTO order = this.findOrderById(id);
        if (order.getPaymentStatus()) {
            throw new DataInUseException(MessageUtils.ERROR_DATA_LOCKED);
        }
        orderRepo.deleteById(id);
        systemLogService.writeLog(module, ACTION.PRO_ORDERS_DELETE.name(), "Xóa đơn hàng: " + order.toString());
        logger.info(OrderServiceImpl.class.getName() + ": Xóa đơn hàng " + order.toString());
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public String deleteOrderDetail(Integer orderDetailId) {
        OrderDetail orderDetail = this.findOrderDetailById(orderDetailId);
        try {
            orderDetailRepo.deleteById(orderDetailId);
            systemLogService.writeLog(module, ACTION.PRO_ORDERS_DELETE.name(), "Xóa item of đơn hàng: " + orderDetail.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Xóa item of đơn hàng " + orderDetail.toString());
            return MessageUtils.DELETE_SUCCESS;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new AppException(ex);
        }
    }

    @Transactional
    @Override
    public String doPay(Integer orderId, Date paymentTime, Integer paymentMethod, Float paymentAmount, String paymentNote) {
        orderRepo.updatePaymentStatus(orderId, paymentTime, paymentMethod, paymentAmount, paymentNote);
        return "Thanh toán thành công!";
    }

    @Override
    public List<Order> findByStaffId(Integer customerId) {
        return orderRepo.findByNhanvienId(customerId);
    }

    @Override
    public List<Order> findOrdersBySalesChannelId(Integer salesChannelId) {
        return orderRepo.findBySalesChannel(salesChannelId);
    }

    @Override
    public List<Order> findOrdersByStatus(Integer orderStatusId) {
        return orderRepo.findByOrderStatus(orderStatusId);
    }

    @Override
    public List<OrderDTO> findOrdersByCustomerId(Integer customerId) {
        return OrderDTO.fromOrders(orderRepo.findAll(null, customerId, Pageable.unpaged()).getContent());
    }

    @Override
    public List<Order> findOrdersByPaymentMethodId(Integer paymentMethodId) {
        return orderRepo.findByPaymentMethodId(paymentMethodId);
    }

    @Override
    public List<Order> findOrdersToday() {
        return orderRepo.findOrdersToday();
    }

    @Override
    public OrderDetail findOrderDetailById(Integer orderDetailId) {
        return orderDetailRepo.findById(orderDetailId).orElse(null);
    }

    @Override
    public Double findRevenueToday() {
        return orderRepo.findRevenueToday();
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
                row.createCell(1).setCellValue(listData.get(i).getCode());
                row.createCell(2).setCellValue(listData.get(i).getOrderTime());
                row.createCell(3).setCellValue(listData.get(i).getSalesChannelName());
                row.createCell(4).setCellValue(String.valueOf(listData.get(i).getTotalAmountDiscount()));
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

    @Override
    public void exportToPDF(OrderDTO dto, HttpServletResponse response) {
        boolean checkBatch = false;

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        PDFMergerUtility mergePdf = new PDFMergerUtility();
        //Barcode_Image.createImage(order.getId().toString() + ".png", order.getId().toString());
        HashMap<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("customerName", dto.getReceiverName());
        parameterMap.put("customerAddress", dto.getReceiverAddress());
        parameterMap.put("customerPhone", dto.getReceiverPhone());
        parameterMap.put("customerEmail", dto.getReceiverEmail());
        parameterMap.put("totalSubtotal","$ 0");
        parameterMap.put("totalShippingCost","$ 0");
        parameterMap.put("discount","$ " + dto.getAmountDiscount());
        parameterMap.put("totalPayment", dto.getTotalAmountDiscount());
        parameterMap.put("paymentMethod", dto.getPayMethodName());
        parameterMap.put("invoiceNumber", dto.getCode());
        parameterMap.put("orderDate", dto.getOrderTime());
        parameterMap.put("nowDate", new Date());
        FileStorage f = fileStorageService.findQRCodeOfOrder(dto.getId());
        parameterMap.put("barcode", Path.of(CommonUtils.rootPath + "/" + f.getDirectoryPath() + "/" + f.getStorageName()));

        // orderDetails
        List<OrderDetailRpt> listDetail = new ArrayList<>();
        for (OrderDetailDTO detailDTO : dto.getListOrderDetailDTO()) {
            OrderDetailRpt rpt = new OrderDetailRpt();
            rpt.setProductName(detailDTO.getProductVariantDTO().getVariantName());
            rpt.setUnitPrice(detailDTO.getUnitPrice());
            rpt.setQuantity(detailDTO.getQuantity());
            rpt.setSubTotal(detailDTO.getUnitPrice().multiply(BigDecimal.valueOf(detailDTO.getQuantity())));
            rpt.setNote(detailDTO.getNote());
            listDetail.add(rpt);
        }

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ServletOutputStream servletOutputStream = response.getOutputStream();
            try {
//				JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.font.name", "false");
//				JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.encoding", "UTF-8");
//				JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.embedded", "true");
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", " inline; filename=deliveryNote" + dto.getId() + ".pdf");
                InputStream reportStream = new FileInputStream(ReportUtils.getReportTemplate("Invoice"));
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameterMap, new JRBeanCollectionDataSource(listDetail));
                JasperExportManager.exportReportToPdfStream(jasperPrint, (checkBatch) ? byteArrayOutputStream : servletOutputStream);
                if (checkBatch) {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                } else {
                    servletOutputStream.flush();
                    servletOutputStream.close();
                }
            } catch (Exception e) {
                // display stack trace in the browser
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e.printStackTrace(printWriter);
                response.setContentType("text/plain");
                response.getOutputStream().print(stringWriter.toString());
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception print PDF: " + e.getMessage());
        }
    }
}