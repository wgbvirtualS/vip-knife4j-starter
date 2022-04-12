package com.vip.knife4j.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger配置文件属性
 *
 * @author wgb
 * @date 2022/02/16
 */
@ConfigurationProperties(prefix = "knife4j")
public class SwaggerProperties {

    /**
     * 扫描包路径
     */
    private String basePackage;

    /**
     * 作者
     */
    private Author author = new Author();

    /**
     * API的相关信息
     */
    private ApiInfo apiInfo = new ApiInfo();

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public ApiInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

    public static class ApiInfo {
        String title = "Swagger Restful API";
        String description = "This is a swagger api desc";
        String version = "1.0.0";
        String termsOfServiceUrl = "";
        String license = "";
        String licenseUrl = "";

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }
    }

    public static class Author {
        private String name = "";

        private String email = "";

        private String url = "";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
