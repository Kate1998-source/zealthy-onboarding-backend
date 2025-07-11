package com.zealthy.onboarding.service;

import com.zealthy.onboarding.entity.OnboardingConfig;
import com.zealthy.onboarding.entity.ComponentType;
import com.zealthy.onboarding.repository.OnboardingConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class AdminService {

    @Autowired
    private OnboardingConfigRepository configRepository;

    public List<OnboardingConfig> getAllConfigurations() {
        return configRepository.findAllByOrderByPageNumberAsc();
    }

    public Map<Integer, List<ComponentType>> getConfigurationByPage() {
        List<OnboardingConfig> configs = configRepository.findAll();
        Map<Integer, List<ComponentType>> pageConfig = new HashMap<>();
        
        pageConfig.put(2, new java.util.ArrayList<>());
        pageConfig.put(3, new java.util.ArrayList<>());
        
        for (OnboardingConfig config : configs) {
            pageConfig.get(config.getPageNumber()).add(config.getComponentName());
        }
        
        return pageConfig;
    }

    public void updateConfiguration(Map<ComponentType, Integer> componentPageMap) {
        // Validate that each page has at least one component
        Map<Integer, Integer> pageComponentCount = new HashMap<>();
        pageComponentCount.put(2, 0);
        pageComponentCount.put(3, 0);
        
        for (Integer pageNumber : componentPageMap.values()) {
            pageComponentCount.put(pageNumber, pageComponentCount.get(pageNumber) + 1);
        }
        
        if (pageComponentCount.get(2) == 0 || pageComponentCount.get(3) == 0) {
            throw new RuntimeException("Each page must have at least one component");
        }

        // Delete existing configuration
        configRepository.deleteAll();

        // Save new configuration
        for (Map.Entry<ComponentType, Integer> entry : componentPageMap.entrySet()) {
            OnboardingConfig config = new OnboardingConfig(entry.getValue(), entry.getKey());
            configRepository.save(config);
        }
    }

    public List<OnboardingConfig> getComponentsForPage(Integer pageNumber) {
        return configRepository.findByPageNumber(pageNumber);
    }
}