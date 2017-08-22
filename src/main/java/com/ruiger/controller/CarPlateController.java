package com.ruiger.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruiger.dao.CarPlateRepository;
import com.ruiger.modle.business.CarPlate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 睿哥
 * @version 1.0
 * @time 17:09
 * @description # 处理车牌资源
 * @since 2017/03/21
 */

@RestController
@RequestMapping("/carPlate")
public class CarPlateController {

	@Autowired
	private CarPlateRepository carPlateRepository;

	/**
	 * 添加车牌
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/add")
	public String add(HttpServletRequest request, HttpServletResponse response){
		String title = request.getParameter("title");
		String keyWord = request.getParameter("keyWord");
		String link = request.getParameter("link");
		String password = request.getParameter("password");
		String description = request.getParameter("description");
		//定义返回json
		int errCode = 0 ;// 默认0成功
		String msg = "添加车牌资源成功";

		//根据keyWord 轮询数据库
		CarPlate carPlate = carPlateRepository.findByKeyWord(keyWord);
		if(carPlate != null){
			errCode = -1;
			msg = "车牌【" + keyWord + "】" + "资源已存在，请套牌后重新添加";
		}else {
			carPlate = new CarPlate();
			carPlate.setTitle(title);
			carPlate.setKeyWord(keyWord);
			carPlate.setLink(link);
			carPlate.setPassword(password);
			carPlate.setDescription(description);
			try{
				carPlateRepository.save(carPlate);
			}catch (Exception e){
				errCode = -2;
				msg = "车牌【" + keyWord + "】" + "添加失败";
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("errCode",errCode);
		jsonObject.put("msg",msg);
		return jsonObject.toString();
	}
}
