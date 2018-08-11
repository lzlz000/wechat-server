package site.lzlz.mainbody.utils;

import org.junit.Test;

import java.net.URISyntaxException;

public class AccessTokenUtilTest {

    @Test
    public void getAccessToken() throws URISyntaxException {
        System.out.println(AccessTokenUtil.getAccessToken());
    }
}