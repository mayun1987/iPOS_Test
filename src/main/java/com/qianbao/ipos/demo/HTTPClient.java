/**
* @Title: HTTPClient.java
* @Package com.sit.threadtest
* @Description: TODO(用一句话描述该文件做什么)
* @author songgao
* @date 2018年12月25日
* @version V1.0
*/
package com.qianbao.ipos.demo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @ClassName: HTTPClient
* @Description: TODO(这里用一句话描述这个类的作用)
* @author songgao
* @date 2018年12月25日
*
*/
public class HTTPClient {
	public static String get(String url) {
		System.out.println("HTTPClient:" + url);
		HttpGet httpGet=new HttpGet(url);
		CloseableHttpClient httpClient= HttpClients.createDefault();
		try {
			long startTime = System.currentTimeMillis();
			HttpResponse httpResponse = httpClient.execute(httpGet);
			long useTime = System.currentTimeMillis()-startTime;
			HttpEntity entity = httpResponse.getEntity();
			String returnStr = EntityUtils.toString(entity);
			System.out.println(Thread.currentThread().getName()+"(returnStr):"+returnStr+"--useTime:"+useTime);
            return returnStr;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public static String post(String url,Map<String, String> params) {
		HttpPost Httppost=new HttpPost(url);
		CloseableHttpClient httpClient= HttpClientBuilder.create().build();
		try {
			long startTime = System.currentTimeMillis();
			List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
			pairList.add(new BasicNameValuePair("agentCode", params.get("agentCode")));
			pairList.add(new BasicNameValuePair("cer", params.get("cer")));
			pairList.add(new BasicNameValuePair("data", params.get("data")));
			pairList.add(new BasicNameValuePair("sign", params.get("sign")));
			Httppost.setEntity(new UrlEncodedFormEntity(pairList,"utf-8"));
			HttpResponse httpResponse = httpClient.execute(Httppost);
			long useTime = System.currentTimeMillis()-startTime;
			HttpEntity entity = httpResponse.getEntity();
			String returnStr = EntityUtils.toString(entity);
//			System.out.println(Thread.currentThread().getName()+"(POST) :"+returnStr+"--useTime:"+useTime);
            return returnStr;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		//HTTPClient.get("http://localhost:8089/getDemo");
		HTTPClient.post("http://localhost:8080/csdnpay/aip",null);
	}
}
