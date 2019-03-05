package com.chen.pandora.starter.web;

/**
 * 业务code
 * @author 陈添明
 * @date 2019/3/5
 */
public enum  BusinessCodeEnum {

    正常(0),
    未知系统错误(10000),
    未知业务失败(20000),
    未登录(20001),
    无权限(20002),
    数据过期(20003),
    参数错误(20004);
    public int code;

    BusinessCodeEnum(int code) {
        this.code = code;
    }
}
