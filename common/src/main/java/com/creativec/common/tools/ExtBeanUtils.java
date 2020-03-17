package com.creativec.common.tools;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.PropertyAccessorFactory;

public class ExtBeanUtils {

    public static void copyIncludeProperties(Object source, Object target, String... includeProperties) {
        BeanWrapper srcWrap = PropertyAccessorFactory.forBeanPropertyAccess(source);
        BeanWrapper trgWrap = PropertyAccessorFactory.forBeanPropertyAccess(target);
        for (String prop : includeProperties) {
            trgWrap.setPropertyValue(prop, srcWrap.getPropertyValue(prop));
        }
        //默认始终进行version属性复制，以确保乐观锁控制逻辑
        try {
            trgWrap.setPropertyValue("version", srcWrap.getPropertyValue("version"));
        } catch (InvalidPropertyException ipe) {
            //如果没有version属性则忽略
        }
    }

    /**
     * 基于源对象类型新创建实例，并把指定属性列表复制到新建对象实例
     * 常用于数据库查询出实体对象后，希望在JSON序列化输出时只保留必要属性列表值
     *
     * @param source 包含完整数据属性的源对象
     * @param includeProperties 指定需要进行拷贝输出的属性集合列表
     * @return 新建的部分属性值对象
     */
    public static Object buildDTOIncludeProperties(Object source, String... includeProperties) {
        try {
            Class sourceClass = source.getClass();
            Object target = sourceClass.getDeclaredConstructor().newInstance();
            BeanWrapper srcWrap = PropertyAccessorFactory.forBeanPropertyAccess(source);
            BeanWrapper trgWrap = PropertyAccessorFactory.forBeanPropertyAccess(target);
            for (String prop : includeProperties) {
                trgWrap.setPropertyValue(prop, srcWrap.getPropertyValue(prop));
            }
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
