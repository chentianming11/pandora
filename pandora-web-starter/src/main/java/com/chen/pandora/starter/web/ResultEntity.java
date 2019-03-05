package com.chen.pandora.starter.web;

import lombok.Builder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

/**
 * @author 陈添明
 * @date 2019/3/5
 */
public class ResultEntity {

    public static final String OK = "ok";

    public static  HttpEntity ok(Object body) {
        return buildHttpEntity(BusinessCodeEnum.正常.code, OK, body);
    }

    public static  HttpEntity ok() {
        return buildHttpEntity(BusinessCodeEnum.正常.code, OK, null);
    }

    public static  HttpEntity fail(int code, String msg) {
        return buildHttpEntity(code, msg, null);
    }

    public static  HttpEntity fail(String msg) {
        return fail(BusinessCodeEnum.未知业务失败.code, msg);
    }

    private static HttpEntity buildHttpEntity(int code, String msg, Object body) {
        Result result = Result.builder()
                .code(code)
                .msg(msg)
                .body(body)
                .build();
        return  ResponseEntity.ok(result);
    }

    @Builder
    private class Result {
        private Object body;
        private int code;
        private String msg;
    }
}
