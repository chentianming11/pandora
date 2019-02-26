
package com.chen.pandora.db.starter.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.chen.pandora.db.starter.interceptor.MapKeyFormatInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈添明
 * @date 2019/1/26
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    @ConditionalOnProperty(name = "db-starter.plugin.pagination", matchIfMissing = true, havingValue = "true")
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * Map key下划线转驼峰
     */
    @Bean
    @ConditionalOnProperty(name = "db-starter.plugin.map-key-format", matchIfMissing = true, havingValue = "true")
    public MapKeyFormatInterceptor mapKeyFormatInterceptor() {
        return new MapKeyFormatInterceptor();
    }
}