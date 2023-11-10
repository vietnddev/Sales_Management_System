package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.category.entity.FabricType;
import com.flowiee.app.category.entity.LoaiKichCo;
import com.flowiee.app.category.entity.LoaiMauSac;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Entity
@Table(name = "product_variant_temp")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductVariantTemp extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_id", nullable = false)
    private Product product;
    
    @Column(name = "ma_san_pham", length = 50, nullable = false)
    private String maSanPham;
    
    @Column(name = "ten_bien_the")
    private String tenBienThe;

    @Column(name = "so_luong_kho", nullable = false)
    private int soLuongKho;

    @Column(name = "da_ban", nullable = false)
    private int soLuongDaBan;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mau_sac_id", nullable = false)
    private LoaiMauSac loaiMauSac;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kich_co_id", nullable = false)
    private LoaiKichCo loaiKichCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_lieu_vai_id", nullable = false)
    private FabricType fabricType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garment_factory_id")
    private GarmentFactory garmentFactory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_import_id")
    private GoodsImport goodsImport;

    @Transient
    private Price price;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<ProductAttribute> listThuocTinh;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<Price> listGiaBan;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<OrderDetail> listOrderDetail;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<FileStorage> listFileStorage;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<Items> listItems;

    public static ProductVariantTemp convertFromProductVariant(ProductVariant productVariant) {
        ProductVariantTemp productVariantTemp = new ProductVariantTemp();
        productVariantTemp.setId(productVariant.getId());
        productVariantTemp.setProduct(productVariant.getProduct());
        productVariantTemp.setMaSanPham(productVariant.getMaSanPham());
        productVariantTemp.setTenBienThe(productVariant.getTenBienThe());
        productVariantTemp.setSoLuongKho(productVariant.getSoLuongKho());
        productVariantTemp.setSoLuongDaBan(productVariant.getSoLuongDaBan());
        productVariantTemp.setTrangThai(productVariant.getTrangThai());
        productVariantTemp.setLoaiMauSac(productVariant.getLoaiMauSac());
        productVariantTemp.setLoaiKichCo(productVariant.getLoaiKichCo());
        productVariantTemp.setFabricType(productVariant.getFabricType());
        productVariantTemp.setGarmentFactory(productVariant.getGarmentFactory());
        productVariantTemp.setSupplier(productVariant.getSupplier());
        productVariantTemp.setGoodsImport(productVariant.getGoodsImport());
        productVariantTemp.setPrice(productVariant.getPrice());
        productVariantTemp.setListThuocTinh(productVariant.getListThuocTinh());
        productVariantTemp.setListGiaBan(productVariant.getListGiaBan());
        productVariantTemp.setListOrderDetail(productVariant.getListOrderDetail());
        productVariantTemp.setListFileStorage(productVariant.getListFileStorage());
        productVariantTemp.setListItems(productVariant.getListItems());
        return productVariantTemp;
    }

    @Override
    public String toString() {
        return "BienTheSanPhamTemp{" +
                "sanPham=" + product +
                ", maSanPham='" + maSanPham + '\'' +
                ", tenBienThe='" + tenBienThe + '\'' +
                ", soLuongKho=" + soLuongKho +
                ", trangThai='" + trangThai + '\'' +
                ", loaiMauSac=" + loaiMauSac +
                ", loaiKichCo=" + loaiKichCo +
                ", giaSanPham=" + price +
                '}';
    }
}