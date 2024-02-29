package com.flowiee.app.service.impl;

import com.flowiee.app.dto.OrderDetailDTO;
import com.flowiee.app.entity.*;
import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.exception.DataInUseException;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.repository.OrderDetailRepository;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.entity.Category;
import com.flowiee.app.repository.OrderRepository;
import com.flowiee.app.service.*;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.DateUtils;

import com.flowiee.app.utils.MessageUtils;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Clock;
import java.time.Instant;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private static final String module = AppConstants.SYSTEM_MODULE.PRODUCT.name();

    private final OrderRepository orderRepo;
    private final OrderDetailRepository orderDetailRepo;
    private final ProductService productService;
    private final SystemLogService systemLogService;
    private final CartService cartService;
    private final PriceService priceService;
    private final VoucherService voucherService;
    private final FileStorageService fileStorageService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepo, OrderDetailRepository orderDetailRepo, ProductService productService, SystemLogService systemLogService, CartService cartService, PriceService priceService, VoucherService voucherService, FileStorageService fileStorageService) {
        this.orderRepo = orderRepo;
        this.orderDetailRepo = orderDetailRepo;
        this.productService = productService;
        this.systemLogService = systemLogService;
        this.cartService = cartService;
        this.priceService = priceService;
        this.voucherService = voucherService;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public List<OrderDTO> findAllOrder() {
        return OrderDTO.fromOrders(orderRepo.findAll((Integer) null, null, Pageable.unpaged()).getContent());
    }

    @Override
    public Page<OrderDTO> findAllOrder(int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("thoiGianDatHang").descending());
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
        double totalAmount = 0;
        for (OrderDetail d : listOrderDetail) {
            totalProduct += d.getSoLuong();
            totalAmount += d.getPrice() * d.getSoLuong();
        }
        OrderDTO dto = orders.get(0);
        dto.setListOrderDetailDTO(OrderDetailDTO.fromOrderDetails(listOrderDetail));
        dto.setTotalProduct(totalProduct);
        dto.setTotalAmount(totalAmount);
        dto.setTotalAmountDiscount(dto.getTotalAmount() - dto.getAmountDiscount());
        return orders.get(0);
    }

    @Transactional
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
            order.setReceiverName(request.getReceiveName());
            order.setReceiverPhone(request.getReceivePhone());
            order.setReceiverEmail(request.getReceiveEmail());
            order.setReceiverAddress(request.getReceiveAddress());

            if (request.getOrderTimeStr() != null) {
                Date orderTime = DateUtils.convertStringToDate("dd/MM/yyyy hh:mm a", "dd/MM/yyyy HH:mm:ss", request.getOrderTimeStr());
                order.setThoiGianDatHang(orderTime);
            } else {
                order.setThoiGianDatHang(new Date());
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
            systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_ORDERS_CREATE.name(), "Thêm mới đơn hàng: " + order.toString());
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
            orderDetailRepo.save(orderDetail);
            systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_ORDERS_CREATE.name(), "Thêm mới item vào đơn hàng: " + orderDetail.toString());
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
        orderRepo.save(order);
        systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_ORDERS_UPDATE.name(), "Cập nhật đơn hàng: " + order.toString());
        logger.info(OrderServiceImpl.class.getName() + ": Cập nhật đơn hàng " + order.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String updateOrderDetail(OrderDetail orderDetail, Integer orderDetailId) {
        try {
            orderDetail.setId(orderDetailId);
            orderDetailRepo.save(orderDetail);
            systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_ORDERS_UPDATE.name(), "Cập nhật item of đơn hàng: " + orderDetail.toString());
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
            throw new DataInUseException(MessageUtils.ERROR_DATA_LOCKED);
        }
        orderRepo.deleteById(id);
        systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_ORDERS_DELETE.name(), "Xóa đơn hàng: " + order.toString());
        logger.info(OrderServiceImpl.class.getName() + ": Xóa đơn hàng " + order.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String deleteOrderDetail(Integer orderDetailId) {
        OrderDetail orderDetail = this.findOrderDetailById(orderDetailId);
        try {
            orderDetailRepo.deleteById(orderDetailId);
            systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_ORDERS_DELETE.name(), "Xóa item of đơn hàng: " + orderDetail.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Xóa item of đơn hàng " + orderDetail.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.SERVICE_RESPONSE_FAIL;
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

    public List<OrderDTO> convertObjectsToDTO(List<Object[]> objects) {
        List<OrderDTO> dataResponse = new ArrayList<>();
//        for (Object[] data : objects) {
//            OrderDTO order = new OrderDTO();
//            order.setOrderId(Integer.parseInt(String.valueOf(data[0])));
//            order.setOrderCode(String.valueOf(data[1]));
//            order.setOrderTime(DateUtils.convertStringToDate(String.valueOf(data[2]), "yyyy-MM-dd HH:mm:ss.SSSSSS"));
//            order.setOrderTimeStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getOrderTime()));
//            order.setReceiveAddress(String.valueOf(data[3]) != null ? String.valueOf(data[3]) : "-");
//            order.setReceivePhone(String.valueOf(data[4]) != null ? String.valueOf(data[4]) : "-");
//            order.setReceiveName(String.valueOf(data[5]));
//            order.setReceiveEmail(String.valueOf(data[19]));
//            order.setOrderBy(new Customer(Integer.parseInt(String.valueOf(data[6])), String.valueOf(data[7])));
//            order.setAmountDiscount(data[8] != null ? Double.parseDouble(String.valueOf(data[8])) : 0);
//            order.setSalesChannel(new Category(Integer.parseInt(String.valueOf(data[9])), String.valueOf(data[10])));
//            order.setSalesChannelId(Integer.parseInt(String.valueOf(data[9])));
//            order.setSalesChannelName(String.valueOf(data[10]));
//            order.setNote(String.valueOf(data[11]) != null ? String.valueOf(data[11]) : "-");
//            order.setOrderStatus(new Category(Integer.parseInt(String.valueOf(data[12])), String.valueOf(data[13])));
//            order.setPayMethodName(String.valueOf(data[15]));
//            if (data[22] != null) {
//                order.setPaymentTime(DateUtils.convertStringToDate(String.valueOf(data[22]), "yyyy-MM-dd HH:mm:ss.SSSSSS"));
//            }
//            order.setPaymentTimeStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getPaymentTime()));
//            order.setCreatedBy(new Account(Integer.parseInt(String.valueOf(data[16]))));
//            order.setCreatedAt(DateUtils.convertStringToDate(String.valueOf(data[17]), "yyyy-MM-dd HH:mm:ss.SSSSSS"));
//            order.setCreatedAtStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getCreatedAt()));
//            order.setQrCode(String.valueOf(data[18]));
//            order.setVoucherUsedCode(data[20] != null ? String.valueOf(data[20]) : "-");
//            order.setPaymentStatus(data[21] != null && Boolean.parseBoolean(String.valueOf(data[21])));
//            order.setPaymentAmount(String.valueOf(data[23]));
//            order.setPaymentNote(String.valueOf(data[24]));
//            dataResponse.add(order);
//        }
        return dataResponse;
    }

    @Override
    public void exportToPDF(OrderDTO dto, HttpServletResponse response) {
        boolean checkBatch = false;

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        PDFMergerUtility mergePdf = new PDFMergerUtility();
        //Barcode_Image.createImage(order.getId().toString() + ".png", order.getId().toString());
        HashMap<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("customerName", dto.getCustomerName());
        parameterMap.put("customerAddress", dto.getReceiveAddress());
        parameterMap.put("customerPhone", dto.getReceivePhone());
        parameterMap.put("customerEmail", dto.getReceiveEmail());
//        parameterMap.put("totalSubtotal","$ " +  order.getSubtotal());
//        parameterMap.put("totalShippingCost","$ " +  order.getShippingCost());
//        parameterMap.put("totalTax","$ " +  order.getTax());
        parameterMap.put("totalPayment", dto.getTotalAmountDiscount());
//        parameterMap.put("paymentMethod", order.getPaymentMethod());
        parameterMap.put("invoiceNumber", dto.getOrderCode());
//        parameterMap.put("orderDate", order.getOrderTime());
//        parameterMap.put("nowDate", this.getDate());
//        parameterMap.put("barcode",
//                "C:\\Users\\PhuocLuu\\Desktop\\ShoppingCart\\ShoppingCart\\shoppingcart-webparent\\shoppingcart-backend\\barcode\\"
//                        + order.getId() + ".png");

        // orderDetails
//        int index = 1;
        Map<String, String> listDetail = new HashMap<>();
        listDetail.put("productName", "Con chim");

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ServletOutputStream servletOutputStream = response.getOutputStream();
            try {
//				JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.font.name", "false");
//				JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.encoding", "UTF-8");
//				JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.embedded", "true");
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", " inline; filename=deliveryNote" + dto.getOrderId() + ".pdf");
                InputStream reportStream = new FileInputStream(CommonUtils.getReportTemplate("Invoice"));
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameterMap, new JRBeanCollectionDataSource(List.of(listDetail)));
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