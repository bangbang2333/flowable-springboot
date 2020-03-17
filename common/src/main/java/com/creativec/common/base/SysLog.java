package com.creativec.common.base;

import java.lang.annotation.*;


/**
 * @author ZSX
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface SysLog {
    /**
     * 操作类型
     * @return
     */
    String logtype() default "";

    /**
     * 操作名称
     * @return
     */
    String logname() default "";
}
