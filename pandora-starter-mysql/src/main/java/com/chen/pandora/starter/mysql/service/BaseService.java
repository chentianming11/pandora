package com.chen.pandora.starter.mysql.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.pandora.starter.mysql.mapper.MysqlMapper;
import lombok.extern.log4j.Log4j2;

/**
 * 基础service，用于扩展或者重写mybatis-plus默认实现
 * @author 陈添明
 * @date 2019/1/19
 */
@Log4j2
public abstract class BaseService<M extends MysqlMapper<T>,T> extends ServiceImpl<M, T> {

}
