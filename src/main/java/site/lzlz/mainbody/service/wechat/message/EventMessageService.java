package site.lzlz.mainbody.service.wechat.message;

import org.springframework.stereotype.Service;
import site.lzlz.mainbody.entity.wechat.TextMessage;

import java.util.Map;

@Service
public class EventMessageService implements WechatMessageService{
    @Override
    public TextMessage handlerMessage(Map<String, String> requestMap) {
        TextMessage message = new TextMessage();
        message.setContent("欢迎关注 lzlz.site 回复 “help” 或 “帮助” 获取更多信息");
        return message;
    }
}
