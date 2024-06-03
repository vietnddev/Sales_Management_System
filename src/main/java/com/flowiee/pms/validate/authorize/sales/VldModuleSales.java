package com.flowiee.pms.validate.authorize.sales;

import com.flowiee.pms.validate.authorize.BaseAuthorize;
import com.flowiee.pms.utils.constants.ACTION;
import org.springframework.stereotype.Component;

@Component
public class VldModuleSales extends BaseAuthorize implements IVldModuleSales {
    @Override
    public boolean dashboard(boolean throwException) {
        return super.isAuthorized(ACTION.STG_DASHBOARD.name(), throwException);
    }

    @Override
    public boolean importGoods(boolean throwException) {
        return super.isAuthorized(ACTION.STG_TICKET_IM.name(), throwException);
    }

    @Override
    public boolean exportGoods(boolean throwException) {
        return super.isAuthorized(ACTION.STG_TICKET_EX.name(), throwException);
    }

    @Override
    public boolean readOrder(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_ORD_R.name(), throwException);
    }

    @Override
    public boolean insertOrder(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_ORD_C.name(), throwException);
    }

    @Override
    public boolean updateOrder(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_ORD_U.name(), throwException);
    }

    @Override
    public boolean deleteOrder(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_ORD_D.name(), throwException);
    }

    @Override
    public boolean readCustomer(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_CUS_R.name(), throwException);
    }

    @Override
    public boolean insertCustomer(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_CUS_C.name(), throwException);
    }

    @Override
    public boolean updateCustomer(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_CUS_U.name(), throwException);
    }

    @Override
    public boolean deleteCustomer(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_CUS_D.name(), throwException);
    }

    @Override
    public boolean readVoucher(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_VOU_R.name(), throwException);
    }

    @Override
    public boolean insertVoucher(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_VOU_C.name(), throwException);
    }

    @Override
    public boolean updateVoucher(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_VOU_U.name(), throwException);
    }

    @Override
    public boolean deleteVoucher(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_VOU_D.name(), throwException);
    }

    @Override
    public boolean readSupplier(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_SUP_R.name(), throwException);
    }

    @Override
    public boolean insertSupplier(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_SUP_C.name(), throwException);
    }

    @Override
    public boolean updateSupplier(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_SUP_U.name(), throwException);
    }

    @Override
    public boolean deleteSupplier(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_SUP_D.name(), throwException);
    }

    @Override
    public boolean readPromotion(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PROMO_R.name(), throwException);
    }

    @Override
    public boolean insertPromotion(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PROMO_C.name(), throwException);
    }

    @Override
    public boolean updatePromotion(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PROMO_U.name(), throwException);
    }

    @Override
    public boolean deletePromotion(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_PROMO_D.name(), throwException);
    }

    @Override
    public boolean readLedgerTransaction(boolean throwException) {
        return super.isAuthorized(ACTION.SLS_RCT_R.name(), throwException);
    }

    @Override
    public boolean insertLedgerTransaction(boolean throwException) {
        return super.isAuthorized(ACTION.SLS_RCT_C.name(), throwException);
    }

    @Override
    public boolean updateLedgerTransaction(boolean throwException) {
        return super.isAuthorized(ACTION.SLS_RCT_U.name(), throwException);
    }

    @Override
    public boolean readGeneralLedger(boolean throwException) {
        return super.isAuthorized(ACTION.SLS_LEDGER.name(), throwException);
    }
}