package com.chen.pandora.db.starter.interceptor;

import com.baomidou.mybatisplus.core.toolkit.sql.SqlFormatter;
import com.chen.pandora.db.starter.util.P6SpyUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 陈添明
 * @date 2019/1/26
 */
@Slf4j
public class P6SpyLogger implements MessageFormattingStrategy {

    private static final String SPACE = " ";
    private static final String NEWLINE = "\n";
    private final static SqlFormatter SQL_FORMATTER = new SqlFormatter();


    /**
     * Formats a log message for the logging module
     *
     * @param connectionId the id of the connection
     * @param now          the current ime expressing in milliseconds
     * @param elapsed      the time in milliseconds that the operation took to complete
     * @param category     the category of the operation
     * @param prepared     the SQL statement with all bind variables replaced with actual values
     * @param sql          the sql statement executed
     * @param url          the database url where the sql statement executed
     * @return the formatted log message
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        String retStr = "";
        try {
            if (!sql.trim().equals("")) {
                sql = sql.replaceAll("[\\s]+", SPACE);
                retStr = "P6SpySqlLogger" + " | " + elapsed + " | " + category + " | connection: " + connectionId + " | " + sql + ";";
                if (P6SpyUtil.DISPLAY_FORMAT_SQL == true) {
                    // 格式化sql用于控制台输出-在非生产开启阶段开启
                    StringBuilder formatSql = new StringBuilder()
                            .append(" Time：").append(elapsed)
                            .append(" ms;  ")
                            .append("Execute SQL：")
                            .append(SQL_FORMATTER.format(sql))
                            .append(NEWLINE);
                    log.info(formatSql.toString());
                }
            }
        } catch (Exception e) {
            log.info("", e);
        }
        return retStr;
    }
}