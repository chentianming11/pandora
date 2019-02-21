package com.chen.pandora.db.starter.util;

import com.chen.pandora.db.starter.config.P6SpyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 陈添明
 * @date 2019/1/31
 */
@Component
@EnableConfigurationProperties(P6SpyProperties.class)
public class P6SpyUtil {

    public static boolean DISPLAY_FORMAT_SQL;

    @Autowired
    private P6SpyProperties p6SpyProperties;


    @PostConstruct
    public void init() {
        DISPLAY_FORMAT_SQL = p6SpyProperties.isDisplayFormatSql();
    }
}
