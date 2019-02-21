package com.chen.pandora.db.starter.config;


import com.chen.pandora.db.starter.strategy.DynamicDataSourceStrategy;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 陈添明
 * @date 2019/2/18
 */
@Slf4j
@Accessors(chain = true)
@Data
public class DynamicDataSourceContextHolder {

    private static DynamicDataSourceContextHolder ourInstance = new DynamicDataSourceContextHolder();

    public static DynamicDataSourceContextHolder getInstance() {
        return ourInstance;
    }

    private DynamicDataSourceContextHolder() {
    }

    private List<String> slaveKeys;

    private String masterKey;

    private DynamicDataSourceStrategy dynamicDataSourceStrategy;

    /**
     * Maintain variable for every thread, to avoid effect other thread
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * Use master data source.
     */
    public void useMasterDataSource() {
        CONTEXT_HOLDER.set(masterKey);
    }

    /**
     * Use slave data source.
     */
    public void useSlaveDataSource() {
        CONTEXT_HOLDER.set(dynamicDataSourceStrategy.determineDataSourceKey(slaveKeys));
    }

    /**
     * Get current DataSource
     * @return data source key
     */
    public String getDataSource() {
        String dataSourceKey = CONTEXT_HOLDER.get();
        return dataSourceKey == null ? masterKey : dataSourceKey;
    }

    /**
     * To set DataSource as default
     */
    public void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }
}
