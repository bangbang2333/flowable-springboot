package com.creativec.example.controller;

import com.creativec.example.entity.HandleFlow;
import com.creativec.example.service.HandleFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZSX
 */
@RestController
@RequestMapping("/handle")
public class HandleFlowController {

    @Autowired HandleFlowService handleFlowService;

    @PostMapping("/handleFlow")
    public boolean handleFlow(HandleFlow handleFlow) {
        return handleFlowService.handleFlow(handleFlow);
    }
}
