package com.ruiger.modle;

/**
 * @author 睿哥
 * @version 1.0
 * @time 15:00
 * @description #微信用户基本信息
 * @since 2017/03/14
 */
public class WeixinUserInfo {

	//用户标识
	private String openId;
	//关注状态(1:关注 0:未关注) 未关注时获取不到其余信息
	private int subscribe;
	//关注事件 多次关注 获取最后关注事件
	private String subscribeTime;
	//昵称
	private String nickName;
	//用户性别(1男 2女 3未知)
	private int sex;
	//国家
	private String country;
	//省份
	private String province;
	//所在城市
	private String city;
	//用户语言  简体中文为zh_CN
	private String language;
	//用户头像
	private String headImgUrl;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
}
