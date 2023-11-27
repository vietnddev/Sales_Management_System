package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.Order;
import com.flowiee.app.model.DoanhThuCacNgayTheoThangModel;
import com.flowiee.app.model.DoanhThuCacThangTheoNamModel;
import com.flowiee.app.model.DoanhThuTheoKenhBanHangModel;
import com.flowiee.app.model.TopBestSellerModel;
import com.flowiee.app.service.DashboardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ProductVariantServiceImpl.class);

    @Autowired
    private EntityManager entityManager;

    @Override
    public DoanhThuTheoKenhBanHangModel getDoanhThuTheoKenhBanHang() {
        StringBuilder strSQL = new StringBuilder("SELECT c.NAME, c.COLOR, NVL(SUM(d.TONG_TIEN_DON_HANG),0) AS TOTAL ");
        strSQL.append("FROM (SELECT * FROM CATEGORY WHERE TYPE = 'SALESCHANNEL') c ");
        strSQL.append("LEFT JOIN PRO_DON_HANG d ON c.ID = d.KENH_BAN_HANG ");
        strSQL.append("GROUP BY c.NAME, c.COLOR");
        logger.info("[getDoanhThuTheoKenhBanHang() - SQL findData]: " + strSQL.toString());
        Query result = entityManager.createNativeQuery(strSQL.toString());
        @SuppressWarnings("unchecked")
		List<Object[]> listData = result.getResultList();

        DoanhThuTheoKenhBanHangModel dataReturn = new DoanhThuTheoKenhBanHangModel();
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
    public DoanhThuCacThangTheoNamModel getDoanhThuCacThangTheoNam() {
        StringBuilder strSQL = new StringBuilder("SELECT ");
        strSQL.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 1 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_1, ");
        strSQL.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 2 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_2, ");
        strSQL.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 3 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_3, ");
        strSQL.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 4 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_4,");
        strSQL.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 5 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_5, ");
        strSQL.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 6 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_6, ");
        strSQL.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 7 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_7, ");
        strSQL.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 8 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_8,");
        strSQL.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 9 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_9, ");
        strSQL.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 10 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_10, ");
        strSQL.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 11 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_11, ");
        strSQL.append("SUM(CASE WHEN EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = 12 THEN d.TONG_TIEN_DON_HANG ELSE 0 END) AS THANG_12 ");
        strSQL.append("FROM PRO_DON_HANG d ");
        strSQL.append("WHERE EXTRACT(YEAR FROM d.THOI_GIAN_DAT_HANG) = EXTRACT(YEAR FROM SYSDATE)");
        logger.info("[getDoanhThuCacThangTheoNam() - SQL findData]: " + strSQL.toString());
        Query result = entityManager.createNativeQuery(strSQL.toString());
        @SuppressWarnings("unchecked")
		List<Object[]> listData = result.getResultList();

        List<Float> listDoanhThu = new ArrayList<>();
        for (int i = 0; i < listData.get(0).length; i++) {
            listDoanhThu.add(Float.parseFloat(String.valueOf(listData.get(0)[i] != null ? listData.get(0)[i] : 0)));
        }

        DoanhThuCacThangTheoNamModel dataReturn = new DoanhThuCacThangTheoNamModel();
        dataReturn.setDoanhThu(listDoanhThu);

        return dataReturn;
    }

    @Override
    public DoanhThuCacNgayTheoThangModel getDoanhThuCacNgayTheoThang() {
        // Lấy ngày bắt đầu của tháng hiện tại
        LocalDate ngayBatDau = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        // Lấy ngày kết thúc của tháng hiện tại
        LocalDate ngayKetThuc = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        StringBuilder strSQL = new StringBuilder("WITH all_dates AS (");
        strSQL.append("SELECT TO_DATE('" + ngayBatDau + "', 'YYYY-MM-DD') + LEVEL - 1 AS NGAY ");
        strSQL.append("FROM DUAL ");
        strSQL.append("CONNECT BY LEVEL <= TO_DATE('" + ngayKetThuc + "', 'YYYY-MM-DD') - TO_DATE('" + ngayBatDau + "', 'YYYY-MM-DD') + 1");
        strSQL.append(") ");
        strSQL.append("SELECT all_dates.NGAY, NVL(SUM(PRO_DON_HANG.TONG_TIEN_DON_HANG), 0) AS DOANH_THU ");
        strSQL.append("FROM all_dates ");
        strSQL.append("LEFT JOIN PRO_DON_HANG ON TRUNC(PRO_DON_HANG.THOI_GIAN_DAT_HANG) = all_dates.NGAY ");
        strSQL.append("GROUP BY all_dates.NGAY ");
        strSQL.append("ORDER BY all_dates.NGAY");
        logger.info("[getDoanhThuCacNgayTheoThang() - SQL findData]: " + strSQL.toString());
        Query result = entityManager.createNativeQuery(strSQL.toString());
        @SuppressWarnings("unchecked")
		List<Object[]> listData = result.getResultList();

        DoanhThuCacNgayTheoThangModel dataReturn = new DoanhThuCacNgayTheoThangModel();
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
    public TopBestSellerModel getTopSanPhamBanChay() {
        StringBuilder strSQL = new StringBuilder("SELECT * ");
        strSQL.append("FROM (");
        strSQL.append("SELECT s.VARIANT_NAME, NVL(SUM(d.SO_LUONG), 0) AS Total ");
        strSQL.append("FROM PRO_PRODUCT_VARIANT s ");
        strSQL.append("LEFT JOIN PRO_DON_HANG_CHI_TIET d ");
        strSQL.append("ON s.id = d.BIEN_THE_SAN_PHAM_ID ");
        strSQL.append("GROUP BY s.ID, s.VARIANT_NAME ");
        strSQL.append("ORDER BY total DESC");
        strSQL.append(") ");
        strSQL.append("WHERE ROWNUM <= 10");
        logger.info("[getTopSanPhamBanChay() - SQL findData]: " + strSQL.toString());
        Query result = entityManager.createNativeQuery(strSQL.toString());
        @SuppressWarnings("unchecked")
		List<Object[]> listData = result.getResultList();

        TopBestSellerModel dataReturn = new TopBestSellerModel();
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
        String strSQL = "SELECT * FROM PRO_DON_HANG D WHERE TRUNC(D.THOI_GIAN_DAT_HANG) = TRUNC(SYSDATE)";
        logger.info("[getSoLuongDonHangHomNay() - SQL findData]: " + strSQL.toString());
        Query result = entityManager.createNativeQuery(strSQL);
        @SuppressWarnings("unchecked")
		List<Order> listReturn = result.getResultList();
        return listReturn;
    }

    @Override
    public Float getDoanhThuHomNay() {
        String strSQL = "SELECT NVL(SUM(d.tong_tien_don_hang), 0) FROM PRO_DON_HANG d WHERE TRUNC(d.THOI_GIAN_DAT_HANG) = TRUNC(SYSDATE)";
        logger.info("[getDoanhThuHomNay() - SQL findData]: " + strSQL.toString());
        Query result = entityManager.createNativeQuery(strSQL);
        return Float.parseFloat(String.valueOf(result.getSingleResult()));
    }

    @Override
    public Float getDoanhThuThangNay() {
        String strSQL = "SELECT NVL(SUM(d.TONG_TIEN_DON_HANG), 0) FROM PRO_DON_HANG d WHERE EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = EXTRACT(MONTH FROM SYSDATE)";
        logger.info("[getDoanhThuThangNay() - SQL findData]: " + strSQL.toString());
        Query result = entityManager.createNativeQuery(strSQL);
        return Float.parseFloat(String.valueOf(result.getSingleResult()));
    }

    @Override
    public List<Customer> getSoLuongKhachHangMoi() {
//        String query = "SELECT NVL(COUNT(*), 0) FROM KHACH_HANG c WHERE EXTRACT(MONTH FROM c.CREATED_AT) = EXTRACT(MONTH FROM SYSDATE)";
//        Query result = entityManager.createNativeQuery(query);
//        return Integer.parseInt(String.valueOf(result.getSingleResult()));
        String strSQL = "SELECT * FROM PRO_CUSTOMER c WHERE EXTRACT(MONTH FROM c.CREATED_AT) = EXTRACT(MONTH FROM SYSDATE)";
        logger.info("[getSoLuongKhachHangMoi() - SQL findData]: " + strSQL.toString());
        Query result = entityManager.createNativeQuery(strSQL);
        @SuppressWarnings("unchecked")
		List<Customer> listReturn = result.getResultList();
        return listReturn;
    }
}