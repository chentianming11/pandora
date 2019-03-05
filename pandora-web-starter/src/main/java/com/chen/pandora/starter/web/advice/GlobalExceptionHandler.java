package com.chen.pandora.starter.web.advice;

import com.chen.pandora.starter.web.exception.AppException;
import com.chen.pandora.starter.web.resp.BusinessCode;
import com.chen.pandora.starter.web.resp.Result;
import lombok.extern.slf4j.Slf4j;
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
    public Result handleAppException(AppException ex) {
        log.info(ex.getMessage(), ex);
        return Result.fail(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.info(ex.getMessage());
        return Result.fail(BusinessCode.缺少参数 ,ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public Result handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        log.info(ex.getMessage());
        return Result.fail(BusinessCode.缺少参数, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.info(ex.getMessage());
        return Result.fail( BusinessCode.参数类型不匹配 , ex.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    public Result handleFileUploadException(MultipartException ex){
        log.info(ex.getMessage());
        return Result.fail(BusinessCode.文件上传错误,ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
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
        return Result.fail(BusinessCode.未知系统错误, ex.getMessage());
    }
}
