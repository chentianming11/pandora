package com.chen.pandora.db.starter.config;

import com.chen.pandora.db.starter.annotation.Slave;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 陈添明
 * @date 2019/2/18
 */
@Aspect
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
    /**
     * 使用从库
     */
    @Around("execution(* com.ke.jiaoyi..controller..*(..)) && @annotation(slave))")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, Slave slave) throws Throwable {
        try {
            DynamicDataSourceContextHolder.useSlaveDataSource();
            Object result = proceedingJoinPoint.proceed();
            return result;
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceKey();
            logger.info("restore database connection");
        }
    }
}