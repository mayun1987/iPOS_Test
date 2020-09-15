package com.qianbao.ipos.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AuthPayControllerDemo
 * @Description TODO(用一句话描述该文件做什么)
 * @Author lufl
 * @Date 2019/4/18  10:50
 * @Version V1.0
 */
public class AuthPayControllerDemo extends DemoParams {
    public static final String URL               = "http://ipos.gateway.qianbao.com";
    public static final String AUTH_APPLY_URL    = URL + "/nocardpay/authPay/apply";    //认证申请
    public static final String AUTH_CONFIRM_URL  = URL + "/nocardpay/authPay/confirm";  //确认支付
    public static final String PAYMENT_TRADE_URL = URL + "/nocardpay/paymentTrade";     //代付交易接口
//    public static final String AUTH_APPLY_URL    = "http://172.28.38.67:8083/nocardpay/authPay/apply";    //认证申请
//    public static final String AUTH_CONFIRM_URL  = "http://172.28.38.67:8083/nocardpay/authPay/confirm";  //确认支付
//    public static final String PAYMENT_TRADE_URL = "http://172.28.38.67:8083/nocardpay/paymentTrade";     //代付交易接口

    public static final String outTradeNo   = "201907040000000001";  //【必填】请求交易号，唯一交易标识
    public static final long   tradeAmount  = 301L;                  //【必填】交易金额单位：分
    public static final String tradeCurrency= "CNY";                //【必填】交易币种 CNY：人民币
    public static final String bankCode     = "CITIC";                 //【必填】银行编码  建行CCB 工商ICBC 农行ABC
    public static final int    cardType     = 1;                     //【必填】银行卡类型 1：借记卡，2：贷记卡
    public static final String cardNo       = "6226898009666480";   //【必填】银行卡号 6217000010043148728 620200000000000018 6228480010891840117
    public static final String cardExp      = "2313";                //卡有效期 格式：YYMM 贷记卡必填
    public static final String cardCvv      = "123";                 //卡CVN2             贷记卡必填
    public static final String holderName   = "筱静";                //【必填】持卡人姓名
    public static final String holderIdType = "IDCARD";              //【必填】持卡人证件类型
    public static final String holderIdNo   = "110100198708010088";  //【必填】持卡人证件号
    public static final String holderMobile = "18801455883";         //【必填】持卡人手机号
    public static final String riskInfo     = "{\"location\":\"35.658651,139.745415\"}"; //风控信息 纬度和经度之间以英文逗号分隔，格式：lat<纬度>,lng<经度>

    public static final String requestIp     = "127.0.0.1";              //【必填】用户请求IP
    public static final String tradeSubject  = "订单摘要";                //【必填】订单摘要
    public static final String notifyAddress = "http://www.baidu.com"; //后台异步通知地址,用户支付成功后后台异步通知，如果不上送不会进行异步通知"http://test.com/notify"
    public static final String verifyCode    = "209572";                 //【必填】认证申请时发送的短信验证码

    public static final String outOrderNo   = "201905100000000012";    //【必填】外部订单号,代理商带进来的订单号  与认证、确认支付的不一致out_order_no
    public static final String orderNo      = "201905100000000253";    //【必填】订单编号                  这个是业务自己生成的订单号order_no
    public static final long   txAmt        = 88L;                    //【必填】提现金额

    public static void authApply(String url) {
        Map<String, Object> paramsMap = new HashMap<>(16);

        paramsMap.put("requestTime", new Date().getTime());    //【必填】请求时间戳
        paramsMap.put("outTradeNo", outTradeNo);               //【必填】请求交易号，唯一交易标识
        paramsMap.put("tradeAmount", tradeAmount);             //【必填】交易金额单位：分
        paramsMap.put("tradeCurrency", tradeCurrency);         //【必填】交易币种，CNY：人民币
//        paramsMap.put("riskInfo", riskInfo);                 //风控信息 纬度和经度之间以英文逗号分隔，格式：lat<纬度>,lng<经度>
        paramsMap.put("bankCode", bankCode);                   //【必填】银行编码  工商ICBC
        paramsMap.put("cardType", cardType);                   //【必填】银行卡类型 1：借记卡，2：贷记卡
        paramsMap.put("cardNo", cardNo);                       //【必填】银行卡号6217000010043148728
        paramsMap.put("cardExp", cardExp);                      //卡有效期 格式：YYMM 贷记卡必填
        paramsMap.put("cardCvv", cardCvv);                       //卡CVN2             贷记卡必填
        paramsMap.put("holderName", holderName);               //【必填】持卡人姓名
        paramsMap.put("holderIdType", holderIdType);           //【必填】持卡人证件类型
        paramsMap.put("holderIdNo", holderIdNo);               //【必填】持卡人证件号
        paramsMap.put("holderMobile", holderMobile);           //【必填】持卡人手机号
        dealPayment(url, paramsMap);
    }

