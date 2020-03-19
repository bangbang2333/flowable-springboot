package com.creativec.example.service;

import com.creativec.common.base.BaseService;
import com.creativec.example.entity.HandleFlow;
import com.creativec.example.mapper.HandleFlowMapper;
import org.springframework.stereotype.Service;

/**
 * @author ZSX
 */
@Service
public class HandleFlowService extends BaseService<HandleFlowMapper, HandleFlow> {

    public boolean handleFlow(HandleFlow handleFlow) {
        return true;
    }

}
