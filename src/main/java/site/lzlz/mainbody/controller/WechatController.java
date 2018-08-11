package site.lzlz.mainbody.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.lzlz.mainbody.service.wechat.MessageEntryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@RestController
@RequestMapping("wx")
@Slf4j
public class WechatController {
    // 与接口配置信息中的Token要一致
    private static final String token = "lzlz0810";

    private final MessageEntryService wechatService;

    @Autowired
    public WechatController(MessageEntryService wechatService) {
        this.wechatService = wechatService;
    }

    @GetMapping
    public String wxToken(@RequestParam("signature")String signature,
                          @RequestParam("timestamp")String timestamp,
                          @RequestParam("nonce")String nonce,
                          @RequestParam("echostr")String echostr){
        if (TokenChecker.checkSignature(signature,timestamp,nonce)) {
            return echostr;
        }
        else return null;
    }

    @PostMapping
    public void message(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");
        String respMessage = wechatService.processRequest(request);
        // 响应消息
        try (PrintWriter out = response.getWriter()) {
            out.print(respMessage);
            out.flush();
        } catch (IOException e) {
            log.error("发送微信消息出现错误\n{}",Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        }
    }
    @GetMapping("test")
    public String wxToken(){
        log.debug("hello");
        log.info("hello");
        log.warn("hello");
        log.error("hello");
        return "hello!";
    }

    private static class TokenChecker{
        /**
         * 验证签名
         */
        private static boolean checkSignature(String signature, String timestamp, String nonce) {
            String[] arr = new String[] { token, timestamp, nonce };
            // 将token、timestamp、nonce三个参数进行字典序排序
            Arrays.sort(arr);
            StringBuilder content = new StringBuilder();
            for (String anArr : arr) {
                content.append(anArr);
            }
            MessageDigest md;
            String tmpStr = null;

            try {
                md = MessageDigest.getInstance("SHA-1");
                // 将三个参数字符串拼接成一个字符串进行sha1加密
                byte[] digest = md.digest(content.toString().getBytes());
                tmpStr = byteToStr(digest);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
            return tmpStr != null && tmpStr.equals(signature.toUpperCase());
        }

        /**
         * 将字节数组转换为十六进制字符串
         */
        private static String byteToStr(byte[] byteArray) {
            StringBuilder strDigest = new StringBuilder();
            for (byte aByteArray : byteArray) {
                strDigest.append(byteToHexStr(aByteArray));
            }
            return strDigest.toString();
        }

        /**
         * 将字节转换为十六进制字符串
         */
        private static String byteToHexStr(byte mByte) {
            char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
            char[] tempArr = new char[2];
            tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
            tempArr[1] = Digit[mByte & 0X0F];
            return new String(tempArr);
        }
    }
}
