package com.creativec.common.base;

import java.lang.annotation.*;

/**
 * 方法注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Documented
public @interface IgnoreAuth {

    /**
     * 是否忽略校验
     * @return
     */
    boolean isIngore() default true;

}
