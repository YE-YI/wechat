package com.ruiger.modle.business;

import org.springframework.data.annotation.Id;

/**
 * @author 睿哥
 * @version 1.0
 * @time 17:04
 * @description #
 * @since 2017/03/21
 */
public class CarPlate {

	@Id
	private String id;

	//标题
	private String title;

	//车牌号
	private String keyWord;

	//链接
	private String link;

	//密码
	private String password;

	//申明 描述
	private String description;

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

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
