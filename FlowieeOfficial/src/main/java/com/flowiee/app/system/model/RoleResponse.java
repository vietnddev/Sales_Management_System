package com.flowiee.app.system.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RoleResponse {
    @Data
    public static class Action {
        private boolean isChecked;
        private String keyAction;
        private String valueAction;
    }

    private Map<String, String> module;
    private List<RoleResponse.Action> action;
}