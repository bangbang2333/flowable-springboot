package com.creativec.jj.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单退货申请前端控制器
 * </p>
 *
 * @author zsx
 * @since 2020-07-02
 */
@RestController
@RequestMapping("/order-return-apply")
@Api(tags = "订单退货申请接口")
public class OrderReturnApplyController {

    @Autowired
    private OrderReturnApplyServiceImpl orderReturnApplyServiceImpl;

}
