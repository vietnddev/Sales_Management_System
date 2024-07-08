package com.flowiee.pms.model.role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ActionModel {
    String actionKey;
    String actionLabel;
    String moduleKey;
}