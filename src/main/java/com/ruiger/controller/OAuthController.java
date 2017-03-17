package com.ruiger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruiger.constant.Constant;
import com.ruiger.modle.SNSUserInfo;
import com.ruiger.modle.WeixinOauth2Token;
import com.ruiger.service.weixinUser.UserInfoService;
import com.ruiger.util.WeixinUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 睿哥
 * @version 1.0
 * @time 16:35
 * @description #
 * @since 2017/03/14
 */
@RestController
@RequestMapping("/oauthServlet")
public class OAuthController {
	private static Logger logger = Logger.getLogger(OAuthController.class);

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private Constant constant;

	@RequestMapping(value = "",method = RequestMethod.POST)
	public String checkSignature(HttpServletRequest request, HttpServletResponse response){
		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		logger.info("用户同意授权【" + code + "】");
		JSONObject result = null;
		// 用户同意授权
		if (!"authdeny".equals(code)) {
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = WeixinUtil.getOauth2Token(constant.getAppid(), constant.getAppsecret(), code);
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();
			// 获取用户信息
			SNSUserInfo snsUserInfo = userInfoService.getSNSUserInfo(accessToken, openId);
			logger.info("用户信息【" + JSON.toJSONString(snsUserInfo) + "】");
			// 设置要传递的参数
			result =  new JSONObject();
			result.put("snsUserInfo", snsUserInfo);
			result.put("state", state);
		}
		return result != null?result.toString():"";
	}
}
