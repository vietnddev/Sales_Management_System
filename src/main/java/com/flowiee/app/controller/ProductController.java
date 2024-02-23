package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.FileDTO;
import com.flowiee.app.dto.ProductDTO;
import com.flowiee.app.dto.ProductVariantDTO;
import com.flowiee.app.entity.*;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.*;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/product")
@Tag(name = "Product API", description = "Quản lý sản phẩm")
public class ProductController extends BaseController {
    private final PriceService priceService;
    private final ProductService productService;
    private final ProductHistoryService productHistoryService;
    private final FileStorageService fileStorageService;

    @Autowired
    public ProductController(PriceService priceService, ProductService productService, ProductHistoryService productHistoryService, FileStorageService fileStorageService) {
        this.priceService = priceService;
        this.productService = productService;
        this.productHistoryService = productHistoryService;
        this.fileStorageService = fileStorageService;
    }

    @Operation(summary = "Find all products")
    @GetMapping("/all")
    public ApiResponse<List<ProductDTO>> findProducts(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                      @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                      @RequestParam(value = "txtSearch", required = false) String txtSearch,
                                                      @RequestParam(value = "fullInfo", required = false) Boolean fullInfo) {
        try {
            if (!super.vldModuleProduct.readProduct(true)) {
                return null;
            }
            if (fullInfo != null && !fullInfo) {
                return ApiResponse.ok(ProductDTO.fromProducts(productService.findProductsIdAndProductName()));
            }
            Page<Product> productPage = productService.findAllProducts(pageSize, pageNum - 1, txtSearch, null, null, null);
            List<ProductDTO> productList = productService.setInfoVariantOfProduct(ProductDTO.fromProducts(productPage.getContent()));
            return ApiResponse.ok(productList, pageNum, pageSize, productPage.getTotalPages(), productPage.getTotalElements());
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            logger.error(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product"), ex);
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Find detail products")
    @GetMapping("/{id}")
    public ApiResponse<ProductDTO> findDetailProduct(@PathVariable("id") Integer productId) {
        if (!super.vldModuleProduct.readProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(ProductDTO.fromProduct(productService.findProductById(productId)));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Find all variants")
    @GetMapping("/variant/all")
    public ApiResponse<List<ProductVariant>> findProductVariants() {
        if (!super.vldModuleProduct.readProduct(true)) {
            return null;
        }
        try {
            List<ProductVariant> result = productService.findAllProductVariants();
            return ApiResponse.ok(result);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product variant"));
        }
    }

    @Operation(summary = "Find all variants of product")
    @GetMapping("/{productId}/variants")
    public ApiResponse<List<ProductVariantDTO>> findVariantsOfProduct(@PathVariable("productId") Integer productId) {
        if (!super.vldModuleProduct.readProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.findAllProductVariantOfProduct(productId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product variant"));
        }
    }

    @Operation(summary = "Find detail product variant")
    @GetMapping("/variant/{id}")
    public ApiResponse<ProductVariant> findDetailProductVariant(@PathVariable("id") Integer productVariantId) {
        if (!super.vldModuleProduct.readProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.findProductVariantById(productVariantId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Check product variant already exists")
    @GetMapping("/variant/exists")
    public ApiResponse<Boolean> checkProductVariantAlreadyExists(@RequestParam("productId") Integer productId,
                                                                 @RequestParam("colorId") Integer colorId,
                                                                 @RequestParam("sizeId") Integer sizeId) {
        if (!super.vldModuleProduct.readProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.isProductVariantExists(productId, colorId, sizeId));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            logger.error(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product"), ex);
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Create product")
    @PostMapping("/create")
    public ApiResponse<Product> createProduct(@RequestBody ProductDTO product) {
        if (!super.vldModuleProduct.insertProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.saveProduct(Product.fromProductDTO(product)));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "product"), ex);
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Create product variant")
    @PostMapping("/variant/create")
    public ApiResponse<ProductVariant> createProductVariant(@RequestBody ProductVariantDTO productVariantDTO) {
        if (!super.vldModuleProduct.insertProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.saveProductVariant(productVariantDTO));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "productVariant"), ex);
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "productVariant"));
        }
    }

    @Operation(summary = "Create product attribute")
    @PostMapping("/attribute/create")
    public ApiResponse<ProductAttribute> createProductAttribute(@RequestBody ProductAttribute productAttribute) {
        if (!super.vldModuleProduct.insertProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.saveProductAttribute(productAttribute));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "product attribute"));
        }
    }

    @Operation(summary = "Update product")
    @PutMapping("/update/{id}")
    public ApiResponse<ProductDTO> updateProduct(@RequestBody Product product, @PathVariable("id") Integer productId) {
        if (!super.vldModuleProduct.updateProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(ProductDTO.fromProduct(productService.updateProduct(product, productId)));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "product"), ex);
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Update product variant")
    @PutMapping("/variant/update/{id}")
    public ApiResponse<ProductVariant> updateProductVariant(@RequestBody ProductVariant productVariant, @PathVariable("id") Integer productVariantId) {
        if (!super.vldModuleProduct.updateProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.updateProductVariant(productVariant, productVariantId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Update product attribute")
    @PutMapping("/attribute/update/{id}")
    public ApiResponse<ProductAttribute> updateProductAttribute(@RequestBody ProductAttribute productAttribute, @PathVariable("id") Integer productAttributeId) {
        if (!super.vldModuleProduct.updateProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.updateProductAttribute(productAttribute, productAttributeId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "product attribute"));
        }
    }

    @Operation(summary = "Delete product")
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable("id") Integer productId) {
        if (!super.vldModuleProduct.deleteProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.deleteProduct(productId));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "product"), ex);
            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Delete product variant")
    @DeleteMapping("/variant/delete/{id}")
    public ApiResponse<String> deleteProductVariant(@PathVariable("id") Integer productVariantId) {
        if (!super.vldModuleProduct.deleteProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.deleteProductVariant(productVariantId));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "productVariant"), ex);
            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Delete product attribute")
    @DeleteMapping("/attribute/delete/{id}")
    public ApiResponse<String> deleteProductAttribute(@PathVariable("id") Integer productAttributeId) {
        if (!super.vldModuleProduct.deleteProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.deleteProductAttribute(productAttributeId));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "product attribute"), ex);
            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "product attribute"));
        }
    }

    @Operation(summary = "Upload images of product")
    @PostMapping(value = "/{productId}/uploads-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<FileStorage> uploadImageOfProduct(@RequestParam("file") MultipartFile file, @PathVariable("productId") Integer productId) {
        try {
            if (!super.vldModuleProduct.updateImage(true)) {
                return null;
            }
            if (productId <= 0 || productService.findProductById(productId) == null) {
                throw new BadRequestException();
            }
            if (file.isEmpty()) {
                throw new FileNotFoundException();
            }
            return ApiResponse.ok(fileStorageService.saveImageSanPham(file, productId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "image"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Upload images of product variant")
    @PostMapping(value = "/{productId}/variant/uploads-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<FileStorage> uploadImageOfProductVariant(@RequestParam("file") MultipartFile file, @PathVariable("productId") Integer productVariantId) {
        try {
            if (!super.vldModuleProduct.updateImage(true)) {
                return null;
            }
            if (productVariantId <= 0 || productService.findProductVariantById(productVariantId) == null) {
                throw new BadRequestException();
            }
            if (file.isEmpty()) {
                throw new FileNotFoundException();
            }
            return ApiResponse.ok(fileStorageService.saveImageBienTheSanPham(file, productVariantId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "image"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Update product image is use default")
    @PutMapping(value = "/{productId}/active-image/{imageId}")
    public ApiResponse<FileStorage> activeImageOfProduct(@PathVariable("productId") Integer productId, @PathVariable("imageId") Integer imageId) {
        try {
            if (!super.vldModuleProduct.updateImage(true)) {
                return null;
            }
            if (productId == null || productId <= 0 || imageId == null || imageId <= 0) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(fileStorageService.setImageActiveOfSanPham(productId, imageId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "update image of product"));
        }
    }

    @Operation(summary = "Change image of product")
    @PutMapping(value = "/{productId}/change-image/{imageId}")
    public ApiResponse<FileStorage> changeImageOfProduct(@RequestParam("file") MultipartFile file, @PathVariable("imageId") Integer imageId) {
        try {
            if (!super.vldModuleProduct.updateImage(true)) {
                return null;
            }
            if (imageId <= 0 || fileStorageService.findById(imageId) == null) {
                throw new BadRequestException("Image not found");
            }
            if (file.isEmpty()) {
                throw new BadRequestException("File attach not found!");
            }
            return ApiResponse.ok(fileStorageService.changeImageSanPham(file, imageId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "update image of product"));
        }
    }

    @Operation(summary = "Update variant image is use default")
    @PutMapping(value = "/variant/{productVariantId}/active-image/{imageId}")
    public ApiResponse<FileStorage> activeImageOfProductVariant(@PathVariable("productVariantId") Integer productVariantId, @PathVariable("imageId") Integer imageId) {
        try {
            if (!super.vldModuleProduct.updateImage(true)) {
                return null;
            }
            if (productVariantId == null || productVariantId <= 0 || imageId == null || imageId <= 0) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(fileStorageService.setImageActiveOfBienTheSanPham(productVariantId, imageId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "update image of product"));
        }
    }

    @PostMapping(value = "/change-image/{imageId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<FileStorage> changeFile(@RequestParam("file") MultipartFile fileUpload, @PathVariable("imageId") Integer imageId) {
        try {
            if (!super.vldModuleProduct.updateImage(true)) {
                return null;
            }
            if (imageId <= 0 || fileStorageService.findById(imageId) == null) {
                throw new BadRequestException();
            }
            if (fileUpload.isEmpty()) {
                throw new FileNotFoundException();
            }
            return ApiResponse.ok(fileStorageService.changeImageSanPham(fileUpload, imageId));
        } catch (Exception ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "contact"));
        }
    }

    @Operation(summary = "Create price")
    @PostMapping(value = "/variant/{productVariantId}/price/create")
    public ApiResponse<Price> createPrice(@RequestBody Price price, @PathVariable("productVariantId") Integer productVariantId) {
        try {
            if (!super.vldModuleProduct.priceManagement(true)) {
                return null;
            }
            if (price == null || productVariantId <= 0 || productService.findProductVariantById(productVariantId) == null) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(priceService.save(price));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "price"));
        }
    }

    @Operation(summary = "Update price")
    @PutMapping(value = "/variant/{productVariantId}/price/update/{priceId}")
    public ApiResponse<Price> updatePrice(@RequestBody Price price,
                                          @PathVariable("productVariantId") Integer productVariantId,
                                          @PathVariable("priceId") Integer priceId) {
        try {
            if (!super.vldModuleProduct.priceManagement(true)) {
                return null;
            }
            if (price == null || productVariantId <= 0 || productService.findProductVariantById(productVariantId) == null) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(priceService.update(price, productVariantId, priceId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "price"));
        }
    }

    @Operation(summary = "Get histories of product")
    @GetMapping(value = "/{productId}/history")
    public ApiResponse<List<ProductHistory>> getHistoryOfProduct(@PathVariable("productId") Integer productId) {
        try {
            if (!super.vldModuleProduct.readProduct(true)) {
                return null;
            }
            if (ObjectUtils.isEmpty(productService.findProductById(productId))) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(productHistoryService.findByProduct(productId));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product history"), ex);
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product history"));
        }
    }

    @Operation(summary = "Find images of product")
    @GetMapping("{productId}/images")
    public ApiResponse<List<FileDTO>> getImagesOfProduct(@PathVariable("productId") Integer productId) {
        try {
            if (!super.vldModuleProduct.readProduct(true)) {
                return null;
            }
            List<FileStorage> images = fileStorageService.getImageOfSanPham(productId);
            return ApiResponse.ok(FileDTO.fromFileStorages(images), 1, 0, 1, images.size());
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "gallery"), ex);
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "gallery"));
        }
    }

    @Operation(summary = "Find images of all products")
    @GetMapping("/images/all")
    public ApiResponse<List<FileStorage>> viewGallery() {
        try {
            if (!super.vldModuleProduct.readGallery(true)) {
                return null;
            }
            return ApiResponse.ok(fileStorageService.getAllImageSanPham(AppConstants.SYSTEM_MODULE.PRODUCT.name()));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "gallery"));
        }
    }
}