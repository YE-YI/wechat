package com.ruiger.modle.business;

import org.springframework.data.annotation.Id;

/**
 * @author 睿哥
 * @version 1.0
 * @time 19:42
 * @description #
 * @since 2017/03/19
 */

public class Ugirls {

	@Id
	private String id;

	private String title;

	private String image_urls;

	private String no;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage_urls() {
		return image_urls;
	}

	public void setImage_urls(String image_urls) {
		this.image_urls = image_urls;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
}
