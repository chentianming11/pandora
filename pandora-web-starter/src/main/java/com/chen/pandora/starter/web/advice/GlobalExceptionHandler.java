package com.chen.pandora.starter.web.advice;

import com.chen.pandora.starter.web.BusinessCodeEnum;
import com.chen.pandora.starter.web.ResultEntity;
import com.chen.pandora.starter.web.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * 统一异常处理
 * @author 陈添明
 * @date 2019/3/5
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public HttpEntity handleAppException(AppException ex) {
        log.info(ex.getMessage(), ex);
        return ResultEntity.fail(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public HttpEntity handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.info(ex.getMessage());
        return ResultEntity.fail( "缺少参数: " + ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public HttpEntity handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        log.info(ex.getMessage());
        return ResultEntity.fail( "缺少参数: " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public HttpEntity handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.info(ex.getMessage());
        return ResultEntity.fail( "参数类型不匹配: " + ex.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    public HttpEntity handleFileUploadException(MultipartException ex){
        log.info(ex.getMessage());
        return ResultEntity.fail( "文件上传错误: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public HttpEntity handleException(Exception ex) {
        Throwable t = ex;
        while (true) {
            if (t instanceof AppException) {
                return handleAppException((AppException) t);
            }
            if ((t = t.getCause()) == null) {
                break;
            }
        }
        log.error(ex.getMessage(), ex);
        return ResultEntity.fail(BusinessCodeEnum.未知系统错误.code, ex.getMessage());
    }
}
