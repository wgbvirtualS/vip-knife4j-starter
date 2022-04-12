package com.vip.knife4j.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Swagger的自动配置类
 *
 * @author wgb
 * @date 2022/2/16 9:26
 */
@Configuration
@EnableSwagger2
@ConditionalOnClass({Controller.class})
@ConditionalOnMissingBean({Docket.class})
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfig implements WebMvcConfigurer {

    private final SwaggerProperties swaggerProperties;

    public SwaggerAutoConfig(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    /**
     * 配置了Swagger的Docket的bean实例
     * enable是否启动swagger，如果为False则Swagger不能在浏览器访问
     *
     * @return {@link Docket}
     */
    @Bean
    public Docket autoEnableSwagger() {
        String basePackage = swaggerProperties.getBasePackage();
        Set<String> set = new HashSet<>();
        set.add("https");
        set.add("http");
        Docket docket = new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
                // 定义是否开启swagger，false为关闭，可以通过变量控制
                .enable(true)
                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo())
                // 支持的通讯协议集合
                .protocols(set)
                // 授权信息设置，必要的header token等认证信息
                .securitySchemes(securitySchemes())
                // 授权信息全局应用
                .securityContexts(securityContexts());
        // 设置扫描路径及过滤路径（无侵入代码的体现）
        ApiSelectorBuilder builder = docket.select();
        if (null == basePackage || "".equals(basePackage.trim())) {
            return builder.paths(PathSelectors.any()).build();
        } else {
            return builder.apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any()).build();
        }
    }

    /**
     * 配置Swagger 信息 = ApiInfo
     *
     * @return {@link ApiInfo}
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getApiInfo().getTitle())
                .description(swaggerProperties.getApiInfo().getDescription())
                .termsOfServiceUrl(swaggerProperties.getApiInfo().getTermsOfServiceUrl())
                .contact(new Contact(swaggerProperties.getAuthor().getName(), swaggerProperties.getAuthor().getUrl()
                        , swaggerProperties.getAuthor().getEmail()))
                .version(swaggerProperties.getApiInfo().getVersion())
                .license(swaggerProperties.getApiInfo().getLicense())
                .licenseUrl(swaggerProperties.getApiInfo().getLicenseUrl())
                .build();

    }

    /**
     * 设置授权信息
     *
     * @return {@link List}<{@link SecurityScheme}>
     */
    private List<? extends SecurityScheme> securitySchemes() {
        // 添加OAuth2的令牌认证
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        return Collections.singletonList(apiKey);
    }

    /**
     * 授权信息全局应用
     *
     * @return {@link List}<{@link SecurityContext}>
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("Authorization",
                        new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")})))
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build());
    }
}
