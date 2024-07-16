package com.rts.config;

import com.rts.constants.PathConstants;
import com.rts.interceptor.AuthInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: RTS
 * @CreateDateTime: 2024/7/15 19:12
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(PathConstants.INTERCEPTOR_PATH)
                .excludePathPatterns(PathConstants.AUTH_REGISTER,PathConstants.AUTH_LOGIN);
    }


}
