package com.flowiee.pms.model.role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ActionModel {
    String actionKey;
    String actionLabel;
    String moduleKey;

    public ActionModel() {}
    
    public ActionModel(String actionKey, String actionLabel, String moduleKey) {
    	this.actionKey = actionKey;
    	this.actionLabel = actionLabel;
    	this.moduleKey = moduleKey;
    }
}