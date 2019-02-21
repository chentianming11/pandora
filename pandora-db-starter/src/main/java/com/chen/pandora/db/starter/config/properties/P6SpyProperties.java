package com.chen.pandora.db.starter.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 陈添明
 * @date 2019/1/31
 */
@ConfigurationProperties("db-starter.p6spy")
@Data
public class P6SpyProperties {

    private boolean displayFormatSql = false;
}
