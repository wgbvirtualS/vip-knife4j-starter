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
 * Automatic configuration class for Swagger
 *
 * @author wgb
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
     * Docket bean instance with Swagger configured
     * Enable Indicates whether to enable Swagger. If the value is False, Swagger cannot be accessed in the browser
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
                // Define whether to enable swagger，false is close, It can be controlled by variables
                .enable(true)
                // Set the API meta-information to be included in the JSON ResourceListing response.
                .apiInfo(apiInfo())
                // Set of supported communication protocols
                .protocols(set)
                // Authorization information Set authentication information, such as header and token
                .securitySchemes(securitySchemes())
                // Global application of authorization information
                .securityContexts(securityContexts());
        // Set scan path and filter path (non-intrusive code embodiment)
        ApiSelectorBuilder builder = docket.select();
        if (null == basePackage || "".equals(basePackage.trim())) {
            return builder.paths(PathSelectors.any()).build();
        } else {
            return builder.apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any()).build();
        }
    }

    /**
     * Configure Swagger information
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
     * Setting Authorization Information
     *
     * @return {@link List}<{@link SecurityScheme}>
     */
    private List<? extends SecurityScheme> securitySchemes() {
        // Added token authentication for Oauth2
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        return Collections.singletonList(apiKey);
    }

    /**
     * Global application of authorization information
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
