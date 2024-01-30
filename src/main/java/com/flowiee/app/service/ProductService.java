package com.flowiee.app.service;

import com.flowiee.app.dto.ProductDTO;
import com.flowiee.app.dto.ProductVariantDTO;
import com.flowiee.app.entity.Product;
import com.flowiee.app.entity.ProductAttribute;
import com.flowiee.app.entity.ProductVariant;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Page<Product> findAllProducts();

    Page<Product> findAllProducts(Integer productTypeId, Integer brandId, String status);

    Page<Product> findAllProducts(int size, int page);

    List<Product> findProductsIdAndProductName();

    List<Product> findProductsByType(Integer productTypeId);

    List<Product> findProductsByUnit(Integer unitId);

    List<Product> findProductsByBrand(Integer brandId);

    List<ProductDTO> setInfoVariantOfProduct(List<ProductDTO> productDTOs);

    List<ProductVariant> findAllProductVariants();

    List<ProductVariant> findProductVariantBySize(Integer sizeId);

    List<ProductVariant> findProductVariantByColor(Integer colorId);

    List<ProductVariant> findProductVariantByImport(Integer importId);

    List<ProductVariant> findProductVariantByFabricType(Integer fabricTypeId);

    List<ProductVariantDTO>  findAllProductVariantOfProduct(Integer productId);

    List<ProductAttribute> findAllAttributes(Integer productVariantId);

    Product findProductById(Integer productId);

    ProductVariant findProductVariantById(Integer productVariantId);

    ProductAttribute findProductAttributeById(Integer productAttributeId);

    Product saveProduct(Product product);

    Product updateProduct(Product product, Integer productId);

    String deleteProduct(Integer productId);

    ProductVariant saveProductVariant(ProductVariant productVariant);

    ProductVariant updateProductVariant(ProductVariant productVariant, Integer productVariantId);

    String deleteProductVariant(Integer productVariantId);

    ProductAttribute saveProductAttribute(ProductAttribute productAttribute);

    ProductAttribute updateProductAttribute(ProductAttribute productAttribute, Integer productAttributeId);

    String deleteProductAttribute(Integer productAttributeId);

    String updateProductVariantQuantity(Integer soLuong, Integer id);

    Integer findProductVariantTotalQtySell(Integer productId);

    Integer findProductVariantQuantityBySizeOfEachColor(Integer productId, Integer colorId, Integer sizeId);

    BigDecimal findProductVariantPriceSell(int id);

    boolean productInUse(Integer productId);

    boolean isProductVariantExists(int productId, int colorId, int sizeId);

    byte[] exportData(List<Integer> listSanPhamId);
}