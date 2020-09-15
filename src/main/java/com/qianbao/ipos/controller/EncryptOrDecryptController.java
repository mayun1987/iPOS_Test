package com.qianbao.ipos.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.qianbao.ipos.bean.EncryptOrDecrypt;
import com.qianbao.ipos.demo.SecureUtil;
import com.qianbao.ipos.util.AESUtil;
import com.qianbao.ipos.util.DESedeEncrypt;
import com.qianbao.ipos.util.IPosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

import static com.qianbao.ipos.demo.DemoParams.agentCode;

@RestController
public class EncryptOrDecryptController {
    //日志log4j2
    private static Logger logger = LoggerFactory.getLogger(EncryptOrDecryptController.class);


    //测试环境
    @RequestMapping(value = "/encryptOrDecrypt", method = RequestMethod.POST)
    public String EncryptOrDecrypt(@ModelAttribute EncryptOrDecrypt encryptOrDecrypt) throws Exception {
        String MODE = encryptOrDecrypt.getMODE();
        String TYPE = encryptOrDecrypt.getTYPE();
        String key1 = encryptOrDecrypt.getKey1();
        String key2 = encryptOrDecrypt.getKey2();
        String key3 = encryptOrDecrypt.getKey3();
        String keyValue1 = encryptOrDecrypt.getKeyValue1();
        String keyValue2 = encryptOrDecrypt.getKeyValue2();
        String keyValue3 = encryptOrDecrypt.getKeyValue3();
        String request = encryptOrDecrypt.getRequest();
        String response = encryptOrDecrypt.getResponse();



        switch (MODE) {
            case "POS-RSA":
                if (TYPE.equals("encrypt加密")) {
                    Map<String, Object> requestMap = JSON.parseObject(request);
                    Map<String, Object> encryptMap = IPosUtil.reqInfoEncrypt(keyValue1, keyValue2, requestMap);
                    String encryptStr = JSON.toJSONString(encryptMap);
                    logger.info(encryptStr);
                    return encryptStr;
                } else if (TYPE.equals("decrypt解密")) {
                    Map<String, Object> resMap = IPosUtil.resInfoDecrypt(keyValue1, keyValue2, JSON.parseObject(request, Map.class));
                    String resStr = JSON.toJSONString(resMap);
                    logger.info(resStr);
                    return resStr;
                } else if (TYPE.equals("sign加签")) {
                    Map<String, Object> requestMap = JSON.parseObject(request);
                    Map<String, Object> encryptMap = IPosUtil.reqInfoSign(keyValue2, requestMap);
                    String encryptStr = JSON.toJSONString(encryptMap);
                    logger.info(encryptStr);
                    return encryptStr;
                } else if (TYPE.equals("sign验签")) {
                    logger.info("暂未实现！");
                    return "暂未实现！";
                } else {
                    logger.info("暂未实现！");
                    return "暂未实现！";
                }
            case "推送AESUtil":
                if (TYPE.equals("encrypt加密")) {
                    logger.info(AESUtil.encrypt(request, keyValue1));
                    return AESUtil.encrypt(request, keyValue1);
                } else {
                    logger.info("暂未实现！");
                    return "暂未实现！";
                }
            case "生活退款DESede":
                if (TYPE.equals("encrypt加密")) {
                    logger.info(DESedeEncrypt.encrypt(keyValue1, request));
                    return DESedeEncrypt.encrypt(keyValue1, request);
                } else if (TYPE.equals("decrypt解密")) {
                    logger.info(DESedeEncrypt.decrypt(keyValue1, request));
                    return DESedeEncrypt.decrypt(keyValue1, request);
                } else {
                    logger.info("暂未实现！");
                    return "暂未实现！";
                }
            case "二维码SecureUtil":
                if (TYPE.equals("encrypt加密")) {
                    Map<String, Object> requestMap = JSON.parseObject(request);
                    Map<String, String> secureMap = SecureUtil.reqEncrypt(agentCode, requestMap, keyValue2, keyValue1);
                    String secureStr = JSON.toJSONString(secureMap);
                    logger.info(secureStr);
                    return secureStr;
                } else if (TYPE.equals("decrypt解密")) {
                    Map<String, String> returnMap = JSON.parseObject(request, new TypeReference<Map<String, String>>() {
                    });
                    String secureStr = SecureUtil.resDecrypt(agentCode, keyValue2, keyValue1, returnMap);
                    logger.info(secureStr);
                    return secureStr;
                } else if (TYPE.equals("sign加签")) {
                    logger.info("暂未实现！");
                    return "暂未实现！";
                } else if (TYPE.equals("sign验签")) {
                    logger.info("暂未实现！");
                    return "暂未实现！";
                } else {
                    logger.info("暂未实现！");
                    return "暂未实现！";
                }
            default:
        }
        return null;
    }


}

