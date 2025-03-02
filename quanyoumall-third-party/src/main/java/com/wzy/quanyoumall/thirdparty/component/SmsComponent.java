package com.wzy.quanyoumall.thirdparty.component;

import com.wzy.quanyoumall.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.alicloud.sms")
@Component
public class SmsComponent {

    private String host;
    private String path;
    private String appcode;
    private String templateID;

    public void sendCode(String phone, String code){
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("mobile", phone);
        bodys.put("templateID", templateID);
        bodys.put("templateParamSet", code);
        bodys.put("callbackUrl", "");
        bodys.put("channel", "0");
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
