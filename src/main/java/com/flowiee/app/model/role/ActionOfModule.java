package com.flowiee.app.model.role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionOfModule {
    private String actionKey;
    private String actionLabel;
    private String moduleKey;
    
    public ActionOfModule() {
    	
    }
    
    public ActionOfModule(String actionKey, String actionLabel, String moduleKey) {
    	this.actionKey = actionKey;
    	this.actionLabel = actionLabel;
    	this.moduleKey = moduleKey;
    }
}