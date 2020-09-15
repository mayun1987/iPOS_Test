package com.qianbao.ipos.controller;

import com.qianbao.ipos.bean.Push;
import com.qianbao.ipos.util.AESUtil;
import com.qianbao.ipos.util.CheckSign;
import com.qianbao.ipos.util.FormatDate;
import com.qianbao.ipos.util.HttpClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.*;
//import java.util.logging.Logger;
//import com.qianbao.ipos.log.AppLogger;
import com.alibaba.fastjson.JSON;

@RestController
public class PushController {

    //    static String time = "2019-05-15 18:47:00";
    static String time = FormatDate.timeFormat(System.currentTimeMillis(), "MM-dd HH:mm");

    private static Logger logger = LoggerFactory.getLogger(PushController.class);

//    @RequestMapping("/push")
//    public ModelAndView push() {
//        return new ModelAndView("index");
//    }


//    @RequestMapping("/refund")
//    public ModelAndView refund() {
//        return new ModelAndView("/refund");
//    }
//
//    @GetMapping("/push")
//    public String push() {
//        return "/push";
//    }


    //测试环境
    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public String PushTerminalNo(@ModelAttribute Push push) {
        String ENVIRONMENT = push.getENVIRONMENT();
        String PUSHTYPE = push.getPUSHTYPE();
        String ksn = push.getKSN();
        String terminal = push.getTerminal();
        String order = push.getOder();
        String amt = push.getAmt();
        String content = push.getContent();

        String reqUrl = "";
//                String responselog ="sit";

        logger.info("ENVIRONMENT：" + ENVIRONMENT);
        logger.info("PUSHTYPE：" + PUSHTYPE);
        if (ENVIRONMENT.isEmpty() || PUSHTYPE.isEmpty()) {
            logger.info("推送环境、推送类型不能为空！");
            return "推送环境、推送类型不能为空，请选择推送环境和推送类型！";
        } else if (ksn.isEmpty()) {
            logger.info("KSN不能为空！");
            return "KSN不能为空，请输入KSN！";
        } else if (terminal.isEmpty()) {
            logger.info("终端号不能为空！");
            return "终端号不能为空，请输入终端号！";
        } else {
            logger.info("ENVIRONMENT！" + ENVIRONMENT);
            switch (ENVIRONMENT) {
                case "测试环境":
                    reqUrl = "https://sit1-apis.qianbao.com/iposPush/v1/push/terminal/";
                    break;
                case "预发布环境":
                    reqUrl = "http://pre-apis.qianbao.com/iposPush/v1/push/terminal/";
                    break;
                case "生产环境":
                    reqUrl = "http://apis.qianbao.com/iposPush/v1/push/terminal/";
                    break;

                default:
            }
            logger.info("ENVIRONMENT-reqUrl+terminal：" + reqUrl + terminal);
        }

//                reqUrl = "https://sit1-apis.qianbao.com/iposPush/v1/push/terminal/";
        reqUrl = reqUrl + terminal;
        logger.info("reqUrl：" + reqUrl);

        //聚合码,优惠买单
        if (PUSHTYPE.equals("微信") || PUSHTYPE.equals("支付宝") || PUSHTYPE.equals("优惠买单")) {
            if (order.isEmpty()) {
                logger.info("订单号不能为空！");
                return "订单号不能为空！";
            } else if (order.length() < 12) {
                logger.info("oder位数输入太短，自动加11位0！");
                order = "00000000000" + order;
            }
            if (amt.isEmpty()) {
                logger.info("金额不能为空！");
                return "金额不能为空！";
            } else if (Double.parseDouble(amt) < 1.00) {
                logger.info("amt输入太小，自动补成1！");
                amt = "1";
            }
            logger.info("order: " + order + "amt:" + amt + ";reqUrl：" + reqUrl);
            switch (PUSHTYPE) {
                case "微信":
                    return CheckSign.checkSign_push(jhm_Map("3", order, amt), reqUrl);
//                            break;
                case "支付宝":
                    return CheckSign.checkSign_push(jhm_Map("2", order, amt), reqUrl);
//                            break;
                case "优惠买单":
                    return CheckSign.checkSign_push(yhmd_Map(order, amt), reqUrl);
//                            break;

                default:

            }

        }


        //消息推送
        if (PUSHTYPE.contains("消息")) {
            if (content.isEmpty()) {
                logger.info("消息内容不能为空！");
                return "消息内容不能为空！";
            }
            logger.info("PUSHTYPE：" + PUSHTYPE);
//                    String responselog ="消息推送响应";
            switch (PUSHTYPE) {
                case "普通消息公告":
                    return CheckSign.checkSign_push(xxzx_Map(0, "gg", content), reqUrl);
//                            break;
                case "普通消息升级提醒":
                    return CheckSign.checkSign_push(xxzx_Map(0, "sjtx", content), reqUrl);
//                            break;
                case "紧急消息公告":
                    return CheckSign.checkSign_push(xxzx_Map(1, "gg", content), reqUrl);
//                            break;
                case "紧急消息升级提醒":
                    return CheckSign.checkSign_push(xxzx_Map(1, "sjtx", content), reqUrl);
//                            break;

                default:

            }
        }

        return "推送请求异常！";

    }


