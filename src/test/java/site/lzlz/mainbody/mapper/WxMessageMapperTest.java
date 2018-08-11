package site.lzlz.mainbody.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.lzlz.mainbody.entity.dataobject.WxMessageDO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxMessageMapperTest {
    @Autowired
    WxMessageMapper wxMessageMapper;

    @Test
    public void selectMessageByKey() {
        WxMessageDO wxMessage = wxMessageMapper.selectMessageByKey("help");
        System.out.println(wxMessage);
        Assert.assertNotNull(wxMessage);
    }
}