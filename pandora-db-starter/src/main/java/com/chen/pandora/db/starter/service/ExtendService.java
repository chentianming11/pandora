package com.chen.pandora.db.starter.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.pandora.db.starter.mapper.ExtendMapper;

/**
 * ExtendService，用于扩展或者重写mybatis-plus默认实现
 * @author 陈添明
 * @date 2019/1/19
 */
public abstract class ExtendService<M extends ExtendMapper<T>,T> extends ServiceImpl<M, T> {

}
