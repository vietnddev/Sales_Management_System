package com.flowiee.app.repository;

import com.flowiee.app.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select " +
           "o.id as order_id_0, " +
           "o.maDonHang as ma_don_hang_1, " +
           "o.thoiGianDatHang as order_time_2, " +
           "o.receiverAddress as receiver_address_3, " +
           "o.receiverPhone as receiver_phone_4, " +
           "o.receiverName as receiver_name_5, " +
           "nvl(c.id, 0) as order_by_id_6, " +
           "c.tenKhachHang as order_by_name_7, " +
           "nvl(o.totalAmountAfterDiscount, 0) as total_amount_8, " +
           "nvl(sc.id, 0) as sales_channel_id_9, " +
           "sc.name as sales_channel_name_10, " +
           "o.ghiChu as note_11, " +
           "nvl(os.id, 0) as order_status_id_12, " +
           "os.name as order_status_name_13, " +
           "nvl(op.id, 0) as order_pay_id_14, " +
           "op.paymentStatus as order_pay_status_15, " +
           "nvl(pm.id, 0) as payment_method_id_16, " +
           "pm.name as payment_method_name_17, " +
           "nvl(acc.id, 0) as cashier_id_18, " +
           "acc.hoTen as cashier_name_19, " +
           "nvl(o.createdBy, 0) as created_by_id_20, " +
           "o.createdAt as created_at_21, " +
           "case when f.order.id is not null then concat(concat(f.directoryPath, '/'), f.tenFileKhiLuu) else '' end as qrcode_file_name_22, " +
           "o.receiverEmail as receiver_email_23 " +
           "from Order o " +
           "left join Customer c on c.id = o.customer.id " +
           "left join OrderPay op on op.order.id = o.id " +
           "left join Account acc on acc.id = op.thuNgan.id " +
           "left join Category sc on sc.id = o.kenhBanHang.id and sc.type = 'SALES_CHANNEL' " +
           "left join Category os on os.id = o.trangThaiDonHang.id and os.type = 'ORDER_STATUS' " +
           "left join Category pm on pm.id = op.hinhThucThanhToan.id and pm.type = 'PAYMENT_METHOD' " +
           "left join FileStorage f on f.order.id = o.id")
    List<Object[]> findAll(@Param("orderId") Integer orderId);

    @Query("from Order d where d.trangThaiDonHang.id=:trangThaiDonHangId")
    List<Order> findByTrangThaiDonHang(@Param("trangThaiDonHangId") Integer trangThaiDonHangId);

    @Query("from Order d order by d.id desc")
    List<Order> findDonHangMoiNhat();

    @Query("from Order d where d.customer.id=:customerId")
    List<Order> findByKhachHangId(@Param("customerId") Integer customerId);

    @Query("from Order d where d.nhanVienBanHang.id=:nhanVienId")
    List<Order> findByNhanvienId(@Param("nhanVienId") Integer nhanVienId);

    @Query("from Order o where o.kenhBanHang.id=:salesChannelId")
    List<Order> findBySalesChannel(@Param("salesChannelId") Integer salesChannelId);

    @Query("from Order o where o.trangThaiDonHang.id=:orderStatusId")
    List<Order> findByOrderStatus(@Param("orderStatusId") Integer orderStatusId);

    @Query("from Order o where o.customer.id=:customerId")
    List<Order> findByCustomer(@Param("customerId") Integer customerId);

    @Query("select nvl(sum(o.totalAmountAfterDiscount), 0) from Order o where trunc(o.thoiGianDatHang) = trunc(current_date)")
    Double findRevenueToday();

    @Query("select nvl(sum(o.totalAmountAfterDiscount), 0) from Order o where extract(month from o.thoiGianDatHang) = extract(month from current_date)")
    Double findRevenueThisMonth();

    @Query("from Order o where trunc(o.thoiGianDatHang) = truc(current_date)")
    List<Order> findOrdersToday();

    @Query("select " +
           "sum(case when extract(month from o.thoiGianDatHang) = 1 then o.totalAmountAfterDiscount else 0 end) as JAN, " +
           "sum(case when extract(month from o.thoiGianDatHang) = 2 then o.totalAmountAfterDiscount else 0 end) as FEB, " +
           "sum(case when extract(month from o.thoiGianDatHang) = 3 then o.totalAmountAfterDiscount else 0 end) as MAR, " +
           "sum(case when extract(month from o.thoiGianDatHang) = 4 then o.totalAmountAfterDiscount else 0 end) as APRIL, " +
           "sum(case when extract(month from o.thoiGianDatHang) = 5 then o.totalAmountAfterDiscount else 0 end) as MAY, " +
           "sum(case when extract(month from o.thoiGianDatHang) = 6 then o.totalAmountAfterDiscount else 0 end) as JUN, " +
           "sum(case when extract(month from o.thoiGianDatHang) = 7 then o.totalAmountAfterDiscount else 0 end) as JUL, " +
           "sum(case when extract(month from o.thoiGianDatHang) = 8 then o.totalAmountAfterDiscount else 0 end) as AUG, " +
           "sum(case when extract(month from o.thoiGianDatHang) = 9 then o.totalAmountAfterDiscount else 0 end) as SEP, " +
           "sum(case when extract(month from o.thoiGianDatHang) = 10 then o.totalAmountAfterDiscount else 0 end) as OCT, " +
           "sum(case when extract(month from o.thoiGianDatHang) = 11 then o.totalAmountAfterDiscount else 0 end) as NOV, " +
           "sum(case when extract(month from o.thoiGianDatHang) = 12 then o.totalAmountAfterDiscount else 0 end) as DEC " +
           "from Order o " +
           "where extract(year from o.thoiGianDatHang) = extract(year from current_date)")
    List<Object[]> findRevenueEachMonthOfYear();
}