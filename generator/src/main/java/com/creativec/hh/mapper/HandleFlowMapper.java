package com.creativec.hh.mapper;

import com.creativec.hh.entity.HandleFlow;
import com.creativec.common.base.BaseMyBatisMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import com.xuemei.common.mapper.MybatisRedisCache;

/**
 * <p>
 * 流程处理表 Mapper 接口
 * </p>
 *
 * @author zsx
 * @since 2020-03-31
 */
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface HandleFlowMapper extends BaseMyBatisMapper<HandleFlow> {

}
