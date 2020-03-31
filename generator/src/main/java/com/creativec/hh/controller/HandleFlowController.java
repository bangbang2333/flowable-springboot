package com.creativec.hh.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 流程处理前端控制器
 * </p>
 *
 * @author zsx
 * @since 2020-03-31
 */
@RestController
@RequestMapping("/handle-flow")
@Api(tags = "流程处理接口")
public class HandleFlowController {

    @Autowired
    private HandleFlowServiceImpl handleFlowServiceImpl;

}
