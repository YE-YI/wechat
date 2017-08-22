package com.ruiger.util;

import org.apache.log4j.Logger;

/**
 * @author 睿哥
 * @version 1.0
 * @time 17:01
 * @description #
 * @since 2017/03/14
 */
public class WxUrlUtil {
	private static Logger logger = Logger.getLogger(WxUrlUtil.class);

	private static String appid = "wxc9bd5f35532ae9ca";

	private static String authorize_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";



	/**
	 * 生成微信授权访问首页
	 * @param appid
	 * @param index_url
	 * @param state
	 * @return
	 */
	public static String generateIndexUrl(String appid,String index_url,String state){
		String redirect_uri = WeixinUtil.urlEncodeUTF8(index_url);
		String url = authorize_url.replace("APPID",appid).replace("REDIRECT_URI",redirect_uri).replace("SCOPE","snsapi_userinfo").replace("STATE",state);
		return url;
	}
}
