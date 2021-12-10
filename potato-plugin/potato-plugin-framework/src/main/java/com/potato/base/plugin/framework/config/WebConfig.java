package com.potato.base.plugin.framework.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * WebMvcConfig
 * 不要使用 WebMvcConfigurationSupport
 * @author lizhifu
 * @date 2021/12/10
 */
@Configuration
public class WebConfig implements WebMvcRegistrations {
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new CustomRequestMappingHandlerMapping();
    }
}
