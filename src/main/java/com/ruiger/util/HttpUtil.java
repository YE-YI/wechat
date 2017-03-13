package com.ruiger.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author 睿哥
 * @version 1.0
 * @time 9:46
 * @description #
 * @since 2017/03/10
 */
public  class HttpUtil {
	private final static Logger log = Logger.getLogger(HttpUtil.class);

	public final static int HTTP_GET = 1;		//get请求
	public final static int HTTP_POST = 2;		//post请求

	public static String executeUrl(String url,int type) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse lfdResponse = null;
		if(type == 1){
			HttpGet httpGet = new HttpGet(url);
			lfdResponse = httpClient.execute(httpGet);
		}else {
			HttpPost httpPost = new HttpPost(url);
			lfdResponse = httpClient.execute(httpPost);
		}
		if (lfdResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity loginEntity = lfdResponse.getEntity();
			String entityContent = EntityUtils.toString(loginEntity,"utf-8");
			return entityContent;
		}
		return null;
	}
}
