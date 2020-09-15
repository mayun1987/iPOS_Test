package com.qianbao.ipos.controller;

import com.qianbao.ipos.bean.Refund;
import com.qianbao.ipos.util.DESedeEncrypt;
import com.qianbao.ipos.util.HttpClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.*;

import com.alibaba.fastjson.JSON;

@RestController
public class RefundController {

    //默认结果
    public static String result = "未获取到！";
    //KEY
    private String desKey = "";
    private String url = "";
    //日志log4j2
    private static Logger logger = LoggerFactory.getLogger(RefundController.class);

//    @RequestMapping("/refund")
//    public ModelAndView refund() {
//        return new ModelAndView("index");
//    }

    String ENVIRONMENT = "";

    //测试环境
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public String Refund(@ModelAttribute Refund refund) {
        ENVIRONMENT = refund.getENVIRONMENT();
        String order = refund.getOder();
        String amt = refund.getAmt();
        if (ENVIRONMENT.isEmpty()) {
            logger.info("退款环境不能为空！");
            return "退款环境不能为空，请选择退款环境！";
        }else {
            logger.info("ENVIRONMENT！" + ENVIRONMENT);
            switch (ENVIRONMENT) {
                case "测试环境":
                    url = "https://sit-apis.qianbao.com/ipos/v1/qblife/refundOrder";
                    desKey = "ZWQ1N2M2ZTctNDdkMi00Nzk0LWE5ZmQtODM1ZDVmZjMwYTFh";
                    break;
                case "预发布环境":
                    url = "https://pre-apis.qianbao.com/ipos/v1/qblife/refundOrder";
                    desKey = "zm39nfhzKZ7EC4AskZSM1UBtDtlXJZ0I";
                    break;
                case "生产环境":
                    url = "https://apis.qianbao.com/ipos/v1/qblife/refundOrder";
                    desKey = "zm39nfhzKZ7EC4AskZSM1UBtDtlXJZ0I";
                    break;

                default:
            }
            logger.info("ENVIRONMENT-url：" + url);
        }

        if (order.isEmpty()) {
            logger.info("订单号不能为空！");
            return "订单号不能为空！";
        }
        if (amt.isEmpty()) {
            logger.info("金额不能为空！");
            return "金额不能为空！";
        }

        Map<String, String> refundMap = new HashMap<>();
        refundMap.put("orderNo", order);   //   原订单号 交易状态为成功 并且交易时间为当天
        refundMap.put("refundAmt", amt);   //   交易金额，可选填
        String jsonStr = JSON.toJSONString(refundMap);

        try {
            String resJSON = HttpClient.postReq(url, DESedeEncrypt.encrypt(desKey, jsonStr), "UTF-8");
            logger.info("请求报文：" + jsonStr);
//            logger.info("请求报文加密："+DESedeEncrypt.encrypt(desKey_t, jsonStr));
            Map<String, Object> resMap = JSON.parseObject(resJSON, Map.class);
            result = JSON.toJSON(resMap).toString();

            logger.info("响应报文：" + JSON.toJSON(resMap));

            if (!resMap.get("status").equals("20000301")) {
                result = "退款失败：" + result;
                logger.info(result);
                return result;
            } else {
                String DES_Str = DESedeEncrypt.decrypt(desKey, resMap.get("result").toString());
//              Map<String, Object> orderMap = JSON.parseObject(DES_Str, Map.class);
                resMap.put("result", DES_Str);
                logger.info("解密报文：" + JSON.toJSON(resMap));
                result = "退款成功：" + DES_Str;
                logger.info(result);
                return result;
            }

        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
            return result;
        }

    }


}