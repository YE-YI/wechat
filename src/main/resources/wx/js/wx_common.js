// 测试环境（）
var server = "http://ruiger.ngrok.cc/";

var agentName = "ruigerAgent";
var OpenidAgent = "OpenidAgent";
var judgeAgent = "judgeAgent";
var pageName = "pageurl";
var openId = window.localStorage.getItem("openid");
var random = window.localStorage.getItem("random");

// document.write("<script language='javascript' src='/webjarslocator/jquery-i18n-properties/jquery-i18n-properties.js'></script>");
//保存userAgent
function wxSaveUserAgent(uid, openid, token) {
	if(window.localStorage.getItem(agentName)){
		window.localStorage.removeItem(agentName);
	}
	var userAgent = "{\"uid\":\"" + uid + "\",\"openid\":\"" + openid + "\",\"token\":\"" + token + "\"}";
	window.localStorage.setItem(agentName, userAgent);
	return userAgent;
}

//保存openid
function saveAgentOpenid(openid){
	if(window.localStorage.getItem(OpenidAgent)){
		window.localStorage.removeItem(OpenidAgent);
	}
	var agentOpenid = "{\"openid\":\"" + openid + "\"}";
	window.localStorage.setItem(OpenidAgent, agentOpenid);
}
//保存页面跳转值
function saveAgentJudge(judge){
	if(window.localStorage.getItem(judgeAgent)){
		window.localStorage.removeItem(judgeAgent);
	}
	var agentJudge = "{\"judge\":" + judge + "}";
	window.localStorage.setItem(judgeAgent, agentJudge);
}
//保存跳转页面到agent
function saveAgentPage(pageurl){
	if(window.localStorage.getItem(pageName)){
		window.localStorage.removeItem(pageName);
	}
	var agentPage = "{\"pageurl\":\"" + pageurl + "\"}";
	window.localStorage.setItem(pageName, agentPage);
}
//清空所有的agent
function clearAgent(){
	if(window.localStorage.getItem(agentName)){
		window.localStorage.removeItem(agentName);
	}
}

//获取链接上的参数
function GetRequest() {  
   var url = location.search; //获取url中"?"符后的字串
   var theRequest = new Object();
   if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for(var i = 0; i < strs.length; i ++) {
         theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
      }
   }
   return theRequest;
}

/**
 * 加载资浏览器语言对应的资源文件
 */
function loadProperties(){
	$.i18n.properties({
		name:'message',
		path: '../../../../i18n/',
		mode:'map',
		callback: function() {
		}
	});
}
//判断字串是否为空
function strIsEmpty(str){
	if (str !== null && str !== undefined && str !== '') {
		return false;
	}else{
		return true;
	}
}




