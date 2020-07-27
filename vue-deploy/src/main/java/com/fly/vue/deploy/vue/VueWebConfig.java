package com.fly.vue.deploy.vue;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * @author 张攀钦
 * @date 2020-07-27-16:48
 * web 相关配置
 */
@Configuration
public class VueWebConfig implements WebMvcConfigurer {
    /**
     * 映射的静态资源路径
     */
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"file:./static/", "classpath:/META-INF/resources/",
            "classpath:/resources/", "classpath:/static/", "classpath:/public/"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加静态资源缓存
        CacheControl cacheControl = CacheControl.maxAge(5, TimeUnit.HOURS).cachePublic();
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS).setCacheControl(cacheControl);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new VueCookieInterceptor()).addPathPatterns("/test/**");
    }

    @Bean
    public VueErrorController vueErrorController() {
        return new VueErrorController(new DefaultErrorAttributes());
    }
}
