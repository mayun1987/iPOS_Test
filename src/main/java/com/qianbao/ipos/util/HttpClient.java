package com.qianbao.ipos.util;


import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class HttpClient {

    public static String postReq(String url, String content,String encoding) throws Exception {

        //创建httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httppost = new HttpPost(url);
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
        httppost.setConfig(requestConfig);

        StringEntity stringEntity = new StringEntity(content, ContentType.APPLICATION_JSON);
        httppost.setEntity(stringEntity);
        try {
            //发送http请求，并将响应结果赋值给response
            CloseableHttpResponse response = httpclient.execute(httppost);
            //从响应报文中获取到响应状态码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity);
                }
            } else {
                throw new Exception(String.format("the response status code is %d", statusCode));
            }
        } catch (IOException e) {
            throw new Exception(String.format("发送请求失败:%s", e.getMessage()), e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
