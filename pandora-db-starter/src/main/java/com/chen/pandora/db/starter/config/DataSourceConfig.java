package com.chen.pandora.db.starter.config;

import com.chen.pandora.db.starter.common.DataSourceConstant;
import com.chen.pandora.db.starter.config.hikari.HikariCpConfig;
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
    public DataSource dataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        DataSource master = createHikariDataSource(properties.getMaster());
        DataSource slave = createHikariDataSource(properties.getSlave());
        dataSourceMap.put(DataSourceConstant.MASTER, master);
        dataSourceMap.put(DataSourceConstant.SLAVE, slave);
        // Set master datasource as default
        dynamicRoutingDataSource.setDefaultTargetDataSource(master);
        // Set master and slave datasource as target datasource
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
