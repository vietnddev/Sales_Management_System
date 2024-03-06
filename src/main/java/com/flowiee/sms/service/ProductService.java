package com.flowiee.sms.service;

import com.flowiee.sms.entity.ProductDetail;
import com.flowiee.sms.model.dto.ProductDTO;
import com.flowiee.sms.model.dto.ProductVariantDTO;
import com.flowiee.sms.entity.Product;
import com.flowiee.sms.entity.ProductAttribute;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Page<ProductDTO> findAllProducts(int pageSize, int pageNum, String txtSearch, Integer productType, Integer color, Integer size);

    List<Product> findProductsIdAndProductName();

    List<Product> findProductsByType(Integer productTypeId);

    List<ProductDetail> findAllProductVariants();

    List<ProductVariantDTO>  findAllProductVariantOfProduct(Integer productId);

    List<ProductAttribute> findAllAttributes(Integer productVariantId);

    Product findProductById(Integer productId);

    ProductDetail findProductVariantById(Integer productVariantId);

    ProductAttribute findProductAttributeById(Integer productAttributeId);

    Product saveProduct(Product product);

    Product updateProduct(Product product, Integer productId);

    String deleteProduct(Integer productId);

    ProductDetail saveProductVariant(ProductVariantDTO productVariantDTO);

    ProductDetail updateProductVariant(ProductDetail productDetail, Integer productVariantId);

    String deleteProductVariant(Integer productVariantId);

    ProductAttribute saveProductAttribute(ProductAttribute productAttribute);

    ProductAttribute updateProductAttribute(ProductAttribute productAttribute, Integer productAttributeId);

    String deleteProductAttribute(Integer productAttributeId);

    String updateProductVariantQuantity(Integer soLuong, Integer id, String type);

    String updatePrice(Integer variantId, BigDecimal originalPrice, BigDecimal discountPrice);

    Integer findProductVariantTotalQtySell(Integer productId);

    Integer findProductVariantQuantityBySizeOfEachColor(Integer productId, Integer colorId, Integer sizeId);

    Integer totalProductsInStorage();

    boolean productInUse(Integer productId);

    boolean isProductVariantExists(int productId, int colorId, int sizeId);

    byte[] exportData(List<Integer> listSanPhamId);
}