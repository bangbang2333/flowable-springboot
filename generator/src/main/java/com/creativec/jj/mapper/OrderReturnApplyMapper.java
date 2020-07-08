package com.creativec.jj.mapper;

import com.creativec.jj.entity.OrderReturnApply;
import com.creativec.common.base.BaseMyBatisMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import com.creativec.common.config.MybatisRedisCache;

/**
 * <p>
 * 订单退货申请 Mapper 接口
 * </p>
 *
 * @author zsx
 * @since 2020-07-02
 */
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface OrderReturnApplyMapper extends BaseMyBatisMapper<OrderReturnApply> {

}
