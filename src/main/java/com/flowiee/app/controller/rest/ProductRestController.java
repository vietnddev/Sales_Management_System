package com.flowiee.app.controller.rest;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.ProductDTO;
import com.flowiee.app.dto.ProductVariantDTO;
import com.flowiee.app.dto.VoucherInfoDTO;
import com.flowiee.app.entity.*;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.service.FileStorageService;
import com.flowiee.app.service.PriceService;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.service.VoucherService;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/product")
@Tag(name = "Product API", description = "Quản lý sản phẩm")
public class ProductRestController extends BaseController {
    @Autowired private PriceService priceService;
    @Autowired private ProductService productService;
    @Autowired private VoucherService voucherService;
    @Autowired private FileStorageService fileStorageService;

    @Operation(summary = "Find all products")
    @GetMapping("/all")
    public ApiResponse<List<ProductDTO>> findProducts() {
        if (!super.validateModuleProduct.readProduct(true)) {
            return null;
        }
        try {
            Page<Product> productPage = productService.findAllProducts();
            List<ProductDTO> productList = productService.setInfoVariantOfProduct(ProductDTO.fromProducts(productPage.getContent()));
            return ApiResponse.ok(productList);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Find detail products")
    @GetMapping("/{id}")
    public ApiResponse<ProductDTO> findDetailProduct(@PathVariable("id") Integer productId) {
        if (!super.validateModuleProduct.readProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(ProductDTO.fromProduct(productService.findProductById(productId)));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Find all variants")
    @GetMapping("/variant/all")
    public ApiResponse<List<ProductVariant>> findProductVariants() {
        if (!super.validateModuleProduct.readProduct(true)) {
            return null;
        }
        try {
            List<ProductVariant> result = productService.findAllProductVariants();
            return ApiResponse.ok(result);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product variant"));
        }
    }

    @Operation(summary = "Find all variants of product")
    @GetMapping("/variant/{productId}")
    public ApiResponse<List<ProductVariantDTO>> findVariantsOfProduct(@PathVariable("productId") Integer productId) {
        if (!super.validateModuleProduct.readProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.findAllProductVariantOfProduct(productId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product variant"));
        }
    }

    @Operation(summary = "Find detail product variant")
    @GetMapping("/variant/{id}")
    public ApiResponse<ProductVariant> findDetailProductVariant(@PathVariable("id") Integer productVariantId) {
        if (!super.validateModuleProduct.readProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.findProductVariantById(productVariantId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Create product")
    @PostMapping("/create")
    public ApiResponse<Product> createProduct(@RequestBody Product product) {
        if (!super.validateModuleProduct.insertProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.saveProduct(product));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Create product variant")
    @PostMapping("/variant/create")
    public ApiResponse<List<ProductVariant>> createProductVariant(@RequestBody ProductVariant productVariant) {
        if (!super.validateModuleProduct.insertProduct(true)) {
            return null;
        }
        try {
            productService.saveProductVariant(productVariant);
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Create product attribute")
    @PostMapping("/attribute/create")
    public ApiResponse<List<ProductVariant>> createProductAttribute(@RequestBody ProductAttribute productAttribute) {
        if (!super.validateModuleProduct.insertProduct(true)) {
            return null;
        }
        try {
            productService.saveProductAttribute(productAttribute);
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "product attribute"));
        }
    }

    @Operation(summary = "Update product")
    @PutMapping("/update/{id}")
    public ApiResponse<ProductDTO> updateProduct(@RequestBody Product product, @PathVariable("id") Integer productId) {
        if (!super.validateModuleProduct.updateProduct(true)) {
            return null;
        }
        try {
            productService.updateProduct(product, productId);
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Update product variant")
    @PutMapping("/variant/update/{id}")
    public ApiResponse<ProductVariant> updateProductVariant(@RequestBody ProductVariant productVariant, @PathVariable("id") Integer productVariantId) {
        if (!super.validateModuleProduct.updateProduct(true)) {
            return null;
        }
        try {
            productService.updateProductVariant(productVariant, productVariantId);
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Update product attribute")
    @PutMapping("/attribute/update/{id}")
    public ApiResponse<ProductVariant> updateProductAttribute(@RequestBody ProductAttribute productAttribute, @PathVariable("id") Integer productAttributeId) {
        if (!super.validateModuleProduct.updateProduct(true)) {
            return null;
        }
        try {
            productService.updateProductAttribute(productAttribute, productAttributeId);
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "product attribute"));
        }
    }

    @Operation(summary = "Delete product")
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable("id") Integer productId) {
        if (!super.validateModuleProduct.deleteProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.deleteProduct(productId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Delete product variant")
    @DeleteMapping("/variant/delete/{id}")
    public ApiResponse<String> deleteProductVariant(@PathVariable("id") Integer productVariantId) {
        if (!super.validateModuleProduct.deleteProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.deleteProductVariant(productVariantId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "product"));
        }
    }

    @Operation(summary = "Delete product attribute")
    @DeleteMapping("/attribute/delete/{id}")
    public ApiResponse<String> deleteProductAttribute(@PathVariable("id") Integer productAttributeId) {
        if (!super.validateModuleProduct.deleteProduct(true)) {
            return null;
        }
        try {
            productService.deleteProductAttribute(productAttributeId);
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "product attribute"));
        }
    }

    @Operation(summary = "Update product image is use default")
    @PostMapping(value = "/{productId}/active-image/{imageId}")
    public ApiResponse<FileStorage> activeImageOfProductOriginal(@PathVariable("productId") Integer productId, @PathVariable("imageId") Integer imageId) {
        if (!super.validateModuleProduct.updateImage(true)) {
            return null;
        }
        if (productId == null || productId <= 0 || imageId == null || imageId <= 0) {
            throw new BadRequestException();
        }
        fileStorageService.setImageActiveOfSanPham(productId, imageId);
        return ApiResponse.ok(null);
    }

    @Operation(summary = "Update variant image is use default")
    @PostMapping(value = "/variant/{productVariantId}/active-image/{imageId}")
    public ApiResponse<FileStorage> activeImageOfProductVariant(@PathVariable("productVariantId") Integer productVariantId, @PathVariable("imageId") Integer imageId) {
        if (!super.validateModuleProduct.updateImage(true)) {
            return null;
        }
        if (productVariantId == null || productVariantId <= 0 || imageId == null || imageId <= 0) {
            throw new BadRequestException();
        }
        fileStorageService.setImageActiveOfBienTheSanPham(productVariantId, imageId);
        return ApiResponse.ok(null);
    }

    @Operation(summary = "Create price")
    @PostMapping(value = "/variant/{productVariantId}/price/create")
    public ApiResponse<Price> createPrice(@RequestBody Price price, @PathVariable("productVariantId") Integer productVariantId) {
        if (!super.validateModuleProduct.priceManagement(true)) {
            return null;
        }
        if (price == null || productVariantId <= 0 || productService.findProductVariantById(productVariantId) == null) {
            throw new BadRequestException();
        }
        return ApiResponse.ok(priceService.save(price));
    }

    @Operation(summary = "Update price")
    @PostMapping(value = "/variant/{productVariantId}/price/update/{priceId}")
    public ApiResponse<Price> updatePrice(@RequestBody Price price,
                                          @PathVariable("productVariantId") Integer productVariantId,
                                          @PathVariable("priceId") Integer priceId) {
        if (!super.validateModuleProduct.priceManagement(true)) {
            return null;
        }
        if (price == null || productVariantId <= 0 || productService.findProductVariantById(productVariantId) == null) {
            throw new BadRequestException();
        }
        priceService.update(price, productVariantId, priceId);
        return ApiResponse.ok(null);
    }

    @Operation(summary = "Find images of all products")
    @GetMapping("/image/all")
    public ApiResponse<List<FileStorage>> viewGallery() {
        if (!super.validateModuleProduct.readGallery(true)) {
            return null;
        }
        return ApiResponse.ok(fileStorageService.getAllImageSanPham(SystemModule.PRODUCT.name()));
    }

    @Operation(summary = "Find all vouchers")
    @GetMapping("/voucher/all")
    public ApiResponse<List<VoucherInfoDTO>> findAllVouchers() {
        if (!super.validateModuleProduct.readVoucher(true)) {
            return null;
        }
        return ApiResponse.ok(voucherService.findAll(null, null, null, null));
    }

    @Operation(summary = "Find detail voucher")
    @GetMapping("/voucher/{voucherInfoId}")
    public ApiResponse<VoucherInfoDTO> findDetailVoucherInfo(@PathVariable("voucherInfoId") Integer voucherInfoId) {
        if (!super.validateModuleProduct.readVoucher(true)) {
            return null;
        }
        return ApiResponse.ok(voucherService.findById(voucherInfoId));
    }

    @Operation(summary = "Create voucher")
    @PostMapping("/voucher/create")
    public ApiResponse<VoucherInfoDTO> createVoucher(@RequestBody VoucherInfo voucherInfo) throws ParseException {
        if (!super.validateModuleProduct.insertVoucher(true)) {
            return null;
        }
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //voucherInfo.setStartTime(dateFormat.parse(request.getParameter("startTime_")));
        //voucherInfo.setEndTime(dateFormat.parse(request.getParameter("endTime_")));

        //List<Integer> listProductToApply = new ArrayList<>();
        //String[] pbienTheSP = request.getParameterValues("productToApply");
        //if (pbienTheSP != null) {
        //    for (String id : pbienTheSP) {
        //        listProductToApply.add(Integer.parseInt(id));
        //    }
        //}
        //if (!listProductToApply.isEmpty()) {
        //    voucherService.save(voucherInfo, listProductToApply);
        //}
        return ApiResponse.ok(null);
    }

    @Operation(summary = "Update voucher")
    @PostMapping("/voucher/update/{voucherInfoId}")
    public ApiResponse<VoucherInfoDTO> updateVoucher(@RequestBody VoucherInfo voucherInfo, @PathVariable("voucherInfoId") Integer voucherInfoId) {
        if (!super.validateModuleProduct.updateVoucher(true)) {
            return null;
        }
        if (voucherInfo == null || voucherService.findById(voucherInfoId) == null) {
            throw new BadRequestException();
        }
        voucherService.update(voucherInfo ,voucherInfoId);
        return ApiResponse.ok(null);
    }

    @Operation(summary = "Delete voucher")
    @PostMapping("/voucher/delete/{voucherInfoId}")
    public ApiResponse<VoucherInfoDTO> deleteVoucher(@PathVariable("voucherInfoId") Integer voucherInfoId) {
        if(!super.validateModuleProduct.deleteVoucher(true)) {
            return null;
        }
        voucherService.detele(voucherInfoId);
        return ApiResponse.ok(null);
    }
}