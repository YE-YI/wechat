package com.ruiger.service.core;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 睿哥
 * @version 1.0
 * @time 13:43
 * @description #
 * @since 2017/03/09
 */
public interface WechatService {

	/**
	 * 处理微信消息请求
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request);
}
