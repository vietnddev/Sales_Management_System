package com.flowiee.app.nguoidung.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.common.entity.AuditEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "nguoi_dung")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccountEntity extends AuditEntity implements Serializable{
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

    public AccountEntity(){}

    public AccountEntity(String username, String password, String name, boolean gender, String phone, String email,
                         String avatar, String notes, boolean status) {
        this.username = username;
        this.password = password;
        this.hoTen = name;
        this.gioiTinh = gender;
        this.soDienThoai = phone;
        this.email = email;
        this.avatar = avatar;
        this.ghiChu = notes;
        this.trangThai = status;
    }
}
