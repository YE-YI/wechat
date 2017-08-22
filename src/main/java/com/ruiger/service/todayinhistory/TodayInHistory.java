package com.ruiger.service.todayinhistory;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import com.ruiger.util.HttpUtil;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author 睿哥
 * @version 1.0
 * @time 12:50
 * @description # 历史上的今天
 * @since 2017/03/13
 */
public class TodayInHistory {

	private static Logger logger = Logger.getLogger(TodayInHistory.class);

	/**
	 * 获取历史上的今天数据
	 * 爬取网站 http://www.lssdjt.com/
	 * @return
	 */
	public static String getTodayInHistoryInfo() throws Exception {
		//获取网页源代码
		String html = HttpUtil.executeUrl("http://www.lssdjt.com/",HttpUtil.HTTP_GET);
		// 从网页中抽取数据信息
		String result = extract(html);
		logger.info(result.getBytes().length);
		return result;
	}

	private static String extract(String html) throws XpathSyntaxErrorException {
		String dateTag = getMonthDay(0);
		StringBuilder sb = new StringBuilder("≡≡ 历史上的" + dateTag + " ≡≡").append("\n\n");
		String xpath = "//ul[@class='list clearfix']/li/a/allText()";
		JXDocument jxDocument = new JXDocument(html);
		List<Object> rs = jxDocument.sel(xpath);
		int lastSecondIndex = 0;
		for(Object o:rs){
			logger.info(o.toString());
			int length = sb.toString().getBytes().length;
			lastSecondIndex = sb.length();
			if(length > 2000){
				//超过长度限制 跳出
				logger.info("超过长度限制" + length);
				break;
			}
			//去掉每一条历史里的dateTag日期
			sb.append(o.toString().replace(dateTag,"")).append("\n\n");
		}
		//如果长度还是超过限制 截取倒数第二个换行
		if (sb.toString().getBytes().length > 2048) {
			return sb.substring(0, lastSecondIndex) + "...";
		}

		return sb ==null ? null:sb.substring(0, sb.lastIndexOf("\n\n"));
	}

	/**
	 * 获取前/后n天日期(M月d日)
	 *
	 * @return
	 */
	private static String getMonthDay(int diff) {
		DateFormat df = new SimpleDateFormat("MM月dd日");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, diff);
		return df.format(c.getTime());
	}

}
