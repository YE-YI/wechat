package com.ruiger.controller;

import com.ruiger.service.core.WechatService;
import com.ruiger.util.SignUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 睿哥
 * @version 1.0
 * @time 11:26
 * @description #
 * @since 2017/03/09
 */
@RestController
@RequestMapping("")
public class WechatController {
	private static Logger logger = Logger.getLogger(WechatController.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private WechatService wechatService;

	@RequestMapping(value = "",method = RequestMethod.GET)
	public String checkSignature(){
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String signature  = request.getParameter("signature");
		String echostr  = request.getParameter("echostr");
		if(SignUtil.checkSignature(signature,timestamp,nonce)){
			logger.info("他妈的不是成功了么，一直都失败艹!");
			return echostr;
		}
		return "";
	}

	//调用核心业务接收消息、处理并推送笑死
	@RequestMapping(value ="",method= RequestMethod.POST)
	public String post(){
		String message = wechatService.processRequest(request);
		logger.info("转发消息!"+message);
		return message;
	}

	@RequestMapping("/test")
	public String test(){
		return "Hello World";
	}

}
