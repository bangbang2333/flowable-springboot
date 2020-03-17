package com.creativec.common.base;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * <h1>只读事务</h1>
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Transactional(rollbackFor = Exception.class, readOnly = true)
public @interface TranRead {

}