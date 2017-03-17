package com.ruiger.modle;

/**
 * @author 睿哥
 * @version 1.0
 * @time 10:32
 * @description # 微信js 凭证
 * @since 2017/03/16
 */
public class JsApiTicket {

	private int errcode;

	private String errmsg;

	private String ticket;

	private int expires_in;

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}
