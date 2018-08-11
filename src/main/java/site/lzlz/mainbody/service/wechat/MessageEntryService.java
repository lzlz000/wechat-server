package site.lzlz.mainbody.service.wechat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.lzlz.mainbody.entity.wechat.TextMessage;
import site.lzlz.mainbody.service.wechat.message.EventMessageService;
import site.lzlz.mainbody.service.wechat.message.TextMessageService;
import site.lzlz.mainbody.utils.WechatMessageUtil;
import site.lzlz.mainbody.constants.WechatMsgType;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 微信公众号服务入口
 */
@Service
@Slf4j
public class MessageEntryService {
    @Autowired
    EventMessageService eventMessageService;
    @Autowired
    TextMessageService textMessageService;

    public String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // xml请求解析
            Map<String, String> requestMap = WechatMessageUtil.parseXml(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            log.info("收到消息，类型 {}",msgType);
            TextMessage resultMessage;
            switch (msgType) {
                case WechatMsgType.Text:
                    resultMessage = textMessageService.handlerMessage(requestMap);
                    break;
                case WechatMsgType.Event:
                    resultMessage = eventMessageService.handlerMessage(requestMap);
                    break;
                case WechatMsgType.Link: default:
                    resultMessage = new TextMessage();
                    resultMessage.setContent("暂不支持此消息");
            }
            resultMessage.setToUserName(fromUserName);
            resultMessage.setFromUserName(toUserName);
            resultMessage.setCreateTime(new Date().getTime());
            resultMessage.setMsgType(WechatMsgType.Text);
            resultMessage.setFuncFlag(0);
            respMessage = WechatMessageUtil.textMessageToXml(resultMessage);
            log.info("返回消息\n{}",respMessage);

        } catch (Exception e) {
            log.error("xml解析失败 {}",e.getMessage());
            e.printStackTrace();
        }
        return respMessage;
    }
}
