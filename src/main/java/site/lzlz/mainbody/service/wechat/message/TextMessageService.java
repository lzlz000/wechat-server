package site.lzlz.mainbody.service.wechat.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.lzlz.mainbody.entity.dataobject.WxMessageDO;
import site.lzlz.mainbody.entity.wechat.TextMessage;
import site.lzlz.mainbody.mapper.WxMessageMapper;

import java.util.Map;

@Service
@Slf4j
public class TextMessageService implements WechatMessageService{
    private final WxMessageMapper wxMessageMapper;

    @Autowired
    public TextMessageService(WxMessageMapper wxMessageMapper) {
        this.wxMessageMapper = wxMessageMapper;
    }

    @Override
    public TextMessage handlerMessage(Map<String, String> requestMap) {
        String content = requestMap.get("Content");
        TextMessage resultMessage = new TextMessage();
        log.info("收到文字消息 {}",content);
        WxMessageDO msg = wxMessageMapper.selectMessageByKey(content);
        if (msg != null) {
            resultMessage.setContent(msg.getMessage());
        }else{
            resultMessage.setContent("暂不支持此消息，回复 “help” 或 “帮助” 获取更多信息");
        }
        return resultMessage;
    }
}
