package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.model.request.AccountRequest;
import com.flowiee.app.model.role.Role;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Entity
@Table(name = "sys_account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account extends BaseEntity implements Serializable{	
	private static final long serialVersionUID = 1L;

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

    @Column(name = "role")
    private String role;

    @Column(name = "trang_thai")
    private boolean trangThai;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<FileStorage> listFileStorage;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<DocShare> listDocShare;

    @OneToMany(mappedBy = "nhanVienBanHang", fetch = FetchType.LAZY)
    private List<Order> listOrder;

    @OneToMany(mappedBy = "thuNgan", fetch = FetchType.LAZY)
    private List<OrderPay> listOrderPay;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Customer> listCustomer;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<FlowieeImport> listHistoryImportData;

    @OneToMany(mappedBy = "receivedBy", fetch = FetchType.LAZY)
    private List<TicketImportGoods> listTicketImportGoods;

    @Transient
    private List<Role> listRole;

    @Transient
    private String ip;   

    public Account(Integer id) {
        super.id = id;
    }

    public Account(Integer id, String username, String hoTen) {
        super.id = id;
        this.username = username;
        this.hoTen = hoTen;
    }
    
    public Account fromAccountRequest(AccountRequest request) {
    	Account acc = new Account();
    	acc.setId(request.getId());
    	acc.setUsername(request.getUsername());
    	acc.setPassword(request.getPassword());
    	acc.setHoTen(request.getName());
    	acc.setGioiTinh(request.getSex());
    	acc.setSoDienThoai(request.getPhone());
    	acc.setEmail(request.getEmail());
    	acc.setDiaChi(request.getAddress());
    	acc.setAvatar(request.getAvatar());
    	acc.setGhiChu(request.getNote());
    	acc.setRole(request.getRole());
    	acc.setTrangThai(request.getStatus());
    	return acc; 
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", hoTen=");
		builder.append(hoTen);
		builder.append(", gioiTinh=");
		builder.append(gioiTinh);
		builder.append(", soDienThoai=");
		builder.append(soDienThoai);
		builder.append(", email=");
		builder.append(email);
		builder.append(", diaChi=");
		builder.append(diaChi);
		builder.append(", avatar=");
		builder.append(avatar);
		builder.append(", ghiChu=");
		builder.append(ghiChu);
		builder.append(", role=");
		builder.append(role);
		builder.append(", trangThai=");
		builder.append(trangThai);
		builder.append(", ip=");
		builder.append(ip);
		builder.append("]");
		return builder.toString();
	}
}