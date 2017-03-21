package com.ruiger.modle.business;

import org.springframework.data.annotation.Id;

/**
 * @author 睿哥
 * @version 1.0
 * @time 10:47
 * @description # no  和数量形成键值对
 * @since 2017/03/20
 */
public class UgirlNum {

	@Id
	private String id;

	private String no;

	private Integer count;

	private String title;

	private String url;

	private boolean sign;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSign() {
		return sign;
	}

	public void setSign(boolean sign) {
		this.sign = sign;
	}
}
