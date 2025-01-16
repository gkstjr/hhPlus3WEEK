package hhplus.ecommerce.support.config;

import hhplus.ecommerce.support.UserArgumentResolver;
import hhplus.ecommerce.support.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final UserInterceptor userInterceptor;
    private final UserArgumentResolver userArgumentResolver;

    public WebConfig(UserInterceptor userInterceptor, UserArgumentResolver userArgumentResolver) {
        this.userInterceptor = userInterceptor;
        this.userArgumentResolver = userArgumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/users/*","/orders/*");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver);
    }
}
