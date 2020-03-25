package com.creativec.example.controller;

import com.creativec.common.base.PageResult;
import com.creativec.example.entity.WokeFlow;
import com.creativec.example.service.WokeFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation(notes = "update",value = "修改流程步")
    @PostMapping("/update")
    public Boolean update(WokeFlow wokeFlow){
        return wokeFlowService.updateWokeFlow(wokeFlow);
    }

    @ApiOperation(notes = "getWokeFlowByWokesId",value = "根据招聘信息查询流程配置列表")
    @PostMapping("/getWokeFlowByWokesId")
    public List<WokeFlow> getWokeFlowByWokesId(@RequestParam @ApiParam("招聘信息id") String wokesId){
        return wokeFlowService.queryWokeFlowListByWokesKid(wokesId);
    }

    @ApiOperation(notes = "addWokeFlow",value = "添加流程配置")
    @PostMapping("/addWokeFlow")
    public String addWokeFlow(WokeFlow wokeFlow){
        return wokeFlowService.addWokeFlow(wokeFlow);
    }
}
