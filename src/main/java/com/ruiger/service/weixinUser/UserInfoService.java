package com.ruiger.service.weixinUser;

import com.ruiger.modle.SNSUserInfo;
import com.ruiger.modle.WeixinUserInfo;

/**
 * @author 睿哥
 * @version 1.0
 * @time 15:28
 * @description #
 * @since 2017/03/14
 */
public interface UserInfoService {

	/**
	 * 获取用户信息
	 * @param accessToken  接口访问凭证
	 * @param openid 用户标识
	 * @return
	 */
	public WeixinUserInfo getUserInfo(String accessToken, String openid);


	/**
	 * 通过页面授权获取用户信息
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	@SuppressWarnings({"deprecation","unchecked"})
	public SNSUserInfo getSNSUserInfo(String accessToken,String openid);

}
