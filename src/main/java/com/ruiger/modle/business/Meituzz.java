package com.ruiger.modle.business;

import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 睿哥
 * @version 1.0
 * @time 10:14
 * @description #
 * @since 2016/12/23
 */
@RequestMapping("/meituzz")
public class Meituzz {

	@Id
	private String id;

	private String key;

	private String link;

	public Meituzz(String id, String key, String link) {
		this.id = id;
		this.key = key;
		this.link = link;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
