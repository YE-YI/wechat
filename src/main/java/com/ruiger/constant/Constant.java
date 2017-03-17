package com.ruiger.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 睿哥
 * @version 1.0
 * @time 18:03
 * @description #
 * @since 2017/03/14
 */
@Component
public class Constant {
	@Value("${com.ruiger.constant.appid}")
	private String appid ;

	@Value("${com.ruiger.constant.appsecret}")
	private String appsecret;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
}
