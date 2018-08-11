package site.lzlz.mainbody.service.wechat.message;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.lzlz.mainbody.entity.wechat.TextMessage;

import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TextMessageServiceTest {
    @Autowired
    TextMessageService textMessageService;

    @Test
    public void handlerMessage() {
        HashMap<String,String> map = new HashMap<>();
        map.put("Content","help");
        TextMessage message = textMessageService.handlerMessage(map);
        System.out.println(message.getContent());
        Assert.assertNotEquals(message.getContent(),"暂不支持此消息，回复 “help” 或 “帮助” 获取更多信息");
        map.put("Content","asdasdadfasdfa");
        message = textMessageService.handlerMessage(map);
        Assert.assertEquals(message.getContent(),"暂不支持此消息，回复 “help” 或 “帮助” 获取更多信息");
    }
}