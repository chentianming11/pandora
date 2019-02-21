
package com.chen.pandora.db.starter.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.chen.pandora.db.starter.interceptor.MapKeyFormatInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 陈添明
 * @date 2019/1/26
 */
@EnableTransactionManagement
@Configuration
@EnableConfigurationProperties(PluginProperties.class)
public class MybatisPlusConfig {

    @Autowired
    private PluginProperties pluginProperties;

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        if (pluginProperties.isPagination() == true){
            PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
            return paginationInterceptor;
        } else {
            return null;
        }
    }

    /**
     * Map key下划线转驼峰
     */
    @Bean
    public MapKeyFormatInterceptor mapKeyFormatInterceptor() {
        if (pluginProperties.isMapKeyFormat() == true){
            return new MapKeyFormatInterceptor();
        } else {
            return null;
        }
    }
}