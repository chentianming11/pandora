package com.chen.pandora.db.starter.config;


import com.chen.pandora.db.starter.common.DataSourceConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 陈添明
 * @date 2019/2/18
 */
@Slf4j
public class DynamicDataSourceContextHolder {

    /**
     * Maintain variable for every thread, to avoid effect other thread
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = ThreadLocal.withInitial(() -> DataSourceConstant.MASTER);


    /**
     * Use master data source.
     */
    public static void useMasterDataSource() {
        CONTEXT_HOLDER.set(DataSourceConstant.MASTER);
    }

    /**
     * Use slave data source.
     */
    public static void useSlaveDataSource() {
        CONTEXT_HOLDER.set(DataSourceConstant.SLAVE);
    }

    /**
     * Get current DataSource
     *
     * @return data source key
     */
    public static String getDataSourceKey() {
        String dataSourceKey = CONTEXT_HOLDER.get();
        return dataSourceKey == null ? DataSourceConstant.MASTER : dataSourceKey;
    }

    /**
     * To set DataSource as default
     */
    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }
}
