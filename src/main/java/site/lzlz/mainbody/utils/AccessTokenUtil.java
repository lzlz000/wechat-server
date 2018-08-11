package site.lzlz.mainbody.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@Component
@Slf4j
public class AccessTokenUtil {
    private static final String accessTokenUrl =
            "https://api.weixin.qq.com/cgi-bin/token" +
                    "?grant_type=client_credential" +
                    "&appid=wxdbc4c6ec8c733a1a" +
                    "&secret=de4ed64055bbd10c8aef0564c41b5599";
    private static RestTemplate restTemplate = new RestTemplate();
    private static volatile AccessToken accessToken;

    @Scheduled(cron="30 30 0/1 * * ?") //每小时 30分 30秒 执行一次更新token
    public synchronized static void updateAccessToken(){
        AccessToken accessToken;
        try {
            if(AccessTokenUtil.accessToken==null){
                accessToken = restTemplate.getForObject(new URI(accessTokenUrl),AccessToken.class);
                accessToken.timeStamp = new Date().getTime();
                AccessTokenUtil.accessToken = accessToken;
                log.info("更新微信accessToken: {}",AccessTokenUtil.accessToken.access_token);
            }
        } catch (URISyntaxException e) {
            log.error("微信accessToken获取失败 url语法错误{}",accessTokenUrl);
            e.printStackTrace();
        }
    }
    public static String getAccessToken(){
        while (accessToken == null) {
            updateAccessToken();
        }
        return accessToken.getAccess_token();
    }


    @Data
    private static class AccessToken {
        private String access_token;
        private int expires_in;
        private long timeStamp;
    }
}
