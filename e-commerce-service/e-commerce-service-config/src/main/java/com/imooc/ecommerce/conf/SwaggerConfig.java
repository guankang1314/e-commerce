package com.imooc.ecommerce.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author qingtian
 * @version 1.0
 * @description: Swagger配置类
 * 原生：/swagger-ui.html'
 * 美化：/doc.html
 * @date 2022/1/8 17:40
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * @description: Swagger的实例是docket ，所以配置docket来配置swagger
     * @param:
     * @return: springfox.documentation.spring.web.plugins.Docket
     * @date: 2022/1/8 17:45
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //选择展示那些接口
                .select()
                //只有com.imooc.ecommerce包内的才去展示
                .apis(RequestHandlerSelectors.basePackage("com.imooc.ecommerce"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @description:  Swagger的描述信息
     * @param:
     * @return: springfox.documentation.service.ApiInfo
     * @date: 2022/1/8 17:45
     */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("imooc-micro-service")
                .description("e-commerce-springcloud-service")
                .contact(new Contact(
                        "qingtian","qingtianblog.com","2673339614@qq.com"
                ))
                .version("1.0")
                .build();
    }
}
