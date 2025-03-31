package com.wzy.quanyoumall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.wzy.quanyoumall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private String app_id = "2021000147657504";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCr3kZ3HygpweT+RUeZsiVLtXvT3c9bz+utez2vI6zGHRMPLCoItq0QKdnD/cEj7ZwBAdc7lzjAbvHmzA7LmQAzBcBClBlhJDGueEjm/wxuENFPOyfitvS4SYHmlek0Eo+rY+ZRebMzN4rwNhkUs4B5BahEhX/PIG33+jlpPM8mxQ5UDIh6paE92yPzYuMbQnZ7oe9ADTjlj6cfdD5Jaq8OIKmWRu5EHx70Tqu6QXeybpeLyJemFX2n3H2SxDQo+ygME8yrBJEeV5AjbPZkI/5OB/hR35CR1B37+pYAt8CoI8fvKsQUHLzTXcYFwMYhY8Vw0wFTScLQTDH96dqcH+gNAgMBAAECggEAJxUhov8DzBMadrPcZ1f6YTAA25nqCG/MoSOQUF5lcJu19NJPMLIRwsYxjf8LR5dFzXjUr+lgb2oLOjeW+bduMywQn1M8PjnTzl/t/BFtiy3p2wGRvV1rYujcUEowwLWj1A6gllB9bp0nzB4RMALtqRefPw24lapDN4vAOG0qkh8IBIUq97xh3IZbFpPW5RaM7P7pfpsVzxOXb5lc1Kgjixg9O/lqdk61P8QkjycAgAXuSUGASdTDOH3ZuU719baPgPjmbHjyUflfi5ofyR42LdIbQqten8+/QLjrjQC6xmAubVj98nm7rdDK055wWSD1WdzH/JrYgNVXi8JUd3yR5QKBgQDYTxJwSMqDXuxGxcgMPOhK7dJJohIeHGvebzJx024ka/VIDQ7ioVoSVaWboXyQ20MShjS36PIJ4Zdhr6X5OeSu6asBMwiiUZTz0sHki9MafDEML23wJRxx3XNQiJzDIpLPkw33aRkSQ1o0VXEQEaWVyPY/LSzrLA66mn0UEMq2pwKBgQDLZ6SKet9d0z+bJeStqQ8nl/uuqTAgDnZZPGnrI9fP4+DKIK5gHukPvOu9xqH32JaeGRzFc38hBmBHynBnaajFuIkz0S6Kwgvqk8333jmksN7bbKh4XPEhfJujtSZ8bdybb7AcXWBa0GNXWJR9z9G558LkK96+MIz1XkYR+ZQ2KwKBgQDGWbvOuvD5BlIjk7mdfzyDeREb9EIo2mcP0WewePDG9Tg2mcLMjqjJpz/1Nl/DdUxL8ETnX/SSmwr1yNupbi0FN8BXmTSRo72y19B0gsNRxhAz3Eozq9QplafocV1OkqWNMovcE8Opu5k75B2JXqU4dM9Zn1NRU1UuR/vqKnu4gQKBgBT7E9XU5sVezOjL99KR5aZH3wYdHYGw/DQoLyS6BcDD2KQAyvvPNGsTKZ3hp0NRa/fSz0zupOZbKgM9tmp0U5xRdJWLtilJ6sC/jjdLDNcYB2TKyHXh4F175PH19YpQKrk9xASo/Q9g/147HNX9YbyzUz8F52vmxiNpHgEspUURAoGAaOgdXMQ5keWHkWFLutj/lhPFJCUUhr2UDkLbdfapLui2LMaKTCRx+lw0D/ixxkTRPQLTFaNb1kwqWJVYLWTBc3hyJ3gG155wpBOJZrY8QQyZiUO7iAngSI6QwZMdcMACtMwWL1n2KgXLfJKHzy3TAIn4TviDdRk1NRzgGkekihU=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnvwtCx/dvQVyPRjYb5iKcRJmuQx7DnjUmU+haRjQqmNwLmBykRzp1StdTIqHn9ZlIq1YIl+pNQgENosmQjASHIBdVeQ8FRRAMaL/+LQZpU2/0OsEHPNwv/2Bix1gYkQQdv2jbblcefR0ueSCHnJTxz7thUZaDtyJVHn2AiIDUGQBvv7NpzXtQXwjOpu3qdVLFmmTt6jT9QHbYAnpLtqS8OJK3KmOmOj2ZxwwayxF83t8k/f7/p3HOMJa2llYY/8Uf5cxcUr77pSCOdbZFcN4aCfQuTZxwEe+h1ZZa3aojssdByjTcOiJ4KNyAlChWJ4g+r1gcoNVvFRtiGBEICQgwQIDAQAB";

    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private String notify_url = "http://8.152.219.128/order/listener/payed/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private String return_url = "http://8.152.219.128/order/list.html";

    // 签名方式
    private String sign_type = "RSA2";

    // 字符编码格式
    private String charset = "utf-8";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    private String timeout_express = "1m";

    public String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
//        System.out.println("支付宝的响应："+result);
        return result;
    }
}