/**   
* @Title: GetReqParamAppend.java 
* @Package com.demo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author yanghj@bailianpay.com   
* @date 2019年4月11日 下午5:58:28 
* @version V1.0   
*/
package com.qianbao.ipos.demo;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/** 
* @ClassName: GetReqParamAppend 
* @Description: get请求参数拼接 
* @author yanghj@bailianpay.com
* @date 2019年4月11日 下午5:58:28 
*/
public class GetReqParamAppend {
	public static String appendGetStr(Map<String, String> map) {
		if(map==null) {
			return null;
		}
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		StringBuffer buffer=new StringBuffer();
		while(iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String key = entry.getKey();
			String value = entry.getValue();
			buffer.append(key).append("=").append(value).append("&");
		}
		buffer.setLength(buffer.length()-1);
		return buffer.toString();
	};
}
