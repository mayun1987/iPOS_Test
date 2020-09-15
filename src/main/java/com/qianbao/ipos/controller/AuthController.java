package com.qianbao.ipos.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.qianbao.ipos.bean.PxPay;
import com.qianbao.ipos.demo.DemoParams;
import com.qianbao.ipos.demo.GetReqParamAppend;
import com.qianbao.ipos.demo.HTTPClient;
import com.qianbao.ipos.demo.SecureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.qianbao.ipos.demo.DemoParams.agentCode;

//import java.util.logging.Logger;
//import com.qianbao.ipos.log.AppLogger;

@RestController
public class AuthController {

    //默认结果
    public static String result = "未获取到！";
    //KEY
    private static String URL = "";
    private static String url = "";
    //日志log4j2
    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

//    @RequestMapping("/auth")
//    public ModelAndView auth() {
//        return new ModelAndView("index");
//    }

    String ENVIRONMENT = "";
    String PAYTYPE = "";


    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String Auth_apply_confirm(@ModelAttribute PxPay pxPay) {
        ENVIRONMENT = pxPay.getENVIRONMENT();
        PAYTYPE = pxPay.getPAYTYPE();

        String outTradeNo = pxPay.getOutTradeNo();
        Long tradeAmount = pxPay.getTradeAmount();
        String tradeCurrency = pxPay.getTradeCurrency();
        String bankCode = pxPay.getBankCode();
        Integer cardType = pxPay.getCardType();
        String cardNo = pxPay.getCardNo();
        String cardExp = pxPay.getCardExp();
        String cardCvv = pxPay.getCardCvv();
        String holderName = pxPay.getHolderName();
        String holderIdType = pxPay.getHolderIdType();
        String holderIdNo = pxPay.getHolderIdNo();
        String holderMobile = pxPay.getHolderMobile();
        String riskInfo = pxPay.getRiskInfo();
        String requestIp = pxPay.getRequestIp();
        String tradeSubject = pxPay.getTradeSubject();
        String notifyAddress = pxPay.getNotifyAddress();
        String verifyCode = pxPay.getVerifyCode();
        String outOrderNo = pxPay.getOutOrderNo();
        String orderNo = pxPay.getOrderNo();
        Long txAmt = pxPay.getTxAmt();
        String tradeType = pxPay.getTradeType();

        if (ENVIRONMENT.isEmpty()) {
            logger.info("支付环境不能为空！");
            return "支付环境不能为空,请选择支付环境！";
        } else {
            logger.info("ENVIRONMENT！" + ENVIRONMENT);
            switch (ENVIRONMENT) {
                case "测试环境":
                    URL = "http://172.28.38.67:8083";
                    break;
                case "预发布环境":
                    URL = "http://ipos.gateway.qianbao.com";
                    break;
                case "生产环境":
                    URL = "http://ipos.gateway.qianbao.com";
                    break;
                default:
            }
            logger.info("ENVIRONMENT-url：" + URL);
        }

        if (PAYTYPE.isEmpty()) {
            logger.info("支付类型不能为空！");
            return "支付类型不能为空,请选择退款环境！";
        } else {
            logger.info("PAYTYPE！" + PAYTYPE);
            switch (PAYTYPE) {
                case "认证支付-申请交易-apply":
                    url = "/nocardpay/authPay/apply";
                    Map<String, Object> paramsMap = new HashMap<>(16);
                    paramsMap.put("requestTime", new Date().getTime());    //【必填】请求时间戳
                    paramsMap.put("outTradeNo", outTradeNo);               //【必填】请求交易号，唯一交易标识
                    paramsMap.put("tradeAmount", tradeAmount);             //【必填】交易金额单位：分
                    paramsMap.put("tradeCurrency", tradeCurrency);         //【必填】交易币种，CNY：人民币
//                    paramsMap.put("riskInfo", riskInfo);                 //风控信息 纬度和经度之间以英文逗号分隔，格式：lat<纬度>,lng<经度>
                    paramsMap.put("bankCode", bankCode);                   //【必填】银行编码  工商ICBC
                    paramsMap.put("cardType", cardType);                   //【必填】银行卡类型 1：借记卡，2：贷记卡
                    paramsMap.put("cardNo", cardNo);                       //【必填】银行卡号6217000010043148728
//                    paramsMap.put("cardExp", cardExp);                      //卡有效期 格式：YYMM 贷记卡必填
//                    paramsMap.put("cardCvv", cardCvv);                       //卡CVN2             贷记卡必填
                    paramsMap.put("holderName", holderName);               //【必填】持卡人姓名
                    paramsMap.put("holderIdType", holderIdType);           //【必填】持卡人证件类型
                    paramsMap.put("holderIdNo", holderIdNo);               //【必填】持卡人证件号
                    paramsMap.put("holderMobile", holderMobile);           //【必填】持卡人手机号

                    logger.info("url：" + URL + url);
                    result = dealPayment(URL + url, paramsMap,"POST");
                    break;
                case "认证支付-确认交易-confirm":
                    url = "/nocardpay/authPay/confirm";
                    Map<String, Object> paramsMap2 = new HashMap<>(16);

                    paramsMap2.put("requestTime", new Date().getTime());  //【必填】请求时间
                    paramsMap2.put("requestIp", requestIp);               //【必填】用户请求IP
                    paramsMap2.put("outTradeNo", outTradeNo);             //【必填】请求交易号，注意此处必须与签约申请请求号相同
                    paramsMap2.put("tradeAmount", tradeAmount);           //【必填】交易金额，单位：分
                    paramsMap2.put("tradeCurrency", tradeCurrency);       //【必填】交易币种，CNY：人民币
                    paramsMap2.put("tradeSubject", tradeSubject);         //【必填】订单摘要
                    paramsMap2.put("notifyAddress", notifyAddress);       //异步通知地址，用户支付成功后后台异步通知，如果不上送不会进行异步通知
//                    paramsMap2.put("riskInfo", riskInfo);                 //风控信息
                    paramsMap2.put("verifyCode", verifyCode);             //【必填】验证码
                    paramsMap2.put("bankCode", bankCode);                 //【必填】银行编码
                    paramsMap2.put("cardType", cardType);                 //【必填】银行卡类型 1：借记卡，2：贷记卡
                    paramsMap2.put("cardNo", cardNo);                     //【必填】卡号
//                    paramsMap2.put("cardExp", cardExp);                   //卡有效期
//                    paramsMap2.put("cardCvv", cardCvv);                   //卡CVN2
                    paramsMap2.put("holderName", holderName);             //【必填】持卡人姓名
                    paramsMap2.put("holderIdType", holderIdType);         //【必填】持卡人证件类型
                    paramsMap2.put("holderIdNo", holderIdNo);             //【必填】持卡人证件号
                    paramsMap2.put("holderMobile", holderMobile);         //【必填】持卡人手机号


                    logger.info("url：" + URL + url);
                    result = dealPayment(URL + url, paramsMap2,"POST");
                    break;
                case "交易结果-查询-queryTradeResult":
                    url = "/nocardpay/queryTradeResult";
                    Map<String, Object> data = new HashMap<>();
                    data.put("orderNo", orderNo);
                    data.put("tradeType", tradeType);
                    logger.info("请求报文：" + data);

                    logger.info("url：" + URL + url);
                    result = dealPayment(URL + url, data,"GET");
                    break;
                case "代付交易-paymentTrade":
                    url = "/nocardpay/paymentTrade";
                    Map<String, Object> paramsMap4 = new HashMap<>(16);

                    paramsMap4.put("requestIp", requestIp);               //【必填】用户请求IP
                    paramsMap4.put("outOrderNo", outOrderNo);             //【必填】外部订单号
                    paramsMap4.put("orderNo", orderNo);                   //【必填】订单编号
                    paramsMap4.put("holderName", holderName);             //【必填】持卡人姓名
                    paramsMap4.put("holderIdType", holderIdType);         //【必填】持卡人证件类型
                    paramsMap4.put("holderIdNo", holderIdNo);             //【必填】持卡人证件号
                    paramsMap4.put("holderMobile", holderMobile);         //【必填】持卡人手机号
                    paramsMap4.put("cardNo", cardNo);                     //【必填】卡号
                    paramsMap4.put("txAmt", txAmt);                       //【必填】提现金额
                    paramsMap4.put("bankCode", bankCode);                 //【必填】银行编码
                    paramsMap4.put("tradeSubject", tradeSubject);         //【必填】订单信息摘要

                    logger.info("url：" + URL + url);
                    result = dealPayment(URL + url, paramsMap4,"POST");
                    break;
                default:
            }
//            logger.info("URL+url：" + URL + url);
        }
        return result;
    }

