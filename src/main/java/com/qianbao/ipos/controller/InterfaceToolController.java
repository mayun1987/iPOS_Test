package com.qianbao.ipos.controller;

import com.qianbao.ipos.bean.InterfaceTool;
import com.qianbao.ipos.demo.HTTPClient;
import com.qianbao.ipos.util.CheckSign;
import com.qianbao.ipos.util.FormatDate;
import com.qianbao.ipos.util.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InterfaceToolController {

    //    static String time = "2019-05-15 18:47:00";
    static String time = FormatDate.timeFormat(System.currentTimeMillis(), "MM-dd HH:mm");

    private static Logger logger = LoggerFactory.getLogger(InterfaceToolController.class);


    //测试环境
    @RequestMapping(value = "/interfaceTool", method = RequestMethod.POST)
    public String InterfaceSend(@ModelAttribute InterfaceTool interfaceTool) throws Exception {
        String ENVIRONMENT = interfaceTool.getENVIRONMENT();
        String SENDTYPE = interfaceTool.getSENDTYPE();
        String url = interfaceTool.getURL();
        String sendMap = interfaceTool.getSendMap();

        logger.info("ENVIRONMENT：" + ENVIRONMENT);
        logger.info("SENDTYPE：" + SENDTYPE);
        if (url.isEmpty()) {
            logger.info("请求url不能为空！请输入或选择地址！");
            return "请求url不能为空！请输入或选择地址！";
        }
        switch (SENDTYPE){
            case "POST请求":
                String responsePost = HttpClient.postReq(url, sendMap, "UTF-8");
                return responsePost;
            case "GET请求":
                url = url + "?" + sendMap;
                String responseGet = HTTPClient.get(url);
                return responseGet;
            default:
        }


        return null;
    }

}