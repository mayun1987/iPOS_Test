package com.qianbao.ipos.util;

import com.alibaba.fastjson.JSON;
import com.qianbao.ipos.security.RSA.RSAUtil;
import com.qianbao.ipos.security.error.PosRestHubCode;
import com.qianbao.ipos.security.vo.ReqCommonVo;
import com.qianbao.ipos.security.vo.SecurityVO;
//import com.qianbao.secure.aes.AESUtil;
//import com.qianbao.secure.rsa.RSAUtil;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class IPosUtil {

    public static final String SHA1WithRSA = "SHA1WithRSA";
    public static final String MD5WithRSA = "MD5WithRSA";

    public static SecurityVO initRASKey()
    {
        try
        {
            return RSAUtil.initKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Map<String, Object> reqInfoEncrypt(SecurityVO securityVO, ReqCommonVo reqCommonVo)
    {
        Map<String, Object> reqDataMap = reqCommonVo.getData();
        String token = reqCommonVo.getToken();
        String appVersion = reqCommonVo.getAppVersion();
        String apiVersion = reqCommonVo.getApiVersion();
        reqDataMap.put("token", token);
        reqDataMap.put("appVersion", appVersion);
        reqDataMap.put("apiVersion", apiVersion);
        return reqInfoEncrypt(securityVO, reqDataMap);
    }


    public static Map<String, Object> reqInfoEncrypt(String publicKey, String privateKey, Map<String, Object> reqDataMap)
    {
        try
        {
            Map<String, Object> reqMap = new HashMap();
            String token = (String)reqDataMap.get("token");
            System.out.println("token: " + token);
            reqDataMap.remove("token");
            String uuid = UUID.randomUUID().toString();
//      String uuid = "9c110189-b20d-483a-9428-52233ef89420";
//      System.out.println("randomUUID(): " + UUID.randomUUID());
//      System.out.println("uuid: " + uuid);
            uuid = uuid.substring(0, 16);
//      System.out.println("uuid_16: " + uuid);
//      reqDataMap.put("timestamp", System.currentTimeMillis() + "");
//      reqDataMap.put("timestamp", "1528770698130");

//      System.out.println("timestamp: " + System.currentTimeMillis());
            int random = (int)((Math.random() * 9.0D + 1.0D) * 100000.0D);
//      int random = 955915;
            reqDataMap.put("random", random + "");
//      System.out.println("random: " + random);
            String jsonStr = JSON.toJSONString(reqDataMap);
//      System.out.println("jsonStr("+jsonStr.length()+"):" + jsonStr);

            String cipherReqData = AESUtil.encrypt(jsonStr, uuid);
//      System.out.println("cipherReqData("+cipherReqData.length()+"): " + cipherReqData);

            String cert = RSAUtil.encrypt(uuid, publicKey);
//    String cert = RSAUtil.encrypt("62efe044-a1c2-49", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZKfh+STLXOgjdr2iYG8xqVoAqXgEBrP2WPzKOtn+pOkE9hyWKfom4dlX1HcvByl4v2BTKCcK9+23GlIe6l8jXo4knkO1lx1ieJAnt3BYz8haEESefRG/zUPuKUOholCMThglTPvUa0nOKSCbu8J8RWRUe7Tqk+kctAgqOzc8MMwIDAQAB");
//      System.out.println("uuid: " + uuid+ " 和：\n" + publicKey);
//      System.out.println("cert("+cert.length()+"): " + cert);
            String signContent = cert + cipherReqData + token;
//      System.out.println("signContent("+signContent.length()+"): " + signContent);

            String sign = RSAUtil.sign(signContent, "SHA1WithRSA", privateKey);
//      String sign = RSAUtil.sign("NJah/TPvPSqu0M6/ppFTkHnl2hnFZ65mBk8Cxb4UbW5C/9m6TbjvhMHwUgoOwyMBSbw6xymalTp2rKYp57m/nU5JudneThpgXBbDGzvDLax4635oHIATJe4RMZvzO+WxtUHH2ZS98U4rDOw8OwskBzsLh+9JMr7lN5vXf0JoF4U=824mSGHcuDTEDCZPMhoCCO0kpCJUDjeFXZBAeGbYAN7XVtBuVOlJNHsGM6laZlOUS9tiDmDlrZ2s68SNpMuZd58e3piYS/6crtTIgv49jCPydM3+jo3CqytaO0yjV5jw0fhbHyoHTmsjkpAEAh0AlPThTBNEYb4vCj1PyS/ka/06bEr7ltdjWo4GNQM6pJBDCbe+eQBtX8kOV/ddA5xKAw==token", "SHA1WithRSA", privateKey);
//      System.out.println("sign("+sign.length()+"): " + sign);
            reqMap.put("token", token);
            reqMap.put("data", cipherReqData);
            reqMap.put("cert", cert);
            reqMap.put("sign", sign);
            return reqMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//  public static Map<String, Object> reqInfoEncrypt(String publicKey, String privateKey, Map<String, Object> reqDataMap)
//  {
//    try
//    {
//      Map<String, Object> reqMap = new HashMap();
//      String token = (String)reqDataMap.get("token");
//      System.out.println("token: " + token);
//      reqDataMap.remove("token");
//      String uuid = UUID.randomUUID().toString();
////      String uuid = "9c110189-b20d-483a-9428-52233ef89420";
//      System.out.println("uuid: " + uuid);
//      uuid = uuid.substring(0, 16);
//      System.out.println("uuid_16: " + uuid);
//      reqDataMap.put("timestamp", "1526389421" + "");
//      System.out.println("timestamp: " + System.currentTimeMillis());
//      int random = (int)((Math.random() * 9.0D + 1.0D) * 100000.0D);
//      reqDataMap.put("random", random + "");
//      System.out.println("random: " + random);
//      String jsonStr = JSON.toJSONString(reqDataMap);
//      String cipherReqData = AESUtil.encrypt(jsonStr, uuid);
//      System.out.println("cipherReqData("+cipherReqData.length()+"): " + cipherReqData);
//      String cert = RSAUtil.encrypt(uuid, publicKey);
//      System.out.println("cert("+cert.length()+"): " + cert);
//      String signContent = cert + cipherReqData + token;
//      System.out.println("signContent("+signContent.length()+"): " + signContent);
//      String sign = RSAUtil.sign(signContent, "SHA1WithRSA", privateKey);
//      System.out.println("sign("+sign.length()+"): " + sign);
//      reqMap.put("token", token);
//      reqMap.put("data", cipherReqData);
//      reqMap.put("cert", cert);
//      reqMap.put("sign", sign);
//      return reqMap;
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    return null;
//  }





    public static Map<String, Object> reqInfoSign(String privateKey, Map<String, Object> reqDataMap)
    {
        try
        {
            Map<String, Object> reqMap = new HashMap();
            String token = (String)reqDataMap.get("token");
            reqDataMap.remove("token");
//      reqDataMap.put("timestamp", System.currentTimeMillis() + "");
            reqDataMap.put("timestamp", "1529033876713");
            int random = (int)((Math.random() * 9.0D + 1.0D) * 100000.0D);
//      reqDataMap.put("random", random + "");
            reqDataMap.put("random", "406068");
            TreeMap<String, Object> treeMap = new TreeMap();
//      HashMap<String, Object> treeMap = new HashMap();
            treeMap.putAll(reqDataMap);
            Iterator<Map.Entry<String, Object>> it = treeMap.entrySet().iterator();
            StringBuffer signBuffer = new StringBuffer();
            while (it.hasNext()) {
                Object obj = ((Map.Entry)it.next()).getValue();

                if (!(obj instanceof List))
                {

                    signBuffer.append(obj); }
            }
            String signContent = signBuffer.toString() + token;
//      System.out.println("signContent("+signContent.length()+"): " + signContent);
            String sign = RSAUtil.sign(signContent, "SHA1WithRSA", privateKey);
//      System.out.println("sign("+sign.length()+"): " + sign);
            reqMap.put("token", token);
            reqMap.put("data", reqDataMap);
            reqMap.put("sign", sign);
            return reqMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    public static Map<String, Object> resInfoCheckSign(String publicKey, Map<String, Object> resMapData)
    {
        try
        {
            String sign = (String)resMapData.get("sign");

            Map<String, Object> data = (Map)resMapData.get("data");
            if (null == data) {
                return resMapData;
            }
            TreeMap<String, Object> treeMap = new TreeMap();
            treeMap.putAll(data);
            Iterator<Map.Entry<String, Object>> it = treeMap.entrySet().iterator();
            StringBuffer signBuffer = new StringBuffer();
            while (it.hasNext()) {
                Object obj = ((Map.Entry)it.next()).getValue();

                if (!(obj instanceof List))
                {

                    signBuffer.append(obj); }
            }
            signBuffer.append(resMapData.get("message"));
            signBuffer.append(resMapData.get("status"));
            boolean checkFlag = RSAUtil.checkSign(signBuffer.toString(), sign, publicKey, "SHA1WithRSA");
            if (!checkFlag) {
                resMapData.put("status", PosRestHubCode.ERROR_CODE_SIGN_ERROR.getCode());
                resMapData.put("message", PosRestHubCode.ERROR_CODE_SIGN_ERROR.getDesc());
                return resMapData;
            }
            return resMapData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }







    public static Map<String, Object> reqInfoEncrypt(SecurityVO securityVO, Map<String, Object> reqDataMap)
    {
        try
        {
            String publicKey = securityVO.getPublicKey();
            String privateKey = securityVO.getPrivateKey();
            return reqInfoEncrypt(publicKey, privateKey, reqDataMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }






    public static Map<String, Object> resInfoDecrypt(SecurityVO securityVO, Map<String, Object> resMapData)
    {
        String publicKey = securityVO.getPublicKey();
        String privateKey = securityVO.getPrivateKey();
        return resInfoDecrypt(publicKey, privateKey, resMapData);
    }








    public static Map<String, Object> resInfoDecrypt(String publicKey, String privateKey, Map<String, Object> resMapData)
    {
        String cert = (String)resMapData.get("cert");
        String sign = (String)resMapData.get("sign");
        String data = (String)resMapData.get("data");
        String status = (String)resMapData.get("status");
        String message = (String)resMapData.get("message");
        if (null == data) {
            return resMapData;
        }
        String content = cert + data + message + status;
        Map<String, Object> returnMap = new HashMap();
        try {
            boolean flag = RSAUtil.checkSign(content, sign, publicKey, "SHA1WithRSA");
            if (!flag) {
                returnMap.put("status", PosRestHubCode.ERROR_CODE_SIGN_ERROR.getCode());
                returnMap.put("message", PosRestHubCode.ERROR_CODE_SIGN_ERROR.getDesc());
                return returnMap;
            }
            String dataMapJSONStr = AESUtil.decrypt(data, RSAUtil.decrypt(cert, privateKey));
//      System.out.println("RSAUtil.decrypt(cert, privateKey): " + RSAUtil.decrypt(cert, privateKey));
            returnMap = (Map)JSON.parseObject(dataMapJSONStr, Map.class);
            returnMap.put("status", status);
            returnMap.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("status", PosRestHubCode.ERROR_CODE_SIGN_ERROR.getCode());
            returnMap.put("message", PosRestHubCode.ERROR_CODE_SIGN_ERROR.getDesc());
        }
        return returnMap;
    }

}
