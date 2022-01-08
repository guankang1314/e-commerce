package com.imooc.ecommerce.conf;

import com.imooc.ecommerce.filter.LoginUserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author qingtian
 * @version 1.0
 * @description: WebMvc配置
 * @date 2021/12/27 20:40
 */
@Configuration
public class ImoocWebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * @description: 添加拦截器配置
     * @param: registry
     * @return: void
     * @date: 2021/12/27 20:41
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginUserInfoInterceptor())
                .addPathPatterns("/**").order(0);
    }

    /**
     * @description: 让MVC加载 Swagger 的静态资源
     * @param: registry
     * @return: void
     * @date: 2022/1/8 17:51
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars");
        super.addResourceHandlers(registry);
    }
}
