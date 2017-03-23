package com.ruiger.service.core.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruiger.dao.CarPlateRepository;
import com.ruiger.modle.business.CarPlate;
import com.ruiger.modle.business.UgirlNum;
import com.ruiger.modle.message.common.Data;
import com.ruiger.modle.message.response.Article;
import com.ruiger.modle.message.response.NewsMessage;
import com.ruiger.modle.message.response.TextMessage;
import com.ruiger.service.business.UgirlService;
import com.ruiger.service.core.WechatService;
import com.ruiger.service.message.MessageService;
import com.ruiger.service.todayinhistory.TodayInHistory;
import com.ruiger.util.HttpUtil;
import com.ruiger.util.MessageUtil;
import com.ruiger.util.TemplateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 睿哥
 * @version 1.0
 * @time 13:43
 * @description #
 * @since 2017/03/09
 */
@Service
public class WechatServiceImpl implements WechatService {

	private static Logger log = Logger.getLogger(WechatServiceImpl.class);

	@Autowired
	private MessageService messageService;

	@Autowired
	private UgirlService ugirlService;

	@Autowired
	private CarPlateRepository carPlateRepository;

	@Autowired
	private Environment env;


	/**
	 * 处理微信消息请求
	 *
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		String server = env.getProperty("server.url");

		try {
			//默认消息返回
			String respContent = "哎呀，我的个乖乖处理不过来请求了，请稍后尝试！:-D";

			//处理解析xml
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			//发送方账号
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 创建图文消息
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);

			List<Article> articleList = new ArrayList<Article>();
			// 接收文本消息内容
			String content = requestMap.get("Content");
			//点击菜单id
			String eventKey = requestMap.get("EventKey");
			// 自动回复文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {

				//如果用户发送表情，则回复同样表情。
				if (isQqFace(content)) {
					respContent = content;
					textMessage.setContent(respContent);
					// 将文本消息对象转换成xml字符串
					respMessage = MessageUtil.textMessageToXml(textMessage);
				} else {
					//回复固定消息
					CarPlate carPlate = carPlateRepository.findByKeyWord(StringUtils.isEmpty(content) ? "" : content.trim());
					if (carPlate != null) {
						Data data = messageService.assembleDirver(carPlate);
						String template_id = env.getProperty("com.ruiger.constant.templateid.driverid");
						int code = TemplateUtil.sendTemplate(fromUserName, template_id, data);
						if (code == 0) {
							respMessage = "";
						} else {
							textMessage.setContent(respContent);
							// 将文本消息对象转换成xml字符串
							respMessage = MessageUtil.textMessageToXml(textMessage);
						}
					} else if (!StringUtils.isEmpty(content) && content.contains("盼盼套图")) {
						//直接跳到网页
						String key = content.replace("盼盼套图", "");
						String url = server + "business/meituzz/keyUrl?key=" + key;
						content = HttpUtil.executeUrl(url, HttpUtil.HTTP_POST);
						Article article = new Article();
						article.setTitle("闫盼盼作品" + key);
						// 图文消息中可以使用QQ表情、符号表情
						article.setDescription("");
						// 将图片置为空
						article.setPicUrl(content.replace("\\", ""));
						article.setUrl(server + "wx/meitu.html?key=" + key);
						articleList.add(article);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
					} else {
						switch (content) {

							case "0": {
								StringBuffer buffer = new StringBuffer();
								buffer.append("您好，我是睿哥哥，请回复数字选择服务：").append("\n\n");
								buffer.append("1 历史上的今天").append("\n");
								buffer.append("11 可查看测试单图文").append("\n");
								buffer.append("12  可测试多图文发送").append("\n");
								buffer.append("13  可测试网址").append("\n");
								buffer.append("笑话  来福岛随机5个笑话").append("\n");
								buffer.append("搞笑  来福岛随机8个搞笑图片").append("\n");
								buffer.append("学车 老司机教你怎么把车飚起来").append("\n\n");
								buffer.append("或者您可以尝试发送表情").append("\n\n");
								buffer.append("回复“0”显示此帮助菜单").append("\n");
								respContent = String.valueOf(buffer);
								textMessage.setContent(respContent);
								respMessage = MessageUtil.textMessageToXml(textMessage);
								break;
							}
							case "1": {
//								TodayInHistory.getTodayInHistoryInfo();
								respContent = String.valueOf(TodayInHistory.getTodayInHistoryInfo());
								textMessage.setContent(respContent);
								respMessage = MessageUtil.textMessageToXml(textMessage);
								break;
							}
							case "11": {
								//测试单图文回复
								Article article = new Article();
								article.setTitle("微信公众帐号开发教程Java版");
								// 图文消息中可以使用QQ表情、符号表情
								article.setDescription("这是测试有没有换行\n\n如果有空行就代表换行成功\n\n点击图文可以跳转到新浪微博首页");
								// 将图片置为空
								article.setPicUrl("http://wx1.sinaimg.cn/orj360/006yhpkYly1fdkc0ce43vj30dc0dct90.jpg");
								article.setUrl("http://www.weibo.com");
								articleList.add(article);
								newsMessage.setArticleCount(articleList.size());
								newsMessage.setArticles(articleList);
								respMessage = MessageUtil.newsMessageToXml(newsMessage);
								break;
							}
							case "12": {
								//多图文发送
								Article article1 = new Article();
								article1.setTitle("幼儿园老师让带肉肉 萌童真的带来块肉！\n");
								article1.setDescription("");
								article1.setPicUrl("http://wx1.sinaimg.cn/orj360/006yhpkYly1fdkc0ce43vj30dc0dct90.jpg");
								article1.setUrl("https://zx.sina.cn/sh/2017-03-12/zx-ifychhus0841976.d.html?from=qudao");

								Article article2 = new Article();
								article2.setTitle("福州一男子头插水果刀，仍淡定排队就诊！");
								article2.setDescription("");
								article2.setPicUrl("https://ns.sinaimg.cn/default/transform/20170313/IqZy-fychhvn8559293.jpg");
								article2.setUrl("https://zx.sina.cn/sh/2017-03-12/zx-ifychhus0835841.d.html?from=qudao");

								Article article3 = new Article();
								article3.setTitle("大家欢送董事长下台");
								article3.setDescription("公司管理大会，我做主持，董事长洋溢的发言后，\n\n　　我说，大家欢送董事长下台。\n\n　　然后，...我到现在还没上班呢。");
								article3.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
								article1.setUrl("http://www.laifudao.com/wangwen/77360.htm");

								articleList.add(article1);
								articleList.add(article2);
								articleList.add(article3);
								newsMessage.setArticleCount(articleList.size());
								newsMessage.setArticles(articleList);
								respMessage = MessageUtil.newsMessageToXml(newsMessage);
								break;
							}

							case "13": {
								//测试网址回复
								respContent = "<a href=\"http://www.baidu.com\">百度主页</a>";
								textMessage.setContent(respContent);
								// 将文本消息对象转换成xml字符串
								respMessage = MessageUtil.textMessageToXml(textMessage);
								break;
							}

							case "笑话": {
								//调用来福岛api
								String url = "http://api.laifudao.com/open/xiaohua.json";
								content = HttpUtil.executeUrl(url, HttpUtil.HTTP_GET);
								JSONArray jok = JSON.parseArray(content);
								StringBuffer buffer = new StringBuffer();
								Random rand = new Random();
								Set<Integer> set = new HashSet<Integer>();
								while (set.size() < 5)
									set.add(rand.nextInt(20));
								Iterator<Integer> it = set.iterator();
								while (it.hasNext()) {
									JSONObject object = (JSONObject) jok.get(it.next());
									buffer.append(object.get("title")).append("\n\n");
									buffer.append((String) object.get("content")).append("\n\n");

								}
								respContent = String.valueOf(buffer);
								textMessage.setContent(respContent);
								respMessage = MessageUtil.textMessageToXml(textMessage);
								break;
							}

							case "搞笑": {
								//调用来福岛api
								String url = "http://api.laifudao.com/open/tupian.json";
								Random rand = new Random();
								Set<Integer> set = new HashSet<Integer>();
								while (set.size() < 8)
									set.add(rand.nextInt(20));
								content = HttpUtil.executeUrl(url, HttpUtil.HTTP_GET);
								JSONArray jok = JSON.parseArray(content);
								Iterator<Integer> it = set.iterator();
								while (it.hasNext()) {
									JSONObject object = (JSONObject) jok.get(it.next());
									Article article = new Article();
									article.setTitle((String) object.get("title"));
									article.setPicUrl((String) object.get("thumburl"));
									article.setDescription("");
									article.setUrl((String) object.get("url"));
									articleList.add(article);
								}
								newsMessage.setArticleCount(articleList.size());
								newsMessage.setArticles(articleList);
								respMessage = MessageUtil.newsMessageToXml(newsMessage);
								log.info(respMessage);
								break;
							}

							case "学车": {
								respContent = "自己动手丰衣足食，收破烂的GitHub：https://github.com/YE-YI/";
								respContent += "老司机教你开车哦！";
								textMessage.setContent(respContent);
								respMessage = MessageUtil.textMessageToXml(textMessage);
								break;
							}

							case "闫盼盼": {
								String url = server + "business/meituzz/group";
								content = HttpUtil.executeUrl(url, HttpUtil.HTTP_GET);
								JSONArray array = JSON.parseArray(content);
								StringBuilder sb = new StringBuilder("输入‘套图+作品id'查看套图，例如‘盼盼套图1109’").append("\n\n");
								for (int i = 0; i < array.size(); i++) {
									JSONObject object = (JSONObject) array.get(i);
									sb.append("作品id:" + object.get("key") + "【" + object.get("count")
											+ "】").append("\n\n");
								}
								Article article = new Article();
								article.setTitle("闫盼盼系列套图(格式:作品id[数量])");
								// 图文消息中可以使用QQ表情、符号表情
								article.setDescription(sb.toString());
								// 将图片置为空
								article.setPicUrl("http://mmbiz.qpic.cn/mmbiz_jpg/7K7QHjL1YUgkdXdz4GIOzPBNvEia0G3TqRibPC32bZpobP670Ml7bkdod5kHEgYOb4vC1OqqcfkbQKmNnuxlBgmA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1");
								article.setUrl("");
								articleList.add(article);
								newsMessage.setArticleCount(articleList.size());
								newsMessage.setArticles(articleList);
								respMessage = MessageUtil.newsMessageToXml(newsMessage);
								break;
							}

							case "汤福利": {
								respContent = "汤不热福利来袭";
								respContent = "声明：\n" +
										"      Tumblr（中文名：汤博乐）成立于2007年，是目前全球最大的轻博客网站。所有用户皆可免费注册浏览，由于政策问题在国内（中国）暂无法访问，本公众号所发布Tumblr相关内容皆与Tumblr公司无关，是爱好者的经验之谈，用于交流学习。";
								respContent += "\n各位看官请自行google翻墙如何登陆tumblr\n";
								respContent += "福利视频：https://pan.baidu.com/s/1pLnqYDX";
								respContent += "提取密码：unuh" + "\n";
								respContent += "自己动手丰衣足食，收破烂的GitHub：https://github.com/YE-YI/";
								respContent += "老司机教你开车哦！";
								textMessage.setContent(respContent);
								// 将文本消息对象转换成xml字符串
								respMessage = MessageUtil.textMessageToXml(textMessage);
								break;
							}

							case "每日飙车":{
								List<UgirlNum> list = ugirlService.find8Record();
								//图文消息每天限制8个
								for (UgirlNum ugirlNum : list) {
									Article article = new Article();
									article.setTitle(ugirlNum.getTitle());
									article.setPicUrl(ugirlNum.getUrl().replace("\\", ""));
									article.setUrl(server + "wx/ugirl.html?no=" + ugirlNum.getNo());
									articleList.add(article);
								}

								newsMessage.setArticleCount(articleList.size());
								newsMessage.setArticles(articleList);
								respMessage = MessageUtil.newsMessageToXml(newsMessage);
								break;
							}

							default: {
								respContent = "别急，您的需求小编会慢慢看。\n有好的资源可以在 " + server +"/wx/carPlate.html 页面添加至此公众号哦" +
										"\n\n回复“1”显示帮助信息 ";
								textMessage.setContent(respContent);
								// 将文本消息对象转换成xml字符串
								respMessage = MessageUtil.textMessageToXml(textMessage);
							}
						}
					}
				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
				textMessage.setContent(respContent);
				// 将文本消息对象转换成xml字符串
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
				textMessage.setContent(respContent);
				// 将文本消息对象转换成xml字符串
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
				textMessage.setContent(respContent);
				// 将文本消息对象转换成xml字符串
				respMessage = MessageUtil.textMessageToXml(textMessage);

			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
				textMessage.setContent(respContent);
				// 将文本消息对象转换成xml字符串
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 自定义菜单点击事件
				if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					switch (eventKey) {
						case "11": {
							respContent = "老司机开车了，请系好安全带";
							respContent += "摆渡芸链接：https://pan.baidu.com/s/1qXSpYFi";
							respContent += "提取密码：4sur" + "\n";
							respContent += "自己动手丰衣足食，收破烂的GitHub：https://github.com/YE-YI/";
							respContent += "老司机教你开车哦！";
							textMessage.setContent(respContent);
							// 将文本消息对象转换成xml字符串
							respMessage = MessageUtil.textMessageToXml(textMessage);
							break;
						}
						case "12": {
							Data data = messageService.assembleDirver(null);
							String template_id = env.getProperty("com.ruiger.constant.templateid.driverid");
							int code = TemplateUtil.sendTemplate(fromUserName, template_id, data);
							if (code == 0) {
								respMessage = "";
							} else {
								textMessage.setContent(respContent);
								// 将文本消息对象转换成xml字符串
								respMessage = MessageUtil.textMessageToXml(textMessage);
							}
							break;
						}
						case "13": {
							respContent = "老司机开车了，请系好安全带";
							respContent += "摆渡芸链接：https://pan.baidu.com/s/1dFvIDG1";
							respContent += "提取密码：3a99" + "\n";
							respContent += "自己动手丰衣足食，收破烂的GitHub：https://github.com/YE-YI/";
							respContent += "老司机教你开车哦！";
							textMessage.setContent(respContent);
							// 将文本消息对象转换成xml字符串
							respMessage = MessageUtil.textMessageToXml(textMessage);
							break;
						}

						case "14": {
							respContent = "老司机开车了，请系好安全带";
							respContent += "摆渡芸链接：https://pan.baidu.com/s/1gfcbEJT";
							respContent += "提取密码：pfxu";
							textMessage.setContent(respContent);
							// 将文本消息对象转换成xml字符串
							respMessage = MessageUtil.textMessageToXml(textMessage);
							break;
						}

						case "2": {
							List<UgirlNum> list = ugirlService.find8Record();
							//图文消息每天限制8个
							for (UgirlNum ugirlNum : list) {
								Article article = new Article();
								article.setTitle(ugirlNum.getTitle());
								article.setPicUrl(ugirlNum.getUrl().replace("\\", ""));
								article.setUrl(server + "wx/ugirl.html?no=" + ugirlNum.getNo());
								articleList.add(article);
							}

							newsMessage.setArticleCount(articleList.size());
							newsMessage.setArticles(articleList);
							respMessage = MessageUtil.newsMessageToXml(newsMessage);
							break;
						}

						default: {
							log.error("开发者反馈：EventKey值没找到，它是:" + eventKey);
							respContent = "很抱歉，此按键功能正在升级无法使用";
							textMessage.setContent(respContent);
							// 将文本消息对象转换成xml字符串
							respMessage = MessageUtil.textMessageToXml(textMessage);
						}
					}

				} else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
					// 对于点击菜单转网页暂时不做推送
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

	/**
	 * 判断是否是QQ表情
	 *
	 * @param content
	 * @return
	 */
	public static boolean isQqFace(String content) {
		boolean result = false;

		// 判断QQ表情的正则表达式
		String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
		Pattern p = Pattern.compile(qqfaceRegex);
		Matcher m = p.matcher(content);
		if (m.matches()) {
			result = true;
		}
		return result;
	}

}
