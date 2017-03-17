package com.ruiger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.ruiger.dao.MeituzzRepository;
import com.ruiger.modle.business.Meituzz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author 睿哥
 * @version 1.0
 * @time 10:00
 * @description #
 * @since 2017/03/16
 */
@RestController
@RequestMapping("/business/meituzz")
public class MeituzzController {

	@Autowired
	private MeituzzRepository meituzzRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping("/group")
	public String group() {
		GroupBy groupBy = GroupBy.key("key").initialDocument("{count:0}")
				.reduceFunction("function(key, values){values.count+=1;}");
		GroupByResults<Meituzz> r = mongoTemplate.group("meituzz", groupBy, Meituzz.class);
		BasicDBList list1 = (BasicDBList) r.getRawResults().get("retval");
		System.out.print(JSON.toJSONString(list1));
		return JSON.toJSONString(list1);
	}

	@RequestMapping("/keyUrl")
	public String fristImageUrl(HttpServletRequest request) {
		Integer key = Integer.valueOf(request.getParameter("key"));
		if (StringUtils.isEmpty(key)) {
			key = 52;
		}
		List<Meituzz> list = meituzzRepository.findByKey(key);

		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list.get(0).getLink().replace("\\\\", "");
		}
	}

	@RequestMapping("/view")
	public String imageList(HttpServletRequest request)
			throws IOException {
		String key_str = request.getParameter("key");
		Integer key ;
		if (StringUtils.isEmpty(key_str)) {
			key = 52;
		}else {
			key = Integer.parseInt(key_str);
		}
		List<Meituzz> list = meituzzRepository.findByKey(key);

		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			JSONObject object = new JSONObject();
			object.put("list", list);
			String json = JSON.toJSONString(object);
			json = json.replace("\\\\", "");
			return json;
		}
	}
}
