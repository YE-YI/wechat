package com.ruiger.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruiger.constant.Constant;
import com.ruiger.modle.menu.Menu;
import com.ruiger.service.menu.MenuService;
import com.ruiger.thread.AccessTokenThread;
import com.ruiger.util.WeixinUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 睿哥
 * @version 1.0
 * @time 16:25
 * @description #公众号自定义菜单
 * @since 2017/03/10
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

	private static Logger logger = Logger.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;

	@Autowired
	private Environment env;

	/**
	 * 获取所有菜单
	 * @return
	 */
	@RequestMapping(value="/get",method = RequestMethod.GET)
	public String getMenu(){
		//获取token
		String token = AccessTokenThread.accessToken.getToken();
		JSONObject jsonObject = null;
		if(token != null){
			jsonObject = menuService.getMenu(token);
			return jsonObject.toString();
		}
		return null;
	}

	/**
	 * 创建菜单
	 * @return
	 */
	@RequestMapping(value = "/create",method = RequestMethod.POST)
	public int createMenu(){
		String token = AccessTokenThread.accessToken.getToken();
//		String token = WeixinUtil.getAccessToken(constant.getAppid(),constant.getAppsecret()).getToken();
		int result = 0;
		if(token != null){
			result = menuService.createMenu(getDefaultMenu(),token);
			if(0 == result){
				logger.info("菜单创建成功");
			}else {
				logger.info("菜单创建失败，【" + result + "】" );
			}
		}
		return result;
	}

	//删除菜单
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public int deleteMenu() {
		// 调用接口获取access_token
		String at = AccessTokenThread.accessToken.getToken();
		int result=0;
		if (at != null) {
			// 删除菜单
			result = menuService.deleteMenu(at);
			// 判断菜单删除结果
			if (0 == result) {
				logger.info("菜单删除成功！");
			} else {
				logger.info("菜单删除失败，【" + result + "】");
			}
		}
		return  result;
	}


	/**
	 * 组装菜单数据
	 */
	public  Map<String, Object> getDefaultMenu(){
		Map<String ,Object> wechatMenuMap = new HashMap<>();
		//第一栏菜单
		Menu menu1=new Menu();
		menu1.setId("1");
		menu1.setName("资源");
		menu1.setType("click");
		menu1.setKey("1");

		Menu menu11=new Menu();
		menu11.setId("11");
		menu11.setName("欧美");
		menu11.setType("click");
		menu11.setKey("11");

		Menu menu12=new Menu();
		menu12.setId("12");
		menu12.setName("国产");
		menu12.setType("click");
		menu12.setKey("12");

		Menu menu13=new Menu();
		menu13.setId("13");
		menu13.setName("日韩");
		menu13.setType("click");
		menu13.setKey("13");
		//第二栏
		Menu menu2=new Menu();
		menu2.setId("2");
		menu2.setName("网红");
		menu2.setType("click");
		menu2.setKey("2");

		Menu menu21=new Menu();
		menu21.setId("21");
		menu21.setName("主播");
		menu21.setType("click");
		menu21.setKey("21");



		Menu menu3=new Menu();
		menu3.setId("3");
		menu3.setName("版权说明");
		menu3.setType("view");
		//生成跳转首页
		String server_url = env.getProperty("server.url");
		menu3.setUrl(server_url + "redirect/forward");


		//包装button的List
		List<Map<String, Object>> wechatMenuMapList = new ArrayList();

		//包装第一栏
		Map<String, Object> menuMap1 = new HashMap();
		Map<String, Object> menuMap11 = new HashMap();
		Map<String, Object> menuMap12 = new HashMap();
		List<Map<String, Object>> subMenuMapList1 = new ArrayList();


		//第一栏第一个
		menuMap11.put("name",menu11.getName());
		menuMap11.put("type",menu11.getType());
		menuMap11.put("key",menu11.getKey());
		subMenuMapList1.add(menuMap11);

		//第二栏第二个
		menuMap12.put("name",menu12.getName());
		menuMap12.put("type",menu12.getType());
		menuMap12.put("key",menu12.getKey());
		subMenuMapList1.add(menuMap12);

		menuMap1.put("name",menu1.getName());
		menuMap1.put("sub_button",subMenuMapList1);

		//包装第二栏
		Map<String, Object> menuMap2 = new HashMap();
		Map<String, Object> menuMap21 = new HashMap();
		List<Map<String, Object>> subMenuMapList2 = new ArrayList();

		//第二栏第一个
		menuMap21.put("name",menu21.getName());
		menuMap21.put("type",menu21.getType());
		menuMap21.put("key",menu21.getKey());
		subMenuMapList2.add(menuMap21);

		menuMap2.put("name",menu2.getName());
		menuMap2.put("sub_button",subMenuMapList2);

		//包装第三栏
		Map<String, Object> menuMap3 = new HashMap();
		List<Map<String, Object>> subMenuMapList3 = new ArrayList();

		menuMap3.put("name",menu3.getName());
		menuMap3.put("type",menu3.getType());
		menuMap3.put("url",menu3.getUrl());
		menuMap3.put("sub_button",subMenuMapList3);


		wechatMenuMapList.add(menuMap1);
		wechatMenuMapList.add(menuMap2);
		wechatMenuMapList.add(menuMap3);
		wechatMenuMap.put("button",wechatMenuMapList);
		return  wechatMenuMap;
	}
}
