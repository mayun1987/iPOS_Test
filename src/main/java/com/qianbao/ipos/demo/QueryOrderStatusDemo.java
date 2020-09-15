/**   
* @Title: QueryOrderStatus.java 
* @Package com.demo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author yanghj@bailianpay.com   
* @date 2019年4月11日 下午5:16:27 
* @version V1.0   
*/
package com.qianbao.ipos.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.HashMap;
import java.util.Map;

/** 
* @ClassName: QueryOrderStatus 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yanghj@bailianpay.com
* @date 2019年4月11日 下午5:16:27 
*/
public class QueryOrderStatusDemo extends DemoParams{
	public static void main(String[] args) {
//		String url="http://localhost:8080/nocardpay/queryTradeResult";
		String url="http://172.28.38.67:8083/nocardpay/queryTradeResult";
		String orderNo   = "201905220000000311";    //【必填】订单号
		String tradeType = "1";                     //【必填】支付类型 1：认证支付 2：代付

		Map<String, Object> data =new HashMap<>();
		data.put("orderNo", orderNo);
		data.put("tradeType", tradeType);
		System.out.println("请求报文 :" + data);
//		System.out.println();

		try {
			Map<String, String> reqData = SecureUtil.reqEncrypt(agentCode, data, privateKey, publicKey);
//			System.out.println("reqData  :" + reqData);
			String getParam = GetReqParamAppend.appendGetStr(reqData);
			url = url + "?" + getParam;
			System.out.println("url      :");
			String returnStr = HTTPClient.get(url);
//			System.out.println("returnStr:" + returnStr);
			Map<String, String> returnMap = JSON.parseObject(returnStr, new TypeReference<Map<String, String>>() {
	        });
//			Map<String, String> resData = SecureUtil.resDecrypt(agentCode, privateKey, publicKey, returnMap);
			String resDataString = SecureUtil.resDecrypt(agentCode, privateKey, publicKey, returnMap);
//			System.out.println("resData  :" + resData);
//			HTTPClient.post(url, reqData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
