package com.ruiger.util;

import com.ruiger.constant.Constant;
import com.ruiger.thread.AccessTokenThread;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 睿哥
 * @version 1.0
 * @time 11:45
 * @description #
 * @since 2017/03/09
 */
public class SignUtil {

	private static Logger logger = Logger.getLogger(SignUtil.class);
	private static String token="ghost";


	/**
	 * 验证签名
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		//1.将token 、timestamp、 nonce 三个参数进行排序
		String[] array = new String[]{token,timestamp,nonce};
		Arrays.sort(array);

		//2.将三个参数字符拼接成一个字符串进行sha1加密
		StringBuilder str  = new StringBuilder();
		for(String s:array){
			str.append(s);
		}
		MessageDigest md = null;
		String tmpStr =encrypt_SHA(str.toString());
		if(tmpStr != null){
			if(tmpStr.equals(signature.toUpperCase())){
				logger.info("微信验证成功");
				return true;
			}
		}
		logger.info("微信验证失败");
		return false;
	}

	/**
	 * 获取微信jssdk wx_config 必要参数
	 * @param nonceStr
	 * @param timestamp
	 * @param pageUrl
	 * @return
	 */
	public static Map<String,Object> getWxConfig(String appId,String nonceStr, String timestamp, String pageUrl){
		Map<String, Object> ret = new HashMap<String, Object>();
		String sign = "jsapi_ticket=" + AccessTokenThread.jsApiTicket.getTicket() + "&noncestr=" + nonceStr+ "&timestamp=" + timestamp + "&url=" + pageUrl;
		String signature = encrypt_SHA(sign);
		ret.put("appId", appId);
		ret.put("timestamp", timestamp);
		ret.put("nonceStr", nonceStr);
		ret.put("signature", signature);
		return ret;
	}


	/**
	 * SHA-1 加密
	 * @param str
	 * @return
	 */
	private static String encrypt_SHA(String str){
		MessageDigest md = null;
		String tmpStr = null;
		try {
			//加密
			md = MessageDigest.getInstance("SHA-1");
			byte[] diget = md.digest(str.getBytes());
			tmpStr = byteToStr(diget);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return tmpStr;
	}

	/**
	 * 将字节数组转换为十六进制
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 转换十六进制
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}
}
