package com.flowiee.pms.validate.authorize.dashboard;

import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.validate.authorize.BaseAuthorize;
import org.springframework.stereotype.Component;

@Component
public class VldDashboard extends BaseAuthorize implements IVldDashboard {
    @Override
    public boolean readDashboard(boolean throwException) {
        return super.isAuthorized(ACTION.READ_DASHBOARD, throwException);
    }
}