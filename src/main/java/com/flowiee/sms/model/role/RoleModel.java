package com.flowiee.sms.model.role;

import lombok.Data;

@Data
public class RoleModel {
    private Integer accountId;
    private ModuleModel module;
    private ActionModel action;
    private Boolean isAuthor;
}