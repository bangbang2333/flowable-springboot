package com.creativec.example.controller;

import com.creativec.common.base.GlobalResponse;
import com.creativec.example.service.HandleFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author ZSX
 */
@RestController
@RequestMapping("/handle")
@Api(tags = "流程处理接口")
public class HandleFlowController {

    @Autowired HandleFlowService handleFlowService;

    @ApiOperation(notes = "handleFlow", value = "处理流程")
    @PostMapping("/handleFlow")
    public GlobalResponse handleFlow(@RequestParam @ApiParam("流程处理id")String kid,
                                     @RequestParam @ApiParam("结果:1通过,2不通过") Integer result,
                                     @RequestParam @ApiParam("意见描述") String description) {
        return handleFlowService.handleFlow(kid, result, description);
    }

    @ApiOperation(notes = "getHandleFlow", value = "查询流程")
    @PostMapping("/getHandleFlow")
    public List<Map> getHandleFlow(@RequestParam @ApiParam("应聘者id") String interviewerId) {
        return handleFlowService.getHandleFlow(interviewerId);
    }
}
