package com.ruiger.modle.message.request;

/**
 * @author 睿哥
 * @version 1.0
 * @time 13:54
 * @description #菜单消息
 * @since 2017/03/09
 */
public class MenuMessage extends BaseMessage {
	private String EvenKey;

	public String getEvenKey() {
		return EvenKey;
	}

	public void setEvenKey(String evenKey) {
		EvenKey = evenKey;
	}
}
