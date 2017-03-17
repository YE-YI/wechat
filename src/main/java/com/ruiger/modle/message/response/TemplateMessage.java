package com.ruiger.modle.message.response;


import com.ruiger.modle.message.common.Data;

/**
 * @author 睿哥
 * @version 1.0
 * @time 10:30
 * @description # 消息模板
 * @since 2017/03/17
 */
public class TemplateMessage {

	private  String touser ;

	private String template_id;
	//请注意，URL置空，则在发送后，点击模板消息会进入一个空白页面（ios），或无法点击（android）。
	private String url;

	//标题颜色
	private String topcolor;

	//详细内容
	private Data data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
