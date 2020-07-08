package com.creativec.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creativec.entity.WxUser;
import com.creativec.mapper.WxUserMapper;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxConsts;
import com.soecode.wxtools.api.WxMessageHandler;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class WxUserService extends ServiceImpl<WxUserMapper, WxUser> implements WxMessageHandler {
    public void saveUser(String openId) {
        WxUser user = getByOpenId(openId);
        if (user == null) {
            WxUser wxUser = new WxUser();
            wxUser.setOpenId(openId);
            save(wxUser);
        } else {
            update(new UpdateWrapper<WxUser>().eq("id", user.getId()).set("subscribe", 1));
        }
    }

    public void userQuit(String openId) {
        update(new UpdateWrapper<WxUser>().eq("open_id", openId).set("subscribe", 0));
    }

    public WxUser getByOpenId(String openId) {
        return getOne(new QueryWrapper<WxUser>().eq("open_id", openId).last("limit 1"));
    }

    @Override
    public WxXmlOutMessage handle(WxXmlMessage message, Map<String, Object> context, IService iService) {
        switch (message.getEvent()) {
            case WxConsts.EVT_SUBSCRIBE:
                log.error("==========EVT_SUBSCRIBE=============");
                saveUser(message.getFromUserName());
                return WxXmlOutMessage.TEXT().content("hello zsx").toUser(message.getFromUserName()).fromUser(message.getToUserName()).build();
            case WxConsts.EVT_UNSUBSCRIBE:
                log.error("==========EVT_UNSUBSCRIBE==========");
                userQuit(message.getFromUserName());
                break;
        }
        return null;
    }
}
