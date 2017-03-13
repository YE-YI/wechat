package com.ruiger.modle.message.response;

/**
 * @author 睿哥
 * @version 1.0
 * @time 14:21
 * @description # 音乐类消息
 * @since 2017/03/13
 */
public class MusicMessage extends BaseMessage {
	// 音乐名称
	private String Title;
	// 音乐描述
	private String Description;
	// 音乐链接
	private String MusicUrl;
	// 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	private String HQMusicUrl;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getMusicUrl() {
		return MusicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		MusicUrl = musicUrl;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String HQMusicUrl) {
		this.HQMusicUrl = HQMusicUrl;
	}
}
