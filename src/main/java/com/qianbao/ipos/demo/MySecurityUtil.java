/**   
* @Title: SecureUtil.java 
* @Package com.qianbao.csdnpay.gateway.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author yanghj@bailianpay.com   
* @date 2019年4月11日 上午11:33:47 
* @version V1.0   
*/
package com.qianbao.ipos.demo;

import com.zrj.pay.util.constant.PayConstants;
import com.zrj.pay.util.secure.AES;
import com.zrj.pay.util.secure.RSA;

import java.security.SignatureException;
import java.util.Map;
import java.util.UUID;

/** 
* @ClassName: SecureUtil 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yanghj@bailianpay.com
* @date 2019年4月11日 上午11:33:47 
*  
*/
public class MySecurityUtil {
	
	/**
	 * 解密交易信息.
	 * 参数规则见接入文档.
	 * 1.按照agentCode+cer+data的顺序,中间不含任何拼接字符,拼接成字符串.用对方的公钥进行验签.
	 * 2.用RSA的私钥解密证书cer,得到AES的秘钥.
	 * 3.用AES秘钥解密data,得到明文交易JSON字符串.
	 *
//	 * @param agentCode 商户号
//	 * @param cer 证书
//	 * @param data 数据集合
//	 * @param sign 签名
	 * @param ourPrivateKey 己方的私钥
	 * @param otherPublicKey 对方的公钥
	 * @return 解密后的交易参数JSON格式明文
	 * 
	 * @throws Exception 签名异常,解密异常
	 */
	public static Map<String, String> reqDecrypt(Map<String, String> reqDataMap,String ourPrivateKey,String otherPublicKey) 
			throws Exception{
		String agentCode=reqDataMap.get("agentCode");
		String cer=reqDataMap.get("cer");
		String data=reqDataMap.get("data");
		String sign=reqDataMap.get("sign");
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(agentCode);
		sBuffer.append(cer);
		sBuffer.append(data);
		String text = sBuffer.toString();
		String tradeJson = null;
		//1.按照agentCode+cer+data的顺序,中间不含任何拼接字符,拼接成字符串.用对方的公钥进行验签
		if(!RSA.verify(sign, text , otherPublicKey, PayConstants.DEFAULT_CHARSET)){
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
		reqDataMap.remove("cer")	;
		reqDataMap.remove("sign");
		reqDataMap.put("data", tradeJson);
		return reqDataMap;
	}
	
	/**
	 * ipos响应信息解密
	 * @param publicKey 公钥
	 * @param privateKey 私钥
	 * @param resMapData 响应信息数据Map
	 * @return Map
	 * @throws Exception 
	 * */
	public static Map<String, Object> resEncrypt(String publicKey, String privateKey, Map<String, Object> resMapData) throws Exception{
		//1.随机生成一个AES的密钥
		String keySeed = UUID.randomUUID().toString();
		String aesKey = AES.key(keySeed, 128);
		//String sign =  resMapData.get("sign");
		String data =  (String) resMapData.get("data");
		String status = (String) resMapData.get("status");
		String message = (String) resMapData.get("message");
		if(null==status) status="20000161";
		if(null==message) message="成功";
		String cer = RSA.encrypt(aesKey, publicKey, PayConstants.DEFAULT_CHARSET);
		data=AES.encrypt(data, aesKey, PayConstants.DEFAULT_CHARSET);
		StringBuffer content = new StringBuffer(cer);
		content.append(data).append(message).append(status);
		String sign = RSA.sign(new String(content), privateKey, PayConstants.DEFAULT_CHARSET);
		resMapData.put("sign", sign);
		resMapData.put("cer", cer);
		resMapData.put("status", status);
		resMapData.put("message", message);
		return resMapData;
	}
}
