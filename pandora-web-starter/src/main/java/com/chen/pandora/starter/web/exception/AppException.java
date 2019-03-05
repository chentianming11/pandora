package com.chen.pandora.starter.web.exception;

import com.chen.pandora.starter.web.resp.BusinessCode;
import com.chen.pandora.starter.web.resp.ReturnCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
 * author:liucheng
 * Date:2018/8/23
 * Time:下午7:07
 */
public class AppException extends RuntimeException {

    @Getter
    @Setter
    private ReturnCode code = BusinessCode.未知业务失败;

    public AppException(String message) {
        super(message);
        simplifyStackTrace();
    }

    public AppException(String message, ReturnCode code) {
        super(message);
        this.code = code;
        simplifyStackTrace();
    }


    public AppException(String message, Throwable cause) {
        super(message, cause);
        simplifyStackTrace();
    }

    public AppException(String message, ReturnCode code, Throwable cause) {
        super(message, cause);
        this.code = code;
        simplifyStackTrace();
    }

    /**
     * 对于已知报错，去除冗长的trace，清洁日志
     */
    private void simplifyStackTrace() {
        setStackTrace(Arrays.stream(getStackTrace())
                .filter(s -> s.getClassName().startsWith("com.ke") || s.getClassName().startsWith("com.lianjia") || s.getClassName().startsWith("com.dooioo"))
                .toArray(StackTraceElement[]::new));
    }
}
