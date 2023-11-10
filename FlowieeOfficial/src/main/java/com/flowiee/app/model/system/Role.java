package com.flowiee.app.model.system;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Role {
    @Data
    public static class Action {
        private boolean isChecked;
        private String keyAction;
        private String valueAction;
    }

    private Map<String, String> module;
    private List<Action> action;
}