package com.zealthy.onboarding.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "onboarding_config")
public class OnboardingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page_number", nullable = false)
    private Integer pageNumber;

    @Column(name = "component_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private ComponentType componentName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public OnboardingConfig() {}

    public OnboardingConfig(Integer pageNumber, ComponentType componentName) {
        this.pageNumber = pageNumber;
        this.componentName = componentName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getPageNumber() { return pageNumber; }
    public void setPageNumber(Integer pageNumber) { this.pageNumber = pageNumber; }

    public ComponentType getComponentName() { return componentName; }
    public void setComponentName(ComponentType componentName) { this.componentName = componentName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}