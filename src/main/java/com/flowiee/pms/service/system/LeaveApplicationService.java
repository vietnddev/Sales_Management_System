package com.flowiee.pms.service.system;

import com.flowiee.pms.entity.system.LeaveApplication;
import com.flowiee.pms.model.payload.LeaveApplicationReq;

public interface LeaveApplicationService {
    LeaveApplication getApplicationById(Long leaveApplicationId);

    LeaveApplication submitLeaveRequest(LeaveApplicationReq leaveRequest);

    LeaveApplication approveLeaveRequest(Long requestId, String managerComment);

    LeaveApplication rejectLeaveRequest(Long requestId, String managerComment);
}