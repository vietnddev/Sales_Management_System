package com.flowiee.app.system.model;

import lombok.Data;

@Data
public class FlowieeRole {
    private Integer accountId;
    private ModuleOfFlowiee module;
    private ActionOfModule action;
    private Boolean isAuthor;
}