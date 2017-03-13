package com.ruiger.modle.message.request;

/**
 * @author 睿哥
 * @version 1.0
 * @time 13:49
 * @description #图片消息
 * @since 2017/03/09
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
}
