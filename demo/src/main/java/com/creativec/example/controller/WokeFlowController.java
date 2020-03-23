package com.creativec.example.controller;

import com.creativec.common.base.PageResult;
import com.creativec.example.entity.WokeFlow;
import com.creativec.example.service.WokeFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author llpei
 * @Description:
 * @date Created in 19:51 2020/3/22
 */
@RestController
@RequestMapping("/wokeFlow")
@Api(tags = "流程表接口")
public class WokeFlowController {

    @Autowired
    private WokeFlowService wokeFlowService;

    @ApiOperation(notes = "getWokeFlow",value = "查询流程步")
    @GetMapping("/getWokeFlow")
    public PageResult getWokeFlow(WokeFlow wokeFlow){
        return wokeFlowService.getWokeFlowByPage(wokeFlow);
    }

    @ApiOperation(notes = "update",value = "查询流程步")
    @PostMapping("/update")
    public Boolean update(WokeFlow wokeFlow){
        return wokeFlowService.updateWokeFlow(wokeFlow);
    }
}