    //聚合码
    @SuppressWarnings("serial")
    public static Map<String, Object> jhm_Map(final String payType, String orderNo, String amt) {
        Map<String, Object> map = new HashMap<String, Object>();
        double d = new Double(amt);
        map.put("title", "聚合码");
        map.put("type", "jhm");
        if (payType.equals("2")) {
            map.put("content", time + "，金额为" + Double.parseDouble(amt) / 100 + "元的支付宝交易支付成功，订单尾号为*" + orderNo.substring(12) + "。");
        } else if (payType.equals("3")) {
            map.put("content", time + "，金额为" + Double.parseDouble(amt) / 100 + "元的微信交易支付成功，订单尾号为*" + orderNo.substring(12) + "。");
        }
        map.put("detail", new HashMap<String, Object>() {
            {
                put("type", "txt");
                put("amt", amt);
                put("payType", payType);
                put("para", new HashMap<String, Object>() {{
                    put("orderNo", orderNo);
                }});
            }
        });
        return map;
    }


    //消息推送
    @SuppressWarnings("serial")
    public Map<String, Object> xxzx_Map(final int msgType, String type, String content) {
//        final String str = OrderNo.substring(OrderNo.length() - 7, OrderNo.length()-1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "升级提醒");
        map.put("msgLevel", msgType); //0,普通 1，紧急
        map.put("type", type);//gg：公告；sjtx：升级提醒
        map.put("content", content);//通知内容
        map.put("detail", new HashMap<String, Object>() {
            {
                put("type", "txt");
                put("para", new HashMap<String, Object>() {{
                    put("id", content);  //此id关联的pos_msg_info表的id值，对应有消息内容和发布人，表中msg_type的0为升级提醒，1为公告
                }});
            }
        });
        return map;
    }


    //优惠买单
    @SuppressWarnings("serial")
    public static Map<String, Object> yhmd_Map(String orderNo, String amt) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "钱包生活优惠买单");
        map.put("type", "yhmd");
        map.put("content", time + "，金额为" + Double.parseDouble(amt) / 100 + "元的钱包生活优惠买单交易支付成功，订单尾号为*" + orderNo.substring(12) + "。");
        map.put("detail", new HashMap<String, Object>() {
            {
                put("type", "txt");
                put("amt", amt);
//                put("paytype", "3");
                put("para", new HashMap<String, Object>() {{
                    put("orderNo", orderNo);
                }});
            }
        });
        return map;
    }


//    public static String pushPOST(Map<String, Object> pushTypeMap,String reqUrl) {
//
//        String sndData = JSON.toJSONString(pushTypeMap);
//        System.out.println("未加密报文：           " + sndData);
//        try {
//            System.out.println("加密报文请求：       " + AESUtil.encrypt(sndData, "qianbaoipospushx"));
//            String response = HttpClient.postReq(reqUrl, sndData,"UTF-8");    //微信
//            System.out.println("推送响应报文：       " + response);
//            return  response;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//
//    }

}