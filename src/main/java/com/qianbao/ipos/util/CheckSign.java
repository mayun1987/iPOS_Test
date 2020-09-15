package com.qianbao.ipos.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;

public class CheckSign {

	//push
//	@SuppressWarnings({ "unchecked" })
	public static String checkSign_push(Map<String, Object> pushTypeMap,String reqUrl) {
		
		String sndData = JSON.toJSONString(pushTypeMap);
		System.out.println("未加密报文：           " + sndData);
		try {
			System.out.println("加密报文请求：       " + AESUtil.encrypt(sndData, "qianbaoipospushx"));
		    String response = HttpClient.postReq(reqUrl, sndData,"UTF-8");    //微信
		    System.out.println("推送响应报文：       " + response);

		    return response;

		} catch (Exception e) {
            e.printStackTrace();
        }
        return "推送请求失败！";
	}


}
