package com.zealthy.onboarding.dto;

import com.zealthy.onboarding.entity.ComponentType;
import java.util.Map;

public class ConfigUpdateRequest {

    private Map<ComponentType, Integer> componentPageMap;

    public ConfigUpdateRequest() {}

    public ConfigUpdateRequest(Map<ComponentType, Integer> componentPageMap) {
        this.componentPageMap = componentPageMap;
    }

    public Map<ComponentType, Integer> getComponentPageMap() {
        return componentPageMap;
    }

    public void setComponentPageMap(Map<ComponentType, Integer> componentPageMap) {
        this.componentPageMap = componentPageMap;
    }
}