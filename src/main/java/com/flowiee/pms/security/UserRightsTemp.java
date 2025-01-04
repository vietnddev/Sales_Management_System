package com.flowiee.pms.security;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRightsTemp {
    private static Map<String, List<String>> mvRightsAreGrantedInRuntime = new HashMap<>();

    public boolean checkRight(String pAccountId, String pRight) {
        if (pAccountId == null || pRight == null) {
            return false;
        }
        List<String> lvRights = mvRightsAreGrantedInRuntime.get(pAccountId);
        if (CollectionUtils.isEmpty(lvRights)) {
            return false;
        }
        for (String lvRight : lvRights) {
            if (lvRight.equals(pRight)) {
                return true;
            }
        }
        return false;
    }

    public void addTempRights(String pAccountId, List<String> pRights) {
        removeTempRights(pAccountId);
        mvRightsAreGrantedInRuntime.put(pAccountId, pRights);
    }

    public void removeTempRights(String pAccountId) {
        mvRightsAreGrantedInRuntime.remove(pAccountId);
    }

    public void reset() {
        mvRightsAreGrantedInRuntime.clear();
    }
}