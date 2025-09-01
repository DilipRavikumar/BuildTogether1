package com.buildtogether.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@Slf4j
public class LoggingConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLoggingInterceptor());
    }

    public static class RequestLoggingInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            String requestURI = request.getRequestURI();
            String method = request.getMethod();
            String remoteAddr = request.getRemoteAddr();
            
            log.info("=== HTTP REQUEST ===");
            log.info("Method: {} {}", method, requestURI);
            log.info("Remote Address: {}", remoteAddr);
            log.info("User Agent: {}", request.getHeader("User-Agent"));
            log.info("Content Type: {}", request.getContentType());
            log.info("Timestamp: {}", System.currentTimeMillis());
            
            request.setAttribute("startTime", System.currentTimeMillis());
            
            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            String requestURI = request.getRequestURI();
            String method = request.getMethod();
            int status = response.getStatus();
            long startTime = (Long) request.getAttribute("startTime");
            long duration = System.currentTimeMillis() - startTime;
            
            log.info("=== HTTP RESPONSE ===");
            log.info("Method: {} {}", method, requestURI);
            log.info("Status: {}", status);
            log.info("Duration: {}ms", duration);
            
            if (ex != null) {
                log.error("Exception occurred: {}", ex.getMessage(), ex);
            }
            
            if (status >= 200 && status < 300) {
                log.info("Request completed successfully");
            } else if (status >= 400 && status < 500) {
                log.warn("Client error occurred");
            } else if (status >= 500) {
                log.error("Server error occurred");
            }
        }
    }
}
