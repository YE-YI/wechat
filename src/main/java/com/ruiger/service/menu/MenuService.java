package com.ruiger.service.menu;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author 睿哥
 * @version 1.0
 * @time 16:08
 * @description #
 * @since 2017/03/10
 */
public interface MenuService {

	/**
	 * 获取菜单
	 * @param accessToken
	 * @return
	 */
	public JSONObject getMenu(String accessToken);

	/**
	 * 创建菜单
	 * @param menu
	 * @param accessToken
	 * @return
	 */
	public int createMenu(Map<String,Object> menu, String accessToken);

	/**
	 * 删除菜单
	 * @param accessToken
	 * @return
	 */
	public int deleteMenu(String accessToken);
}
