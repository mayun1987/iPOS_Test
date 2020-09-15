package com.qianbao.ipos.controller;

import com.qianbao.ipos.bean.PxPay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import java.util.logging.Logger;
//import com.qianbao.ipos.log.AppLogger;

@RestController
@Controller
//@RequestMapping("/pxPay")
public class Auth_TestController {

    //默认结果
    public static String result = "未获取到！";
    private String URL = "";
    private String url = "";
    //日志log4j2
    private static Logger logger = LoggerFactory.getLogger(Auth_TestController.class);

//    @RequestMapping("/auth")
//    public ModelAndView auth() {
//        return new ModelAndView("index");
//    }

    String ENVIRONMENT = "";

    //测试环境
    @RequestMapping(value = "/pxPay", method = RequestMethod.POST)
    public String PxPay(@ModelAttribute PxPay pxPay) {
        ENVIRONMENT = pxPay.getENVIRONMENT();

        if (ENVIRONMENT.isEmpty()) {
            logger.info("test支付环境不能为空！");
            return "test支付环境不能为空，请选择支付环境！";
        }else {
            logger.info("ENVIRONMENT！" + ENVIRONMENT);
            switch (ENVIRONMENT) {
                case "测试环境":
                    URL = "http://172.28.38.67:8083/nocardpay/authPay/";
                    break;
                case "预发布环境":
                    URL = "";
                    break;
                case "生产环境":
                    URL = "";
                    break;

                default:
            }
            logger.info("URL+url：" + URL+ url);
        }


        return null;
    }


}