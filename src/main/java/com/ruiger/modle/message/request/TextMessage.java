package com.ruiger.modle.message.request;

/**
 * @author 睿哥
 * @version 1.0
 * @time 13:55
 * @description #文本消息
 * @since 2017/03/09
 */
public class TextMessage extends BaseMessage {
	//消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
