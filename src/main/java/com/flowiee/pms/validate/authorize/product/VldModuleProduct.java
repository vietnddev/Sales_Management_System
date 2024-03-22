package com.flowiee.pms.validate.authorize.product;

import com.flowiee.pms.base.BaseAuthorize;
import com.flowiee.pms.model.ACTION;
import org.springframework.stereotype.Component;

@Component
public class VldModuleProduct extends BaseAuthorize implements IVldModuleProduct {
    @Override
    public boolean readProduct(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRODUCT_READ.name(), throwException);
    }

    @Override
    public boolean insertProduct(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRODUCT_CREATE.name(), throwException);
    }

    @Override
    public boolean updateProduct(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRODUCT_UPDATE.name(), throwException);
    }

    @Override
    public boolean deleteProduct(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRODUCT_DELETE.name(), throwException);
    }

    @Override
    public boolean importProduct(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRODUCT_IMPORT.name(), throwException);
    }

    @Override
    public boolean updateImage(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRODUCT_UPDATE.name(), throwException);
    }

    @Override
    public boolean priceManagement(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRODUCT_PRICE.name(), throwException);
    }

    @Override
    public boolean report(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRODUCT_REPORT.name(), throwException);
    }

    @Override
    public boolean readGallery(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_GALLERY_READ.name(), throwException);
    }

    @Override
    public boolean readMaterial(boolean throwException) {
        return super.isAuthorized(ACTION.STG_MATERIAL_READ.name(), throwException);
    }

    @Override
    public boolean insertMaterial(boolean throwException) {
        return super.isAuthorized(ACTION.STG_MATERIAL_CREATE.name(), throwException);
    }

    @Override
    public boolean updateMaterial(boolean throwException) {
        return super.isAuthorized(ACTION.STG_MATERIAL_UPDATE.name(), throwException);
    }

    @Override
    public boolean deleteMaterial(boolean throwException) {
        return super.isAuthorized(ACTION.STG_MATERIAL_DELETE.name(), throwException);
    }
}