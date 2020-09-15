/**   
* @Title: SecureUtil.java 
* @Package com.qianbao.csdnpay.gateway.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author yanghj@bailianpay.com   
* @date 2019年4月11日 上午11:33:47 
* @version V1.0   
*/
package com.qianbao.ipos.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zrj.pay.util.constant.PayConstants;
import com.zrj.pay.util.secure.AES;
import com.zrj.pay.util.secure.RSA;

import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** 
* @ClassName: SecureUtil 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yanghj@bailianpay.com
* @date 2019年4月11日 上午11:33:47 
*  
*/
public class SecureUtil {
	/**
	 * 解密交易信息.
	 * 参数规则见接入文档.
	 * 1.按照agentCode+cer+data的顺序,中间不含任何拼接字符,拼接成字符串.用对方的公钥进行验签.
	 * 2.用RSA的私钥解密证书cer,得到AES的秘钥.
	 * 3.用AES秘钥解密data,得到明文交易JSON字符串.
	 *
	 * @param agentCode 代理商编号
//	 * @param cer 证书
//	 * @param data 数据集合
//	 * @param sign 签名
	 * @param ourPrivateKey 己方的私钥
	 * @param otherPublicKey 对方的公钥
	 * @return 解密后的交易参数JSON格式明文
	 * 
	 * @throws Exception 签名异常,解密异常
	 */
	public static String resDecrypt(String agentCode, String ourPrivateKey, String otherPublicKey, Map<String, String> resData)
			throws Exception{
		String data = resData.get("data");
		String cer = resData.get("cer");
		String status = resData.get("status");
		String message = resData.get("message");
		String sign = resData.get("sign");
		if(null==sign) {
			System.out.println(JSON.toJSONString(resData));
			throw new Exception("返回验签数据异常");
		}
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(agentCode);
		sBuffer.append(cer);
		if(null!=data) {
			sBuffer.append(data);
		}
	/*	sBuffer.append(message);
		sBuffer.append(status);*/
		String text = sBuffer.toString();
		String tradeJson = null;
		//1.按照merchant+cer+data的顺序,中间不含任何拼接字符,拼接成字符串.用对方的公钥进行验签
		if(!RSA.verify(sign, text , otherPublicKey, PayConstants.DEFAULT_CHARSET)){
			throw new SignatureException("验证签名不通过");
		}
		Map<String, String> returnDataMap = null;
//		Map<String, String> resDataMap = new HashMap<String, String>(4);
		if(null!=data) {
			try {
				returnDataMap = JSON.parseObject(tradeJson, new TypeReference<Map<String, String >>() {
		        });
				//2.用RSA的私钥解密证书cer,得到AES的秘钥
				String aesKey = RSA.decrypt(cer, ourPrivateKey, PayConstants.DEFAULT_CHARSET);
				//3.用AES秘钥解密data,得到明文交易JSON字符串
				tradeJson = AES.decrypt(data, aesKey, PayConstants.DEFAULT_CHARSET);
				System.out.println("响应报文 :" + tradeJson);
				return tradeJson;
			} catch (Exception e) {
				throw new SecurityException("解密异常");
			}
		}

		return "解密异常";
	}

	public static Map<String, String> reqDecrypt(Map<String, String> reqDataMap, String ourPrivateKey, String otherPublicKey)
			throws Exception {
		String agentCode = reqDataMap.get("agentCode");
		String cer = reqDataMap.get("cer");
		String data = reqDataMap.get("data");
		String sign = reqDataMap.get("sign");
		//get请求+会转义成" ",故此处要替换回来
		data = data.replace(" ", "+");
		sign = sign.replace(" ", "+");
		cer = cer.replace(" ", "+");
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(agentCode);
		sBuffer.append(cer);
		sBuffer.append(data);
		String text = sBuffer.toString();
		String tradeJson = null;
		//1.按照agentCode+cer+data的顺序,中间不含任何拼接字符,拼接成字符串.用对方的公钥进行验签
		if (!RSA.verify(sign, text, otherPublicKey, PayConstants.DEFAULT_CHARSET)) {
			throw new SignatureException("验证签名不通过");
		}
		try {
			//2.用RSA的私钥解密证书cer,得到AES的秘钥
			String aesKey = RSA.decrypt(cer, ourPrivateKey, PayConstants.DEFAULT_CHARSET);
			//3.用AES秘钥解密data,得到明文交易JSON字符串
			tradeJson = AES.decrypt(data, aesKey, PayConstants.DEFAULT_CHARSET);
		} catch (Exception e) {
			throw new SecurityException("解密异常");
		}
       /* reqDataMap.remove("cer");
        reqDataMap.remove("sign");
        reqDataMap.put("data", tradeJson);*/
		return JSON.parseObject(tradeJson, Map.class);
	}
	/**
	 * 加密交易信息.
	 * 参数规则见接入文档.
	 * 1.随机生成一个AES的秘钥.
	 * 2.用生成的AES秘钥加密交易信息JSON字符,得到数据集合密文data.
	 * 3.将AES秘钥用对方的公钥加密,生成证书cer.
	 * 4.按照agentCode+cer+data的顺序,中间不含任何拼接字符,生成字符串,用自己的私钥签名.
	 *
	 * @param agentCode 代理商编号
	 * @param data 请求业务数据
	 * @param ourPrivateKey 己方的私钥
	 * @param otherPublicKey 对方的公钥
	 * @return 返回key为MERCHANT,CER,DATA,SIGN的4个值的Map
	 * @throws Exception 签名异常,加密异常
	 */
	public static Map<String, String> reqEncrypt(String agentCode, Map<String, Object> data, String ourPrivateKey, String otherPublicKey) throws Exception{
		//1.随机生成一个AES的密钥
		String keySeed = UUID.randomUUID().toString();
		String aesKey = AES.key(keySeed, 128);
		
		String datajsonStr = null;
		String cer = null;
		try {
			//2.用生成的AES秘钥加密交易信息JSON字符,得到数据集合密文data
			datajsonStr = AES.encrypt(JSON.toJSONString(data), aesKey, PayConstants.DEFAULT_CHARSET);
			//3.将AES秘钥用对方的公钥加密,生成证书cer
			cer = RSA.encrypt(aesKey, otherPublicKey, PayConstants.DEFAULT_CHARSET);
		} catch (Exception e) {
			throw new SecurityException("加密异常");
		}
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(agentCode);
		sBuffer.append(cer);
		sBuffer.append(datajsonStr);
		String text = sBuffer.toString();
//		System.out.println("加签字符串:"+text);
		String sign = null;
		try {
			//4.按照merchant+cer+data的顺序,中间不含任何拼接字符,生成字符串,用自己的私钥签名
			sign = RSA.sign(text, ourPrivateKey, PayConstants.DEFAULT_CHARSET);
		} catch (Exception e) {
			throw new SignatureException("签名异常");
		}
		
		Map<String, String> args = new HashMap<String, String>(4);
		args.put("agentCode", agentCode);
		args.put("cer", cer);
		args.put("data", datajsonStr);
		args.put("sign", sign);
		
		return args;
	}
}
