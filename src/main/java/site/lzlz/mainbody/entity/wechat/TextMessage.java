package site.lzlz.mainbody.entity.wechat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文本消息 
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class TextMessage extends WxMessage {
    /**
     * 回复的消息内容
     */
    private String Content;

}