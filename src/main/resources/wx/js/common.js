//查询信息
var query = function (requestUrl, param, callback, callbackparam, callbackerror) {
	//参数teamId 从localStorage 获取
	var data = {};
	$.ajax({
		type: 'POST',
		url: requestUrl,
		data: param,
		dataType: 'json',
		async: true,
		success: function (resp) {
			if (callbackparam) {
				callback(resp, callbackparam);
			} else {
				callback(resp);
			}
		},
		error: function (data) {
			//alert('错误：'+data);
			var retv = data.responseText;
			if (retv.indexOf("script") >= 0) {
				var i = retv.indexOf(">");
				var j = retv.indexOf(";");
				var a = retv.substring(i + 1, j);
				eval(a);
			}
		}
	});
}

var getUrlVars =function (){
	var vars = [], hash;
	var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	for (var i = 0; i < hashes.length; i++) {
		hash = hashes[i].split('=');
		vars.push(hash[0]);
		vars[hash[0]] = hash[1];
	}
	return vars;
}
