package com.chen.pandora.db.starter.config;

import com.chen.pandora.db.starter.config.hikari.HikariCpConfig;
import com.chen.pandora.db.starter.config.properties.DynamicDataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 陈添明
 * @date 2019/2/18
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DataSourceConfig {


    @Autowired
    private DynamicDataSourceProperties properties;


    /**
     * Dynamic data source.
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() throws IllegalAccessException, InstantiationException {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(8);
        Map<String, DataSourceProperty> mapping = properties.getMapping();
        mapping.forEach((dataSourceKey, dataSourceProperty) -> {
            DataSource hikariDataSource = createHikariDataSource(dataSourceProperty);
            dataSourceMap.put(dataSourceKey, hikariDataSource);
            // 设置数据源到DynamicDataSourceContextHolder
            if (Objects.equals(dataSourceKey, properties.getPrimary())){
                DynamicDataSourceContextHolder.getInstance().setMasterKey(dataSourceKey);
            } else {
                DynamicDataSourceContextHolder.getInstance().getSlaveKeys().add(dataSourceKey);
            }
        });
        // 设置多数据源选择策略
        DynamicDataSourceContextHolder.getInstance()
                .setDynamicDataSourceStrategy(properties.getStrategy().newInstance());

        // 设置默认数据源
        dynamicRoutingDataSource.setDefaultTargetDataSource(dataSourceMap.get(properties.getPrimary()));

        // 设置全部数据源
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        return dynamicRoutingDataSource;
    }

    /**
     * 创建HikariDataSource
     * @param dataSourceProperty
     * @return
     */
    public DataSource createHikariDataSource(DataSourceProperty dataSourceProperty) {
        HikariCpConfig hikariCpConfig = dataSourceProperty.getHikari();
        HikariConfig config = hikariCpConfig.toHikariConfig(properties.getHikari());
        config.setUsername(dataSourceProperty.getUsername());
        config.setPassword(dataSourceProperty.getPassword());
        config.setJdbcUrl(dataSourceProperty.getUrl());
        config.setDriverClassName(dataSourceProperty.getDriverClassName());
        config.setPoolName(dataSourceProperty.getPollName());
        return new HikariDataSource(config);
    }
}
