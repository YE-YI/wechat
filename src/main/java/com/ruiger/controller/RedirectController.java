package com.ruiger.controller;

import com.ruiger.util.WxUrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 睿哥
 * @version 1.0
 * @time 10:04
 * @description # 重定向
 * @since 2017/03/17
 */
@Controller
@RequestMapping("/redirect")
public class RedirectController {

	@Value("${com.ruiger.constant.indexUrl}")
	private  String homePageUrl;

	@Value("${com.ruiger.constant.blog}")
	private String blog_url;

	@Autowired
	private Environment env;

	@RequestMapping("/forward")
	public void frontRecv(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String appid = env.getProperty("com.ruiger.constant.appid");
		String server_url = env.getProperty("server.url");
		String random_state = env.getProperty("com.ruiger.value");
		String redirect_url = WxUrlUtil.generateIndexUrl(appid,server_url +"wx/home.html",random_state);
		System.out.print("转发地址为：" + redirect_url);
		response.sendRedirect(redirect_url);
	}
}
