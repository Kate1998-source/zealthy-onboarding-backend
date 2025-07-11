package com.zealthy.onboarding.repository;

import com.zealthy.onboarding.entity.OnboardingConfig;
import com.zealthy.onboarding.entity.ComponentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OnboardingConfigRepository extends JpaRepository<OnboardingConfig, Long> {
    
    List<OnboardingConfig> findByPageNumber(Integer pageNumber);
    Optional<OnboardingConfig> findByComponentName(ComponentType componentName);
    List<OnboardingConfig> findAllByOrderByPageNumberAsc();
}