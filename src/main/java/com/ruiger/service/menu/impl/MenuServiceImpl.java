package com.ruiger.service.menu.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ruiger.service.menu.MenuService;
import com.ruiger.util.WeixinUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 睿哥
 * @version 1.0
 * @time 16:08
 * @description #
 * @since 2017/03/10
 */
@Service
public class MenuServiceImpl implements MenuService {

	private static Logger logger = Logger.getLogger(MenuServiceImpl.class);

	// 菜单创建（POST） 限1000（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// 菜单查询（POST） 限10000（次/天）
	public static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	// 菜单删除（POST） 限1000（次/天）
	public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 * 获取菜单
	 *
	 * @param accessToken
	 * @return
	 */
	@Override
	public JSONObject getMenu(String accessToken) {
		String url = menu_get_url.replace("ACCESS_TOKEN",accessToken);
		JSONObject jsonObject = WeixinUtil.httpRequest(url,"GET",null);
		return jsonObject;
	}

	/**
	 * 创建菜单
	 *
	 * @param menu
	 * @param accessToken
	 * @return
	 */
	@Override
	public int createMenu(Map<String, Object> menu, String accessToken) {
		int result = 0;

		String url = menu_create_url.replace("ACCESS_TOKEN",accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSON.toJSONString(menu, SerializerFeature.WriteNullStringAsEmpty);
		// 调用接口创建菜单
		JSONObject jsonObject = WeixinUtil.httpRequest(url,"POST",jsonMenu);
		if (null != jsonObject) {
			if (0 != jsonObject.getIntValue("errcode")) {
				result = jsonObject.getIntValue("errcode");
				logger.error("创建菜单失败 errcode:{} errmsg:{}"+ jsonObject.getIntValue("errcode")+ jsonObject.getString("errmsg"));
				logger.error("****"+jsonMenu+"****");
			}
		}
		return result;
	}

	/**
	 * 删除菜单
	 *
	 * @param accessToken
	 * @return
	 */
	@Override
	public int deleteMenu(String accessToken) {
		int result = 0;
		String url = menu_delete_url.replace("ACCESS_TOKEN",accessToken);
		JSONObject jsonObject = WeixinUtil.httpRequest(url,"GET","");
		if (null != jsonObject) {
			if (0 != jsonObject.getIntValue("errcode")) {
				result = jsonObject.getIntValue("errcode");
				logger.error("删除菜单失败 errcode:{} errmsg:{}"+ jsonObject.getIntValue("errcode")+ jsonObject.getString("errmsg"));
			}
		}
		return result;
	}
}
