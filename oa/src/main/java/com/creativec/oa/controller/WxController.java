package com.creativec.oa.controller;

import com.creativec.base.IgnoreAuth;
import com.creativec.oa.service.WxUserService;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxConsts;
import com.soecode.wxtools.api.WxMessageRouter;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.exception.WxErrorException;
import com.soecode.wxtools.util.xml.XStreamTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/wx")
public class WxController {

    private IService iService = new WxService();
    @Autowired private WxUserService wxUserService;

    @IgnoreAuth
    @ResponseBody
    @GetMapping
    public String check(String signature, String timestamp, String nonce, String echostr) throws WxErrorException {
        if (iService.checkSignature(signature, timestamp, nonce, echostr)) {
            System.out.println("check成功");
            return echostr;
        }
        System.out.println("check失败");
        return iService.getAccessToken();
    }

    @IgnoreAuth
    @ResponseBody
    @PostMapping
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 创建一个路由器
        WxMessageRouter router = new WxMessageRouter(iService);
        try (PrintWriter out = response.getWriter()) {
            // 微信服务器推送过来的是XML格式。
            WxXmlMessage wx = XStreamTransformer.fromXml(WxXmlMessage.class, request.getInputStream());
            router.rule().event(WxConsts.EVT_SUBSCRIBE).handler(wxUserService).end();
            router.rule().event(WxConsts.EVT_UNSUBSCRIBE).handler(wxUserService).end();
            System.out.println("消息：\n " + wx.getContent());
            out.println(router.route(wx));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
