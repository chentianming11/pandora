package com.chen.pandora.starter.web.resp;

/**
 * @author 陈添明
 * @date 2019/3/5
 */
public enum BusinessCode implements ReturnCode {


    成功(0, "success"),
    未知系统错误(10000, "未知系统错误"),
    未知业务失败(20000, "未知业务失败"),
    未登录(20001, "未登录"),
    无权限(20002, "无权限"),
    数据过期(20003, "数据过期"),
    参数错误(20004, "参数错误"),
    缺少参数(20005, "缺少参数"),
    参数类型不匹配(20006, "参数类型不匹配"),
    文件上传错误(20007, "文件上传错误");

    /**
     * 业务编号
     */
    private int code;

    /**
     * 业务值
     */
    private String message;

    BusinessCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取返回编码
     *
     * @return code
     */
    @Override
    public int code() {
        return code;
    }

    /**
     * 获取返回描述信息
     *
     * @return message
     */
    @Override
    public String message() {
        return message;
    }
}