    public static void authConfrim(String url) {
        Map<String, Object> paramsMap = new HashMap<>(16);

        paramsMap.put("requestTime", new Date().getTime());  //【必填】请求时间
        paramsMap.put("requestIp", requestIp);               //【必填】用户请求IP
        paramsMap.put("outTradeNo", outTradeNo);             //【必填】请求交易号，注意此处必须与签约申请请求号相同
        paramsMap.put("tradeAmount", tradeAmount);           //【必填】交易金额，单位：分
        paramsMap.put("tradeCurrency", tradeCurrency);       //【必填】交易币种，CNY：人民币
        paramsMap.put("tradeSubject", tradeSubject);         //【必填】订单摘要
//        paramsMap.put("notifyAddress", notifyAddress);       //异步通知地址，用户支付成功后后台异步通知，如果不上送不会进行异步通知
//        paramsMap.put("riskInfo", riskInfo);                 //风控信息
        paramsMap.put("verifyCode", verifyCode);             //【必填】验证码
        paramsMap.put("bankCode", bankCode);                 //【必填】银行编码
        paramsMap.put("cardType", cardType);                 //【必填】银行卡类型 1：借记卡，2：贷记卡
        paramsMap.put("cardNo", cardNo);                     //【必填】卡号
//        paramsMap.put("cardExp", cardExp);                   //卡有效期
//        paramsMap.put("cardCvv", cardCvv);                   //卡CVN2
        paramsMap.put("holderName", holderName);             //【必填】持卡人姓名
        paramsMap.put("holderIdType", holderIdType);         //【必填】持卡人证件类型
        paramsMap.put("holderIdNo", holderIdNo);             //【必填】持卡人证件号
        paramsMap.put("holderMobile", holderMobile);         //【必填】持卡人手机号

        dealPayment(url, paramsMap);
    }

    public static void paymentTrade(String url) {
        Map<String, Object> paramsMap = new HashMap<>(16);

        paramsMap.put("requestIp", requestIp);               //【必填】用户请求IP
        paramsMap.put("outOrderNo", outOrderNo);             //【必填】外部订单号
        paramsMap.put("orderNo", orderNo);                   //【必填】订单编号
        paramsMap.put("holderName", holderName);             //【必填】持卡人姓名
        paramsMap.put("holderIdNo", holderIdNo);             //【必填】持卡人证件号
        paramsMap.put("holderMobile", holderMobile);         //【必填】持卡人手机号
        paramsMap.put("cardNo", cardNo);                     //【必填】卡号
        paramsMap.put("txAmt", txAmt);                       //【必填】提现金额
        paramsMap.put("bankCode", bankCode);                 //【必填】银行编码
        paramsMap.put("tradeSubject", tradeSubject);         //【必填】订单信息摘要

//        paramsMap.put("tradeAmount", tradeAmount);           //【必填】交易金额，单位：分
//        paramsMap.put("tradeCurrency", tradeCurrency);       //【必填】交易币种，CNY：人民币
//        paramsMap.put("notifyAddress", notifyAddress);       //异步通知地址，用户支付成功后后台异步通知，如果不上送不会进行异步通知
//        paramsMap.put("riskInfo", riskInfo);                 //风控信息
//        paramsMap.put("cardType", cardType);                 //【必填】银行卡类型 1：借记卡，2：贷记卡
//        paramsMap.put("cardExp", cardExp);                   //卡有效期
//        paramsMap.put("cardCvv", cardCvv);                   //卡CVN2


        dealPayment(url, paramsMap);
    }

    private static void dealPayment(String url, Map<String, Object> paramsMap) {
        try {
            System.out.println("请求报文 :" + JSON.toJSONString(paramsMap));
            Map<String, String> secureMap = SecureUtil.reqEncrypt(agentCode, paramsMap, privateKey, publicKey);
//            System.out.println("secureMap_toString :" + JSON.toJSONString(secureMap));
//            System.out.println("secureMap :" + secureMap);
            String respContent = HTTPClient.post(url, secureMap);
//            System.out.println("respContent :"+respContent);
            if (respContent != null && !"".equals(respContent.trim())) {
                Map<String, String> respMap = JSON.parseObject(respContent, new TypeReference<Map<String, String>>() {
                });
                Map<String, String> payResultMap = SecureUtil.reqDecrypt(respMap, privateKey, publicKey);
                System.out.println();
                System.out.println("响应报文 :" + payResultMap);
                String status = payResultMap.get("status");
                String message = payResultMap.get("message");
                if ("20000161".equals(status)) {
                    System.out.println("---交易成功---");
                    //TODO 业务逻辑处理
                } else {
                    System.out.println("---交易失败---");
                    //TODO 业务逻辑处理
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("AUTH_APPLY_URL:"+AUTH_APPLY_URL);
        authApply(AUTH_APPLY_URL);

//        System.out.println("AUTH_CONFIRM_URL"+AUTH_CONFIRM_URL);    //执行前需要重新填入收到的验证码
//        authConfrim(AUTH_CONFIRM_URL);

//        System.out.println("PAYMENT_TRADE_URL:"+PAYMENT_TRADE_URL);
//        paymentTrade(PAYMENT_TRADE_URL);

//        System.out.println(new Date().getTime());
    }

}

