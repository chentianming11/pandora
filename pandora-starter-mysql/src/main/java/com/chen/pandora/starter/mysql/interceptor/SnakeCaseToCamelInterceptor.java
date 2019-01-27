package com.chen.pandora.starter.mysql.interceptor;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import strman.Strman;

import java.sql.Statement;
import java.util.*;

/**
 * 下划线转驼峰
 * map的key的下划线转驼峰 并且去除is前缀，并将value转成boolean
 * 如果key不包含下划线，只考虑去除is前缀，并将value转成boolean
 *
 * @author 陈添明
 * @date 2018/8/24
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class SnakeCaseToCamelInterceptor implements Interceptor {

    public static final String IS = "is";
    public static final String UNDER_LINE = "_";


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 先执行得到结果，再对结果进行处理
        List<Object> list = (List<Object>) invocation.proceed();
        list.forEach(object -> {
            if (object instanceof Map) {
                handleMap((Map) object);
            }
        });
        return list;
    }

    private void handleMap(Map<String, Object> map) {
        Set<String> keySet = new HashSet(map.keySet());
        for (String key : keySet) {
            Object value = map.get(key);
            String newKey = null;
            Object newValue = null;
            // 如果是key是下划线模式，转成驼峰。
            if (key.contains(UNDER_LINE)) {
                // 先全部转成小写
                String lowerCaseKey = key.toLowerCase();
                newKey = Strman.toCamelCase(lowerCaseKey);
                newValue = value;
                if (lowerCaseKey.startsWith(IS)) {
                    if (value instanceof Number) {
                        newKey = Strman.toCamelCase(lowerCaseKey.substring(2));
                        newValue = ((Number) value).longValue() == 0L ? false : true;
                    }
                }
                map.remove(key);
                map.put(newKey, newValue);
            } else {
                // 如果key本身不是下划线，则将is头去掉，并将value转为boolean
                if (key.startsWith(IS)) {
                    if (value instanceof Number) {
                        newKey = Strman.toCamelCase(key.substring(2));
                        newValue = ((Number) value).longValue() == 0L ? false : true;
                    }
                    map.remove(key);
                    map.put(newKey, newValue);
                }
            }
        }
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
