package com.creativec.example.controller;

import com.creativec.common.base.GlobalResponse;
import com.creativec.example.service.HandleFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public GlobalResponse handleFlow(@RequestParam String kid,
                                     @RequestParam Integer result,
                                     @RequestParam String description) {
        return handleFlowService.handleFlow(kid, result, description);
    }

    @ApiOperation(notes = "getHandleFlow", value = "查询流程")
    @PostMapping("/getHandleFlow")
    public List<Map> getHandleFlow(@RequestParam String interviewerId) {
        return handleFlowService.getHandleFlow(interviewerId);
    }
}
