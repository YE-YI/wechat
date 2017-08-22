package com.ruiger.service.weixinUser.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruiger.modle.SNSUserInfo;
import com.ruiger.modle.WeixinUserInfo;
import com.ruiger.service.weixinUser.UserInfoService;
import com.ruiger.util.WeixinUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 睿哥
 * @version 1.0
 * @time 15:29
 * @description #
 * @since 2017/03/14
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	private static Logger logger = Logger.getLogger(UserInfoServiceImpl.class);

	public static String userUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
	public static String snsUserUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	public static String user_get_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	/**
	 * 获取用户信息
	 *
	 * @param accessToken 接口访问凭证
	 * @param openid      用户标识
	 * @return
	 */
	@Override
	public WeixinUserInfo getUserInfo(String accessToken, String openid) {
		WeixinUserInfo weixinUserInfo = null;
		String url = userUrl.replace("ACCESS_TOKEN",accessToken).replace("OPENID",openid);
		JSONObject jsonObject = WeixinUtil.httpRequest(url,"GET",null);
		if(null !=jsonObject && !jsonObject.containsKey("errcode")){
			try {
				weixinUserInfo = new WeixinUserInfo();
				// 用户的标识
				weixinUserInfo.setOpenId(jsonObject.getString("openid"));
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				weixinUserInfo.setSubscribe(jsonObject.getIntValue("subscribe"));
				// 用户关注时间
				weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
				// 昵称
				weixinUserInfo.setNickName(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfo.setSex(jsonObject.getIntValue("sex"));
				// 用户所在国家
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				weixinUserInfo.setCity(jsonObject.getString("city"));
				// 用户的语言，简体中文为zh_CN
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				// 用户头像
				weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
			}catch (Exception e){
				if(0 == weixinUserInfo.getSubscribe()){
					logger.error("用户已取消关注"+ weixinUserInfo.getOpenId());
				}else {
					int errorCode = jsonObject.getIntValue("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					logger.error("获取用户信息失败 errcode:"+ errorCode + " errmsg:"+ errorMsg);
				}
			}
		}else {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			logger.error("获取用户信息失败 errcode:"+ errorCode + " errmsg:"+ errorMsg);
		}
		return weixinUserInfo;
	}

	/**
	 * 通过页面授权获取用户信息
	 *
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	@Override
	public SNSUserInfo getSNSUserInfo(String accessToken, String openid) {
		SNSUserInfo snsUserInfo = null;
		String requestUrl = snsUserUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
		// 通过网页授权获取用户信息
		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);

		if (null != jsonObject && !jsonObject.containsKey("errcode")) {
			try {
				snsUserInfo = new SNSUserInfo();
				// 用户的标识
				snsUserInfo.setOpenId(jsonObject.getString("openid"));
				// 昵称
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				// 性别（1是男性，2是女性，0是未知）
				snsUserInfo.setSex(jsonObject.getIntValue("sex"));
				// 用户所在国家
				snsUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				snsUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				snsUserInfo.setCity(jsonObject.getString("city"));
				// 用户头像
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// 用户特权信息
				snsUserInfo.setPrivilegeList((List<String>) jsonObject.get("privilege"));
			} catch (Exception e) {
				snsUserInfo = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				logger.error("获取用户信息失败 errcode:" + errorCode + "errmsg:"+ errorMsg);
			}
		}else{
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			logger.error("获取用户信息失败 errcode:" + errorCode + "errmsg:"+ errorMsg);
		}
		return snsUserInfo;
	}

	/**
	 * 获取所有关注用户id   每次最多获取1w个
	 *
	 * @param accessToken
	 * @return {"total":2,"count":2,"data":{"openid":["","OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"}
	 * {"errcode":40013,"errmsg":"invalid appid"}
	 */
	@Override
	public JSONObject getUserInfos(String accessToken) {
		String head_url = user_get_url.replace("ACCESS_TOKEN",accessToken);
		String first_url = head_url.replace("NEXT_OPENID","");
		JSONObject jsonObject = WeixinUtil.httpRequest(first_url, "GET", null);
		if(null != jsonObject){
			if(!jsonObject.containsKey("errcode")){
				return jsonObject;
			}else {
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				logger.error("获取用户id失败 errcode:" + errorCode + "errmsg:"+ errorMsg);
			}
		}else {
			logger.error("获取用户id失败");
		}
		return null;
	}


}
