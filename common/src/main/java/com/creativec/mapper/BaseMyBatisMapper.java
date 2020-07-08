package com.creativec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author ZSX
 */
public interface BaseMyBatisMapper<T> extends BaseMapper<T> {

    /**
     * 附带审计属性自动处理的删除接口，注意传入对象并且包含主键值
     *
     * @param entity
     * @param <DTO>
     * @return
     */
    <T> int deleteByIdWithFill(T entity);
}