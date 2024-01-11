package com.flowiee.app.service;

import com.flowiee.app.dto.ProductDTO;
import com.flowiee.app.dto.ProductVariantDTO;
import com.flowiee.app.entity.Product;
import com.flowiee.app.entity.ProductVariant;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<Product> findAllProducts();

    Page<Product> findAllProducts(Integer productTypeId, Integer brandId, String status);

    Page<Product> findAllProducts(int size, int page, Integer productTypeId, Integer brandId, String status);

    List<Product> findProductsIdAndProductName();

    List<Product> findProductsByType(Integer productTypeId);

    List<Product> findProductsByUnit(Integer unitId);

    List<Product> findProductsByBrand(Integer brandId);

    List<ProductDTO> setInfoVariantOfProduct(List<ProductDTO> productDTOs);

    ProductVariant findProductVariantById(Integer productVariantId);

    List<ProductVariant> findAllProductVariants();

    List<ProductVariant> findProductVariantBySize(Integer sizeId);

    List<ProductVariant> findProductVariantByColor(Integer colorId);

    List<ProductVariant> findProductVariantByImport(Integer importId);

    List<ProductVariant> findProductVariantByFabricType(Integer fabricTypeId);

    List<ProductVariantDTO>  findAllProductVariantOfProduct(Integer productId);

    Product findProductById(Integer productId);

    String saveProduct(Product product);

    String updateProduct(Product product, Integer productId);

    String deleteProduct(Integer productId);

    String saveProductVariant(ProductVariant productVariant);

    String updateProductVariant(ProductVariant productVariant, Integer productVariantId);

    String deleteProductVariant(Integer productVariantId);

    String updateProductVariantQuantity(Integer soLuong, Integer id);

    Integer findProductVariantTotalQtySell(Integer productId);

    Integer findProductVariantQuantityBySizeOfEachColor(Integer productId, Integer colorId, Integer sizeId);

    Double findProductVariantPriceSell(int id);

    byte[] exportData(List<Integer> listSanPhamId);

    boolean productInUse(Integer productId);
}