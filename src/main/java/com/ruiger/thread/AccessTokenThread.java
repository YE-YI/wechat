package com.ruiger.thread;

import com.ruiger.modle.AccessToken;
import com.ruiger.util.WeixinUtil;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author 睿哥
 * @version 1.0
 * @time 16:21
 * @description #定时获取微信access_token的线程
 * 每7200秒执行一次  token有效期
 * @since 2017/03/10
 */
public class AccessTokenThread {
	private static Logger logger = Logger.getLogger(AccessTokenThread.class);


	private static String appid = "Dwxab65f426dba3a985";

	private static String  appsecret ="99bccf3b726b5c78f27bbfda0990b24a";

	public static AccessToken accessToken = null;

	@Scheduled(fixedDelay = 2*3600*1000)
	//7200秒执行一次
	public void getAccessToken(){
		accessToken= WeixinUtil.getAccessToken(appid,appsecret);
		if(null!=accessToken){
			logger.info("获取成功，accessToken:"+accessToken.getToken());
		}else {
			logger.error("获取token失败");
		}
	}
}
