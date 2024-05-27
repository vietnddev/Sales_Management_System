package com.flowiee.pms.validate.authorize.product;

import com.flowiee.pms.validate.authorize.BaseAuthorize;
import com.flowiee.pms.model.ACTION;
import org.springframework.stereotype.Component;

@Component
public class VldModuleProduct extends BaseAuthorize implements IVldModuleProduct {
    @Override
    public boolean readProduct(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRD_R.name(), throwException);
    }

    @Override
    public boolean insertProduct(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRD_C.name(), throwException);
    }

    @Override
    public boolean updateProduct(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRD_U.name(), throwException);
    }

    @Override
    public boolean deleteProduct(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRD_D.name(), throwException);
    }

    @Override
    public boolean importProduct(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRD_I.name(), throwException);
    }

    @Override
    public boolean readCombo(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_CBO_R.name(), throwException);
    }

    @Override
    public boolean insertCombo(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_CBO_C.name(), throwException);
    }

    @Override
    public boolean updateCombo(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_CBO_U.name(), throwException);
    }

    @Override
    public boolean deleteCombo(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_CBO_D.name(), throwException);
    }

    @Override
    public boolean updateImage(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRD_U.name(), throwException);
    }

    @Override
    public boolean priceManagement(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRD_PRICE.name(), throwException);
    }

    @Override
    public boolean report(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PRD_RPT.name(), throwException);
    }

    @Override
    public boolean readGallery(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_GAL_R.name(), throwException);
    }

    @Override
    public boolean readMaterial(boolean throwException) {
        return super.isAuthorized(ACTION.STG_MAT_R.name(), throwException);
    }

    @Override
    public boolean insertMaterial(boolean throwException) {
        return super.isAuthorized(ACTION.STG_MAT_C.name(), throwException);
    }

    @Override
    public boolean updateMaterial(boolean throwException) {
        return super.isAuthorized(ACTION.STG_MAT_U.name(), throwException);
    }

    @Override
    public boolean deleteMaterial(boolean throwException) {
        return super.isAuthorized(ACTION.STG_MAT_D.name(), throwException);
    }
}