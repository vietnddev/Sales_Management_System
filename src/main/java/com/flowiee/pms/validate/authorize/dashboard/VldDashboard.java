package com.flowiee.pms.validate.authorize.dashboard;

import com.flowiee.pms.base.BaseAuthorize;
import com.flowiee.pms.utils.AppConstants;
import org.springframework.stereotype.Component;

@Component
public class VldDashboard extends BaseAuthorize implements IVldDashboard {
    @Override
    public boolean readDashboard(boolean throwException) {
        return super.isAuthorized(AppConstants.DASHBOARD_ACTION.READ_DASHBOARD.name(), throwException);
    }
}