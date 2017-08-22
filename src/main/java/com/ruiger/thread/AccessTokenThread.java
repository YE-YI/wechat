package com.ruiger.thread;

import com.ruiger.constant.Constant;
import com.ruiger.modle.AccessToken;
import com.ruiger.modle.JsApiTicket;
import com.ruiger.util.WeixinUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 睿哥
 * @version 1.0
 * @time 16:21
 * @description #定时获取微信access_token的线程
 * 每7200秒执行一次  token有效期
 * @since 2017/03/10
 */
@Component
public class AccessTokenThread {
	private static Logger logger = Logger.getLogger(AccessTokenThread.class);


	public static AccessToken accessToken = null;

	public static JsApiTicket jsApiTicket = null;

	@Autowired
	private Constant constant;

	@Scheduled(fixedDelay = 2*3600*1000)
	//7200秒执行一次
	public void getAccessToken(){
		accessToken= WeixinUtil.getAccessToken(constant.getAppid(),constant.getAppsecret());
		if(null!=accessToken){
			logger.info("获取成功，accessToken:"+accessToken.getToken());
			jsApiTicket = WeixinUtil.getJsApiTicket(accessToken.getToken());
			if(null != jsApiTicket){
				logger.info("获取成功，JSApiTicket:" + jsApiTicket.getTicket());
			}else {
				logger.error("获取ticket失败");
			}
		}else {
			logger.error("获取token失败");
		}
	}

}
