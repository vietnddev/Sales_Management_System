package com.flowiee.app.hethong.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.hethong.model.Role;
import com.flowiee.app.sanpham.entity.GoodsImport;
import com.flowiee.app.storage.entity.DocShare;
import com.flowiee.app.storage.entity.Document;
import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.entity.DonHangThanhToan;
import com.flowiee.app.sanpham.entity.KhachHang;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
@Entity
@Table(name = "account")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity implements Serializable{
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @Column(name = "gioi_tinh", nullable = false)
    private boolean gioiTinh;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai")
    private boolean trangThai;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<SystemLog> listLog;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Document> listDocument;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<FileStorage> listFileStorage;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<DocShare> listDocShare;

    @OneToMany(mappedBy = "nhanVienBanHang", fetch = FetchType.LAZY)
    private List<DonHang> listDonHang;

    @OneToMany(mappedBy = "thuNgan", fetch = FetchType.LAZY)
    private List<DonHangThanhToan> listDonHangThanhToan;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<KhachHang> listKhachHang;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<FlowieeImport> listHistoryImportData;

    @OneToMany(mappedBy = "receivedBy", fetch = FetchType.LAZY)
    private List<GoodsImport> listGoodsImport;

    @Transient
    private List<Role> role;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", gioiTinh=" + gioiTinh +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }

    public Account(Integer id) {
        super.id = id;
    }

    public Account(Integer id, String username, String hoTen) {
        super.id = id;
        this.username = username;
        this.hoTen = hoTen;
    }
}