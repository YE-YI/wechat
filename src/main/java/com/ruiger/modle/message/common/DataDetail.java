package com.ruiger.modle.message.common;

/**
 * @author 睿哥
 * @version 1.0
 * @time 10:38
 * @description # 用于消息模板内容详细属性基类
 * @since 2017/03/17
 */
public class DataDetail {

	protected String value;


	protected String color;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
