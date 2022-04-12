package com.vip.knife4j.knife4j;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.vip.knife4j.swagger.SwaggerAutoConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

/**
 * knife4j配置
 *
 * @author wgb
 * @date 2022/04/11
 */
@Configuration
@EnableKnife4j
@Import({SwaggerAutoConfig.class, BeanValidatorPluginsConfiguration.class})
public class Knife4jConfig {
}