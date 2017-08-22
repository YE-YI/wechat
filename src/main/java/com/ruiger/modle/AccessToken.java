package com.ruiger.modle;

/**
 * @author 睿哥
 * @version 1.0
 * @time 15:56
 * @description #微信通用接口凭证
 * @since 2017/03/10
 */
public class AccessToken {
	//凭证
	private String token;
	//有效期 秒
	private int expiresIn;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
