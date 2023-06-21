package com.flowiee.app.dashboard.service;

import com.flowiee.app.dashboard.model.DoanhThuCacThangTheoNam;
import com.flowiee.app.dashboard.model.DoanhThuTheoKenhBanHang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private EntityManager entityManager;

    @Override
    public DoanhThuTheoKenhBanHang getDoanhThuTheoKenhBanHang() {
        StringBuilder query = new StringBuilder("SELECT c.TEN_LOAI, c.MAU_NHAN, SUM(d.TONG_TIEN_DON_HANG) AS TOTAL ");
        query.append("FROM DM_KENH_BAN_HANG c ");
        query.append("LEFT JOIN DON_HANG d ON c.ID = d.KENH_BAN_HANG ");
        query.append("GROUP BY c.ten_loai, c.mau_nhan");

        Query result = entityManager.createNativeQuery(query.toString());
        List<Object[]> listData = result.getResultList();

        DoanhThuTheoKenhBanHang dataReturn = new DoanhThuTheoKenhBanHang();
        List<String> listTenOfKenh = new ArrayList<>();
        List<Float> listDoanhThuOfKenh = new ArrayList<>();
        List<String> listMauSacOfKenh = new ArrayList<>();
        for (Object[] data : listData) {
            listTenOfKenh.add(String.valueOf(data[0]));
            listDoanhThuOfKenh.add(data[2] == null ? 0 : Float.parseFloat(String.valueOf(data[2])));
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
        List<Object[]> listData = result.getResultList();

        List<Float> listDoanhThu = new ArrayList<>();
        for (int i = 0; i < listData.get(0).length; i++) {
            listDoanhThu.add(Float.parseFloat(String.valueOf(listData.get(0)[i])));
        }

        DoanhThuCacThangTheoNam dataReturn = new DoanhThuCacThangTheoNam();
        dataReturn.setDoanhThu(listDoanhThu);

        return dataReturn;
    }

    @Override
    public Integer getSoLuongDonHangHomNay() {
        String query = "SELECT NVL(COUNT(*), 0) FROM DON_HANG D WHERE TRUNC(D.THOI_GIAN_DAT_HANG) = TRUNC(SYSDATE)";
        Query result = entityManager.createNativeQuery(query);
        return Integer.parseInt(String.valueOf(result.getSingleResult()));
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
    public Integer getSoLuongKhachHangMoi() {
        String query = "SELECT NVL(COUNT(*), 0) FROM KHACH_HANG c WHERE EXTRACT(MONTH FROM c.CREATED_AT) = EXTRACT(MONTH FROM SYSDATE)";
        Query result = entityManager.createNativeQuery(query);
        return Integer.parseInt(String.valueOf(result.getSingleResult()));
    }
}