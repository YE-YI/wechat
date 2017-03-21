package com.ruiger.controller;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 睿哥
 * @version 1.0
 * @time 15:59
 * @description # 破解图片防盗链
 * @since 2017/03/20
 */
@Controller
public class RefererController {

	private Logger logger = Logger.getLogger(RefererController.class);

	@RequestMapping(value ="/referer")
	public void referer(HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("防盗链图片地址转接");
			String picPath = request.getQueryString();
			URL url = new URL(picPath);
			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			response.setContentType("image/jpg"); //设置返回的文件类型
			response.setHeader("Access-Control-Allow-Origin", "*");//设置该图片允许跨域访问
			IOUtils.copy(inStream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

