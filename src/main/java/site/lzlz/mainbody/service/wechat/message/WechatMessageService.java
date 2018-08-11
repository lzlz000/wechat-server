package site.lzlz.mainbody.service.wechat.message;

import site.lzlz.mainbody.entity.wechat.TextMessage;

import java.util.Map;

public interface WechatMessageService {
    TextMessage handlerMessage(Map<String,String> requestMap);
}
