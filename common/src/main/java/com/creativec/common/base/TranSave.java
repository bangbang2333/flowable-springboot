package com.creativec.common.base;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * <h1>修改事务</h1>
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Transactional(rollbackFor = Exception.class)
public @interface TranSave {

}