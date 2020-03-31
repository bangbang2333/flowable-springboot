package com.creativec.example.mapper;

import com.creativec.common.base.BaseMyBatisMapper;
import com.creativec.example.entity.HandleFlow;
import org.apache.ibatis.annotations.CacheNamespace;

@CacheNamespace
public interface HandleFlowMapper extends BaseMyBatisMapper<HandleFlow> {

}
