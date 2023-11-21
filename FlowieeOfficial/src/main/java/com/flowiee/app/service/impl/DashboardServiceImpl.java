package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.Order;
import com.flowiee.app.model.product.DoanhThuCacNgayTheoThang;
import com.flowiee.app.model.product.DoanhThuCacThangTheoNam;
import com.flowiee.app.model.product.DoanhThuTheoKenhBanHang;
import com.flowiee.app.model.product.TopSanPhamBanChay;
import com.flowiee.app.service.DashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private EntityManager entityManager;

    @Override
    public DoanhThuTheoKenhBanHang getDoanhThuTheoKenhBanHang() {
        StringBuilder query = new StringBuilder("SELECT c.TEN_LOAI, c.MAU_NHAN, NVL(SUM(d.TONG_TIEN_DON_HANG),0) AS TOTAL ");
        query.append("FROM DM_KENH_BAN_HANG c ");
        query.append("LEFT JOIN DON_HANG d ON c.ID = d.KENH_BAN_HANG ");
        query.append("GROUP BY c.ten_loai, c.mau_nhan");

        Query result = entityManager.createNativeQuery(query.toString());
        @SuppressWarnings("unchecked")
		List<Object[]> listData = result.getResultList();

        DoanhThuTheoKenhBanHang dataReturn = new DoanhThuTheoKenhBanHang();
        List<String> listTenOfKenh = new ArrayList<>();
        List<Float> listDoanhThuOfKenh = new ArrayList<>();
        List<String> listMauSacOfKenh = new ArrayList<>();
        for (Object[] data : listData) {
            listTenOfKenh.add(String.valueOf(data[0]));
            listDoanhThuOfKenh.add(Float.parseFloat(String.valueOf(data[2])));
            listMauSacOfKenh.add(String.valueOf(data[1]));
        }
        dataReturn.setTenOfKenh(listTenOfKenh);
        dataReturn.setDoanhThuOfKenh(listDoanhThuOfKenh);
        dataReturn.setMauSac(listMauSacOfKenh);

        return dataReturn;
    }

    @Override
    public DoanhThuCacThangTheoNam getDoanhThuCacThangTheoNam() {
        StringBuilder query = new StringBuilder("SELECT ");
        query.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 1 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_1, ");
        query.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 2 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_2, ");
        query.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 3 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_3, ");
        query.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 4 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_4,");
        query.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 5 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_5, ");
        query.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 6 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_6, ");
        query.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 7 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_7, ");
        query.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 8 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_8,");
        query.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 9 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_9, ");
        query.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 10 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_10, ");
        query.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 11 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_11, ");
        query.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 12 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_12 ");
        query.append("FROM DON_HANG d ");
        query.append("WHERE EXTRACT(YEAR FROM d.THOI_GIAN_DAT_HANG) = EXTRACT(YEAR FROM SYSDATE)");

        Query result = entityManager.createNativeQuery(query.toString());
        @SuppressWarnings("unchecked")
		List<Object[]> listData = result.getResultList();

        List<Float> listDoanhThu = new ArrayList<>();
        for (int i = 0; i < listData.get(0).length; i++) {
            listDoanhThu.add(Float.parseFloat(String.valueOf(listData.get(0)[i] != null ? listData.get(0)[i] : 0)));
        }

        DoanhThuCacThangTheoNam dataReturn = new DoanhThuCacThangTheoNam();
        dataReturn.setDoanhThu(listDoanhThu);

        return dataReturn;
    }

    @Override
    public DoanhThuCacNgayTheoThang getDoanhThuCacNgayTheoThang() {
        // Lấy ngày bắt đầu của tháng hiện tại
        LocalDate ngayBatDau = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        // Lấy ngày kết thúc của tháng hiện tại
        LocalDate ngayKetThuc = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        StringBuilder query = new StringBuilder("WITH all_dates AS (");
        query.append("SELECT TO_DATE('" + ngayBatDau + "', 'YYYY-MM-DD') + LEVEL - 1 AS NGAY ");
        query.append("FROM DUAL ");
        query.append("CONNECT BY LEVEL <= TO_DATE('" + ngayKetThuc + "', 'YYYY-MM-DD') - TO_DATE('" + ngayBatDau + "', 'YYYY-MM-DD') + 1");
        query.append(") ");
        query.append("SELECT all_dates.NGAY, NVL(SUM(DON_HANG.TONG_TIEN_DON_HANG), 0) AS DOANH_THU ");
        query.append("FROM all_dates ");
        query.append("LEFT JOIN DON_HANG ON TRUNC(DON_HANG.THOI_GIAN_DAT_HANG) = all_dates.NGAY ");
        query.append("GROUP BY all_dates.NGAY ");
        query.append("ORDER BY all_dates.NGAY");

        Query result = entityManager.createNativeQuery(query.toString());
        @SuppressWarnings("unchecked")
		List<Object[]> listData = result.getResultList();

        DoanhThuCacNgayTheoThang dataReturn = new DoanhThuCacNgayTheoThang();
        List<String> listNgay = new ArrayList<>();
        List<Float> listDoanhThu = new ArrayList<>();

        int i = 1;
        for (Object[] data : listData) {
            listNgay.add("Ngày " + i);
            i++;

            listDoanhThu.add(Float.parseFloat(String.valueOf(data[1])));
        }

        dataReturn.setListNgay(listNgay);
        dataReturn.setListDoanhThu(listDoanhThu);

        return dataReturn;
    }

    @Override
    public TopSanPhamBanChay getTopSanPhamBanChay() {
        StringBuilder query = new StringBuilder("SELECT * ");
        query.append("FROM (");
        query.append("SELECT s.TEN_BIEN_THE, NVL(SUM(d.SO_LUONG), 0) AS Total ");
        query.append("FROM SAN_PHAM_BIEN_THE s ");
        query.append("LEFT JOIN DON_HANG_CHI_TIET d ");
        query.append("ON s.id = d.BIEN_THE_SAN_PHAM_ID ");
        query.append("GROUP BY s.ID, s.TEN_BIEN_THE ");
        query.append("ORDER BY total DESC");
        query.append(") ");
        query.append("WHERE ROWNUM <= 10");

        Query result = entityManager.createNativeQuery(query.toString());
        @SuppressWarnings("unchecked")
		List<Object[]> listData = result.getResultList();

        TopSanPhamBanChay dataReturn = new TopSanPhamBanChay();
        List<String> listTenSanPham = new ArrayList<>();
        List<Integer> listSoLuongDaBan = new ArrayList<>();
        for (Object[] data : listData) {
            listTenSanPham.add(String.valueOf(data[0]));
            listSoLuongDaBan.add(Integer.parseInt(String.valueOf(data[1])));
        }
        dataReturn.setTenSanPham(listTenSanPham);
        dataReturn.setSoLuongDaBan(listSoLuongDaBan);

        return dataReturn;
    }

    @Override
    public List<Order> getSoLuongDonHangHomNay() {
//        String query = "SELECT NVL(COUNT(*), 0) FROM DON_HANG D WHERE TRUNC(D.THOI_GIAN_DAT_HANG) = TRUNC(SYSDATE)";
//        Query result = entityManager.createNativeQuery(query);
//        return Integer.parseInt(String.valueOf(result.getSingleResult()));
        String query = "SELECT * FROM DON_HANG D WHERE TRUNC(D.THOI_GIAN_DAT_HANG) = TRUNC(SYSDATE)";
        Query result = entityManager.createNativeQuery(query);
        @SuppressWarnings("unchecked")
		List<Order> listReturn = result.getResultList();
        return listReturn;
    }

    @Override
    public Float getDoanhThuHomNay() {
        String query = "SELECT NVL(SUM(d.tong_tien_don_hang), 0) FROM DON_HANG d WHERE TRUNC(d.THOI_GIAN_DAT_HANG) = TRUNC(SYSDATE)";
        Query result = entityManager.createNativeQuery(query);
        return Float.parseFloat(String.valueOf(result.getSingleResult()));
    }

    @Override
    public Float getDoanhThuThangNay() {
        String query = "SELECT NVL(SUM(d.TONG_TIEN_DON_HANG), 0) FROM DON_HANG d WHERE EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = EXTRACT(MONTH FROM SYSDATE)";
        Query result = entityManager.createNativeQuery(query);
        return Float.parseFloat(String.valueOf(result.getSingleResult()));
    }

    @Override
    public List<Customer> getSoLuongKhachHangMoi() {
//        String query = "SELECT NVL(COUNT(*), 0) FROM KHACH_HANG c WHERE EXTRACT(MONTH FROM c.CREATED_AT) = EXTRACT(MONTH FROM SYSDATE)";
//        Query result = entityManager.createNativeQuery(query);
//        return Integer.parseInt(String.valueOf(result.getSingleResult()));
        String query = "SELECT * FROM KHACH_HANG c WHERE EXTRACT(MONTH FROM c.CREATED_AT) = EXTRACT(MONTH FROM SYSDATE)";
        Query result = entityManager.createNativeQuery(query);
        @SuppressWarnings("unchecked")
		List<Customer> listReturn = result.getResultList();
        return listReturn;
    }
}