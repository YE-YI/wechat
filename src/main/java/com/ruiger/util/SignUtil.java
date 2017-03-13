package com.ruiger.util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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
		String tmpStr = null;
		try {
			//加密
			md = MessageDigest.getInstance("SHA-1");
			byte[] diget = md.digest(str.toString().getBytes());
			tmpStr = byteToStr(diget);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
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
