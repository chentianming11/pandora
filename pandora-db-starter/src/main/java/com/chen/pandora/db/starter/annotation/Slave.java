package com.chen.pandora.db.starter.annotation;

import java.lang.annotation.*;

/**
 * @author 陈添明
 * @date 2019/2/18
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Slave {

}
