package com.ruiger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ruiger.constant.Constant;
import com.ruiger.service.core.WechatService;
import com.ruiger.util.SignUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.RequestWrapper;
import java.util.Map;
import java.util.UUID;

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

	@Autowired
	private Constant constant;

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


	@RequestMapping(value="/getWxConfig")
	public String wxConfig(){
		String page_url = request.getParameter("page_url");
		String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳
		String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串
		Map configMap = SignUtil.getWxConfig(constant.getAppid(),nonceStr,timestamp,page_url);
		return JSON.toJSONString(configMap);
	}


	@RequestMapping("/test")
	public String test(){
		return "Hello World";
	}

}