    private static String dealPayment(String url, Map<String, Object> paramsMap,String httpmethod) {
        if (httpmethod.equals("POST")){
            try {
                System.out.println("请求报文 :" + JSON.toJSONString(paramsMap));
                Map<String, String> secureMap = SecureUtil.reqEncrypt(agentCode, paramsMap, DemoParams.privateKey, DemoParams.publicKey);
                String respContent = HTTPClient.post(url, secureMap);
//            System.out.println("respContent :"+respContent);
                if (respContent != null && !"".equals(respContent.trim())) {
                    Map<String, String> respMap = JSON.parseObject(respContent, new TypeReference<Map<String, String>>() {
                    });
                    Map<String, String> payResultMap = SecureUtil.reqDecrypt(respMap, DemoParams.privateKey, DemoParams.publicKey);
                    System.out.println("响应报文 :" + payResultMap);
//                String status = payResultMap.get("status");
//                if ("20000161".equals(status)) {
//                    System.out.println("---交易成功---");
//                    //TODO 业务逻辑处理
//                } else {
//                    System.out.println("---交易失败---");
//                    //TODO 业务逻辑处理
//                }

                    return JSON.toJSONString(payResultMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (httpmethod.equals("GET")){
            try {
                Map<String, String> reqData = SecureUtil.reqEncrypt(agentCode, paramsMap, DemoParams.privateKey, DemoParams.publicKey);
                String getParam = GetReqParamAppend.appendGetStr(reqData);
                url = url + "?" + getParam;
//                        System.out.println("url      :");
                String returnStr = HTTPClient.get(url);
                Map<String, String> returnMap = JSON.parseObject(returnStr, new TypeReference<Map<String, String>>() {
                });
                return SecureUtil.resDecrypt(agentCode, DemoParams.privateKey, DemoParams.publicKey, returnMap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            return "没有找到http请求方法";
        }

        return "交易失败！";
    }


}