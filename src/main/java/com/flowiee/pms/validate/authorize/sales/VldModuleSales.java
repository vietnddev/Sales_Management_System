package com.flowiee.pms.validate.authorize.sales;

import com.flowiee.pms.base.BaseAuthorize;
import com.flowiee.pms.model.ACTION;
import org.springframework.stereotype.Component;

@Component
public class VldModuleSales extends BaseAuthorize implements IVldModuleSales {
    @Override
    public boolean dashboard(boolean throwException) {
        return super.isAuthorized(ACTION.STG_DASHBOARD.name(), throwException);
    }

    @Override
    public boolean importGoods(boolean throwException) {
        return super.isAuthorized(ACTION.STG_TICKET_IMPORT_GOODS.name(), throwException);
    }

    @Override
    public boolean exportGoods(boolean throwException) {
        return super.isAuthorized(ACTION.STG_TICKET_EXPORT_GOODS.name(), throwException);
    }

    @Override
    public boolean readOrder(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_ORDERS_READ.name(), throwException);
    }

    @Override
    public boolean insertOrder(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_ORDERS_CREATE.name(), throwException);
    }

    @Override
    public boolean updateOrder(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_ORDERS_UPDATE.name(), throwException);
    }

    @Override
    public boolean deleteOrder(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_ORDERS_DELETE.name(), throwException);
    }

    @Override
    public boolean readCustomer(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_CUSTOMER_READ.name(), throwException);
    }

    @Override
    public boolean insertCustomer(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_CUSTOMER_CREATE.name(), throwException);
    }

    @Override
    public boolean updateCustomer(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_CUSTOMER_UPDATE.name(), throwException);
    }

    @Override
    public boolean deleteCustomer(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_CUSTOMER_DELETE.name(), throwException);
    }

    @Override
    public boolean readVoucher(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_VOUCHER_READ.name(), throwException);
    }

    @Override
    public boolean insertVoucher(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_VOUCHER_CREATE.name(), throwException);
    }

    @Override
    public boolean updateVoucher(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_VOUCHER_UPDATE.name(), throwException);
    }

    @Override
    public boolean deleteVoucher(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_VOUCHER_DELETE.name(), throwException);
    }

    @Override
    public boolean readSupplier(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_SUPPLIER_READ.name(), throwException);
    }

    @Override
    public boolean insertSupplier(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_SUPPLIER_CREATE.name(), throwException);
    }

    @Override
    public boolean updateSupplier(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_SUPPLIER_UPDATE.name(), throwException);
    }

    @Override
    public boolean deleteSupplier(boolean throwException) {
        return super.isAuthorized(ACTION.PRO_SUPPLIER_DELETE.name(), throwException);
    }
}