package com.ruiger.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ruiger.modle.message.common.Data;
import com.ruiger.modle.message.driver.DriverData;
import com.ruiger.modle.message.response.TemplateMessage;
import com.ruiger.thread.AccessTokenThread;
import org.apache.log4j.Logger;

/**
 * @author 睿哥
 * @version 1.0
 * @time 10:52
 * @description # 消息模板工具类
 * @since 2017/03/17
 */
public class TemplateUtil {

	private static Logger logger = Logger.getLogger(TemplateUtil.class);

	private static String template_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";


	/**
	 * 发送消息模板  模板标题颜色固定
	 * @param openid
	 * @param template_id
	 * @param data
	 * @return
	 */
	public static int sendTemplate(String openid,String template_id,Data data){
		String url = template_url.replace("ACCESS_TOKEN", AccessTokenThread.accessToken.getToken());
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTouser(openid);
		templateMessage.setTemplate_id(template_id);
		String link_url = "";
		if(data instanceof DriverData){
			link_url = ((DriverData) data).getDriver_link().getValue();
		}
		templateMessage.setUrl(link_url);
		templateMessage.setTopcolor("#FF0000");
		templateMessage.setData(data);
		String json = JSON.toJSONString(templateMessage, SerializerFeature.WriteNullStringAsEmpty);
		JSONObject jsonObject  = WeixinUtil.httpRequest(url,"POST",json);
		int result = 0;
		if (null != jsonObject){
			if(0!= jsonObject.getIntValue("errcode")){
				result = jsonObject.getIntValue("errcode");
				logger.error("错误 errcode:" + jsonObject.getIntValue("errcode") +" errmsg:"+ jsonObject.getString("errmsg"));
			}
		}
		logger.info("模板消息发送结果" + result);
		return result;
	}
}
