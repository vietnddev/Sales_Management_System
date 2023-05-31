package com.flowiee.app.hethong.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.common.model.DateAudit;
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.khotailieu.entity.DocShare;
import com.flowiee.app.khotailieu.entity.Document;
import com.flowiee.app.sanpham.entity.DonHang;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "nguoi_dung")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@AllArgsConstructor
public class Account extends DateAudit implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "username", nullable = false)
    @NotNull(message = "Username không được trống")
    private String username;

    @Column(name = "password", nullable = false)
    @NotNull(message = "Password không được trống")
    private String password;

    @Column(name = "ho_ten", length = 255, nullable = false)
    @NotNull(message = "Họ tên không được trống")
    private String hoTen;

    @Column(name = "gioi_tinh", nullable = false)
    private boolean gioiTinh;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

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

    @Override
    public String toString() {
        return "TaiKhoan{" +
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
}
