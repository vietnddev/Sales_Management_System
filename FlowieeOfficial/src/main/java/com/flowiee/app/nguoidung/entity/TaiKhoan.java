package com.flowiee.app.nguoidung.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.common.entity.AuditEntity;
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.khotailieu.entity.DocShare;
import com.flowiee.app.khotailieu.entity.Document;
import com.flowiee.app.log.entity.SystemLog;
import lombok.*;

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
public class TaiKhoan extends AuditEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "username", length = 255, nullable = false)
    @NotNull(message = "Username không được trống")
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    @NotNull(message = "Password không được trống")
    private String password;

    @Column(name = "ho_ten", length = 255, nullable = false)
    @NotNull(message = "Họ tên không được trống")
    private String hoTen;

    @Column(name = "gioi_tinh", nullable = false)
    private boolean gioiTinh;

    @Column(name = "so_dien_thoai", length = 20, nullable = true)
    private String soDienThoai;

    @Column(name = "email", length = 50, nullable = true)
    private String email;

    @Column(name = "avatar", length = 255, nullable = true)
    private String avatar;

    @Column(name = "ghi_chu", length = 255, nullable = true)
    private String ghiChu;

    @Column(name = "trang_thai")
    private boolean trangThai;

    @JsonIgnoreProperties("taiKhoan")
    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    private List<SystemLog> listLog;

    @JsonIgnoreProperties("taiKhoan")
    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    private List<Document> listDocument;

    @JsonIgnoreProperties("taiKhoan")
    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    private List<FileStorage> listFileStorage;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    private List<DocShare> listDocShare;

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
