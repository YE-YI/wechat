package com.ruiger.service.user;

import com.ruiger.util.ResponseKit;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 睿哥
 * @version 1.0
 * @time 10:44
 * @description #
 * @since 2017/03/27
 */
@Service
public class UserSessionService {
	private static final ResponseKit responseKit = new ResponseKit();
	private static Logger logger = Logger.getLogger(UserSessionService.class);

	/**
	 * 登录验证拦截
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public boolean isLogin(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return true;
	}
}
