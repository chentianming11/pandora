package com.chen.pandora.db.starter.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 陈添明
 * @date 2019/1/28
 */
@ConfigurationProperties("db-starter.plugin")
@Data
public class PluginProperties {

    /**
     * map-key下划线转驼峰插件配置
     */
    private boolean mapKeyFormat = true;

    /**
     * 分页插件配置
     */
    private boolean pagination = true;
}
