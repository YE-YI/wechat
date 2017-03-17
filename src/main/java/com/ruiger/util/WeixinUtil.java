package com.ruiger.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ruiger.modle.AccessToken;
import com.ruiger.modle.JsApiTicket;
import com.ruiger.modle.WeixinOauth2Token;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.util.Map;

/**
 * @author 睿哥
 * @version 1.0
 * @time 15:53
 * @description #
 * @since 2017/03/10
 */
public class WeixinUtil {
	private static Logger logger = Logger.getLogger(WeixinUtil.class);

	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	public final static String oauth_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	public final static String js_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";


	/**
	 * 获取token
	 *
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		//发送请求
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject && !jsonObject.containsKey("errcode")) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				logger.error("获取token失败 errcode:" + jsonObject.getIntValue("errcode") + " errmsg:" + jsonObject.getString("errmsg"));
			}
		} else {
			logger.error("获取token失败 errcode:" + jsonObject.getIntValue("errcode") + " errmsg:" + jsonObject.getString("errmsg"));

		}
		return accessToken;

	}

	/**
	 * 获取jsapi 凭证
	 *
	 * @return
	 */
	public static JsApiTicket getJsApiTicket(String token) {
		JsApiTicket jsApiTicket = null;
		String requestUrl = js_ticket_url.replace("ACCESS_TOKEN", token);
		//发送请求
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				jsApiTicket = new JsApiTicket();
				jsApiTicket.setTicket(jsonObject.getString("ticket"));
				jsApiTicket.setExpires_in(jsonObject.getIntValue("expires_in"));
			} catch (Exception e) {
				jsApiTicket = null;
				logger.error("获取JsApiTicket失败 errcode：" + jsonObject.getIntValue("errcode") + " errmsg:" + jsonObject.getString("errmsg"));
			}
		} else {
			logger.error("获取JsApiTicket失败");
		}
		return jsApiTicket;
	}

	/**
	 * 获取网页授权凭证
	 *
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static WeixinOauth2Token getOauth2Token(String appid, String appsecret, String code) {
		WeixinOauth2Token weixinOauth2Token = null;
		String requestUrl = oauth_token_url.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject && !jsonObject.containsKey("errcode")) {
			try {
				weixinOauth2Token = new WeixinOauth2Token();
				weixinOauth2Token.setAccessToken(jsonObject.getString("access_token"));
				weixinOauth2Token.setExpiresIn(jsonObject.getIntValue("expires_in"));
				weixinOauth2Token.setRefreshToken(jsonObject.getString("refresh_token"));
				weixinOauth2Token.setOpenId(jsonObject.getString("openid"));
				weixinOauth2Token.setScope(jsonObject.getString("scope"));
			} catch (JSONException e) {
				weixinOauth2Token = null;
				// 获取token失败
				logger.error("获取token失败 errcode:" + jsonObject.getIntValue("errcode") + " errmsg:" + jsonObject.getString("errmsg"));
			}
		} else {
			logger.error("获取token失败 errcode:" + jsonObject.getIntValue("errcode") + " errmsg:" + jsonObject.getString("errmsg"));
		}
		return weixinOauth2Token;
	}

	/**
	 * 发起https请求并获取结果
	 *
	 * @param requestUrl    请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr     提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSON.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		}
		return jsonObject;
	}

	/**
	 * URL编码（utf-8）
	 *
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据内容判断文件扩展名
	 *
	 * @param contentType
	 * @return
	 */
	public static String getFileExt(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType))
			fileExt = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileExt = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileExt = ".amr";
		else if ("video/mp4".equals(contentType))
			fileExt = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileExt = ".mp4";
		return fileExt;
	}

}
