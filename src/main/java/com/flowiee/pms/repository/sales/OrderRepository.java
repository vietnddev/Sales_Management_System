package com.flowiee.pms.repository.sales;

import com.flowiee.pms.entity.sales.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
//    @Query("select " +
//           "o.id as order_id_0, " +
//           "o.maDonHang as ma_don_hang_1, " +
//           "o.thoiGianDatHang as order_time_2, " +
//           "o.receiverAddress as receiver_address_3, " +
//           "o.receiverPhone as receiver_phone_4, " +
//           "o.receiverName as receiver_name_5, " +
//           "nvl(c.id, 0) as order_by_id_6, " +
//           "c.customerName as order_by_name_7, " +
//           "o.amountDiscount as amount_discount_8, " +
//           "nvl(sc.id, 0) as sales_channel_id_9, " +
//           "sc.name as sales_channel_name_10, " +
//           "o.ghiChu as note_11, " +
//           "nvl(os.id, 0) as order_status_id_12, " +
//           "os.name as order_status_name_13, " +
//           //"nvl(op.id, 0) as order_pay_id_14, " +
//           //"op.paymentStatus as order_pay_status_15, " +
//           "nvl(pm.id, 0) as payment_method_id_14, " +
//           "pm.name as payment_method_name_15, " +
//           //"'' as cashier_id_18, " +
//           //"'' as cashier_name_19, " +
//           "nvl(o.createdBy, 0) as created_by_id_16, " +
//           "o.createdAt as created_at_17, " +
//           "case when f.order.id is not null then concat(concat(f.directoryPath, '/'), f.storageName) else '' end as qrcode_file_name_18, " +
//           "o.receiverEmail as receiver_email_19, " +
//           "o.voucherUsedCode as voucher_used_code_20, " +
//           "o.paymentStatus as payment_status_21, " +
//           "o.paymentTime as payment_time_22, " +
//           "o.paymentAmount as payment_amount_23, " +
//           "o.paymentNote as payment_note_24 " +
//           "from Order o " +
//           "left join Customer c on c.id = o.customer.id " +
//           //"left join OrderPay op on op.order.id = o.id " +
//           //"left join Account acc on acc.id = op.thuNgan.id " +
//           "left join Category sc on sc.id = o.kenhBanHang.id and sc.type = 'SALES_CHANNEL' " +
//           "left join Category os on os.id = o.trangThaiDonHang.id and os.type = 'ORDER_STATUS' " +
//           "left join Category pm on pm.id = o.paymentMethod.id and pm.type = 'PAYMENT_METHOD' " +
//           "left join FileStorage f on f.order.id = o.id " +
//           "where 1=1 " +
//           "and (:orderId is null or o.id=:orderId) " +
//           "and (:customerId is null or o.customer.id=:customerId)")
//    Page<Object[]> findAll(@Param("orderId") Integer orderId, @Param("customerId") Integer customerId, Pageable pageable);

    @Query("select distinct o from Order o " +
           "left join Customer c on c.id = o.customer.id " +
           "left join Category sc on sc.id = o.kenhBanHang.id and sc.type = 'SALES_CHANNEL' " +
           "left join Category os on os.id = o.trangThaiDonHang.id and os.type = 'ORDER_STATUS' " +
           "left join Category pm on pm.id = o.paymentMethod.id and pm.type = 'PAYMENT_METHOD' " +
           "left join FileStorage f on f.order.id = o.id " +
           "where 1=1 " +
           "and (:orderId is null or o.id=:orderId) " +
           "and (:paymentMethodId is null or o.paymentMethod.id=:paymentMethodId) " +
           "and (:orderStatusId is null or o.trangThaiDonHang.id=:orderStatusId) " +
           "and (:salesChannelId is null or o.kenhBanHang.id=:salesChannelId) " +
           "and (:sellerId is null or o.nhanVienBanHang.id=:sellerId) " +
           "and (:customerId is null or o.customer.id=:customerId)")
    Page<Order> findAll(@Param("orderId") Integer orderId,
                        @Param("paymentMethodId") Integer paymentMethodId,
                        @Param("orderStatusId") Integer orderStatusId,
                        @Param("salesChannelId") Integer salesChannelId,
                        @Param("sellerId") Integer sellerId,
                        @Param("customerId") Integer customerId,
                        Pageable pageable);

    @Query(value = "select sum((select sum(d.price * d.quantity) from order_detail d where d.order_id = o.id) - o.amount_discount) from orders o where trunc(o.order_time) = trunc(current_date)", nativeQuery = true)
    Double findRevenueToday();

//    @Query("select nvl(sum(o.totalAmountDiscount), 0) from Order o where extract(month from o.thoiGianDatHang) = extract(month from current_date)")
//    Double findRevenueThisMonth();

    @Query("from Order o where trunc(o.orderTime) = trunc(current_date)")
    List<Order> findOrdersToday();

    @Modifying
    @Query("update Order set paymentTime=:paymentTime, paymentMethod.id=:paymentMethod, paymentAmount=:paymentAmount, paymentNote=:paymentNote, paymentStatus = true where id=:orderId")
    void updatePaymentStatus(@Param("orderId") Integer orderId, @Param("paymentTime") LocalDateTime paymentTime, @Param("paymentMethod") Integer paymentMethod, @Param("paymentAmount") Float paymentAmount, @Param("paymentNote") String paymentNote);

//    @Query("select " +
//           "sum(case when extract(month from o.thoiGianDatHang) = 1 then o.totalAmountDiscount else 0 end) as JAN, " +
//           "sum(case when extract(month from o.thoiGianDatHang) = 2 then o.totalAmountDiscount else 0 end) as FEB, " +
//           "sum(case when extract(month from o.thoiGianDatHang) = 3 then o.totalAmountDiscount else 0 end) as MAR, " +
//           "sum(case when extract(month from o.thoiGianDatHang) = 4 then o.totalAmountDiscount else 0 end) as APRIL, " +
//           "sum(case when extract(month from o.thoiGianDatHang) = 5 then o.totalAmountDiscount else 0 end) as MAY, " +
//           "sum(case when extract(month from o.thoiGianDatHang) = 6 then o.totalAmountDiscount else 0 end) as JUN, " +
//           "sum(case when extract(month from o.thoiGianDatHang) = 7 then o.totalAmountDiscount else 0 end) as JUL, " +
//           "sum(case when extract(month from o.thoiGianDatHang) = 8 then o.totalAmountDiscount else 0 end) as AUG, " +
//           "sum(case when extract(month from o.thoiGianDatHang) = 9 then o.totalAmountDiscount else 0 end) as SEP, " +
//           "sum(case when extract(month from o.thoiGianDatHang) = 10 then o.totalAmountDiscount else 0 end) as OCT, " +
//           "sum(case when extract(month from o.thoiGianDatHang) = 11 then o.totalAmountDiscount else 0 end) as NOV, " +
//           "sum(case when extract(month from o.thoiGianDatHang) = 12 then o.totalAmountDiscount else 0 end) as DEC " +
//           "from Order o " +
//           "where extract(year from o.thoiGianDatHang) = extract(year from current_date)")
//    List<Object[]> findRevenueEachMonthOfYear();

    @Modifying
    @Query("update Order set ticketExport.id=:ticketExportId where id=:orderId")
    void setTicketExportInfo(@Param("orderId") Integer orderId, @Param("ticketExportId") Integer ticketExportId);

    @Query("from Order where ticketExport.id=:ticketExportId")
    Order findByTicketExport(@Param("ticketExportId") Integer ticketExportId);
}