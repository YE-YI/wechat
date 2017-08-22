package com.ruiger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.ruiger.dao.UgirlNumRepository;
import com.ruiger.dao.UgirlRepository;
import com.ruiger.modle.business.Ugirls;
import com.ruiger.modle.business.UgirlNum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/business/ugirl")
public class UgirlController {

	private Logger logger = Logger.getLogger(UgirlController.class);

	@Autowired
	private UgirlRepository ugirlRepository;

	@Autowired
	private UgirlNumRepository ugirlNumRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private Environment env;

	@RequestMapping("/groupInit")
	public String groupSearch() {
		GroupBy groupBy = GroupBy.key("no","title").initialDocument("{count:0,url:''}")
				.reduceFunction("function(obj, values){values.count+=1;values.url=obj.image_urls}");
		GroupByResults<Ugirls> r = mongoTemplate.group("ugirls", groupBy, Ugirls.class);
		BasicDBList list1 = (BasicDBList) r.getRawResults().get("retval");

		for(Object dbObject:list1){
			UgirlNum num = new UgirlNum();
			num.setTitle((String) ((BasicDBObject) dbObject).get("title"));
			num.setNo((String) ((BasicDBObject) dbObject).get("no"));
			num.setUrl((String) ((BasicDBObject) dbObject).get("url"));
			num.setSign(false);
			Integer count = ((Double) ((BasicDBObject) dbObject).get("count")).intValue();
			num.setCount(count);
			//language=JSON
			String json = "{\"name\":\"ruiger\",\"age\":17}";
			mongoTemplate.save(num);
		}
		return JSON.toJSONString(list1);
	}

	@RequestMapping("/group")
	public String group(){
		List<UgirlNum> list = ugirlNumRepository.findBySign(false,new Sort(Sort.Direction.ASC, "no"));
		//图文消息每天限制8个
		List sublist = list.subList(0,7);
		return JSON.toJSONString(sublist);

	}

	@RequestMapping("/view")
	public String imageList(HttpServletRequest request)
			throws IOException {
		String no = request.getParameter("no");

		List<Ugirls> list = ugirlRepository.findByNo(no);

		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			for(Ugirls ugirls :list){
				ugirls.setImage_urls(env.getProperty("server.url") + "referer?" + ugirls.getImage_urls());
			}
			JSONObject object = new JSONObject();
			object.put("list", list);
			String json = JSON.toJSONString(object);
			json = json.replace("\\\\", "");
			logger.info("尤果网：" + json);
			return json;
		}
	}

}
