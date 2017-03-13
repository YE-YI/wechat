package com.ruiger.modle.message.request;

/**
 * @author 睿哥
 * @version 1.0
 * @time 13:56
 * @description #音频消息
 * @since 2017/03/09
 */
public class VoiceMessage extends BaseMessage {

	private String MediaId;

	//音频格式
	private String Format;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}
}
