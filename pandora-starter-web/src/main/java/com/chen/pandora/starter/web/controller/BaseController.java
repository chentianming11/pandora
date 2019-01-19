package com.chen.pandora.starter.web.controller;

import com.chen.pandora.starter.web.exception.AppException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:liucheng
 * @since:2018/8/23 下午7:13
 */
@Log4j2
public abstract class BaseController {

    protected final static String CODE = "code";
    protected final static String MSG = "msg";
    protected final static String DATA = "data";
    protected final static String EXCEPTION_MSG = "exceptionMsg";

    // -------------------- 异常捕获 -----------------------//
    @ExceptionHandler(AppException.class)
    public ResponseEntity handleAppException(AppException ex) {
        log.info(ex.getMessage(), ex);
        return fail(ex.getMessage(), ex.getCode()).build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.info(ex.getMessage());
        return fail("缺少参数").put(EXCEPTION_MSG, ex.getMessage()).build();
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        log.info(ex.getMessage());
        return fail("缺少参数").put(EXCEPTION_MSG, ex.getMessage()).build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.info(ex.getMessage());
        return fail("参数类型不匹配").put(EXCEPTION_MSG, ex.getMessage()).build();
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity handleFileUploadException(MultipartException ex){
        log.info(ex.getMessage());
        return fail("文件上传错误").put(EXCEPTION_MSG, ex.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
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
        return fail(ex.getMessage(), AppException.CodeEnum.未知系统错误.code).build();
    }

    //----------------------- 返回定义 ---------------------//

    /**
     * 操作正常返回
     *
     * @return
     */
    public Result ok() {
        return new Result("ok", AppException.CodeEnum.正常.code);
    }

    /**
     * 操作业务异常返回
     *
     * @return
     */
    public Result fail(String msg) {
        return new Result(msg, AppException.CodeEnum.未知业务失败.code);
    }

    public Result fail(String msg, int code) {
        return new Result(msg, code);
    }

    /**
     * Controller返回值，spring会自动转化为json，可以链式操作<br>
     * 1、put：键值对<br>
     * 2、body：直接返回javaBean<br>
     * 2种方式不能同时使用
     */
    public class Result {

        private Map<String, Object> map;
        private Object body;
        @Getter
        private int code;
        @Getter
        private String msg;

        public Result(String msg, int code) {
            this.msg = msg;
            this.code = code;
        }

        /**
         * 键值对
         *
         * @param key
         * @param value
         * @return
         */
        public Result put(String key, Object value) {
            Assert.isTrue(body == null, "不支持同时使用put和body");
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(key, value);
            return this;
        }

        /**
         * 直接返回javaBean
         *
         * @param body
         * @return
         */
        public Result body(Object body) {
            Assert.isTrue(map == null, "不支持同时使用put和body");
            this.body = body;
            return this;
        }

        public ResponseEntity build() {
            ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON_UTF8);
            Map result = new HashMap<String, Object>(3);
            result.put(CODE, this.code);
            result.put(MSG, this.msg);
            result.put(DATA, body != null ? body : map);
            return bodyBuilder.body(result);
        }
    }

}
