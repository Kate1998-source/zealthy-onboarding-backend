package com.zealthy.onboarding.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${zealthy.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] allowedOrigins = getAllowedOrigins();
        
        System.out.println("CORS: Configuring allowed origins: " + Arrays.toString(allowedOrigins));
        
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600); // 1 hour preflight cache
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Production-ready CORS settings
        configuration.setAllowedOrigins(List.of(getAllowedOrigins()));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        
        return source;
    }

    private String[] getAllowedOrigins() {
        // Production-ready allowed origins
        return new String[] {
            frontendUrl,                                     // From environment variable
            "http://localhost:3000",                         // Local development
            "http://localhost:3001",                         // Alternative local port
            "https://*.vercel.app",                          // Vercel deployments
            "https://*.netlify.app",                         // Netlify deployments
            "https://zealthy-onboarding-frontend.vercel.app", // Specific production URL
            "https://zealthy-frontend.vercel.app",        // Alternative production URL
            "https://zealthy-onboarding-backend-production.up.railway.app/api/users"
        };
    }
}
