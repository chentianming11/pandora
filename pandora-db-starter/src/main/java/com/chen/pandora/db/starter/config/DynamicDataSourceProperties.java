/**
 * Copyright © 2018 organization baomidou
 * <pre>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <pre/>
 */
package com.chen.pandora.db.starter.config;

import com.chen.pandora.db.starter.config.hikari.HikariCpConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.Ordered;

/**
 * @author 陈添明
 * @date 2019/2/18
 */
@Data
@ConfigurationProperties(prefix = "db-starter.dynamic.datasource")
public class DynamicDataSourceProperties {

    /**
     * 主数据源配置
     */
    @NestedConfigurationProperty
    private DataSourceProperty master = new DataSourceProperty();

    /**
     * 从数据源配置
     */
    @NestedConfigurationProperty
    private DataSourceProperty slave = new DataSourceProperty();

    /**
     * aop切面顺序，默认优先级最高
     */
    private Integer order = Ordered.HIGHEST_PRECEDENCE;

    /**
     * HikariCp全局参数配置
     */
    @NestedConfigurationProperty
    private HikariCpConfig hikari = new HikariCpConfig();
}
