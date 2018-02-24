/**
 * 
 */
package com.bbg.open.b2b4pos.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author zhangxulong
 * @date 2016年4月12日
 *
 */
public class MD5 {
	public static String md5(String password) {
		password = password == null ? "" : password;
		MessageDigest md;
		try {
			// 生成一个MD5加密计算摘要
			md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(password.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			String pwd = new BigInteger(1, md.digest()).toString(16);
			return pwd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}

	/**
	 * 
	 * @Title:doMD5Sign
	 * @Description:生成签名。
	 * @param:@param signMap
	 * @param:@param key
	 * @param:@return
	 * @param:@throws Exception
	 * @return:String
	 * @Author:kevin
	 * @Datetime:2015年8月28日 下午12:57:23
	 */
	public static String doMD5Sign(Map<String, String> signMap) {
		return MD5.md5(SortUtil.createLinkString(signMap, true, false, "").toLowerCase());
	}
	
	public static void main(String[] args) {
		HashMap<String, String> testMap = new HashMap<String,String>();
		testMap.put("c", "3");
		testMap.put("D", "4");
		testMap.put("a", "111");
		testMap.put("b", "2");
		System.out.println(SortUtil.createLinkString(testMap, true, false, "").toLowerCase());
		System.out.println(MD5.doMD5Sign(testMap));
	}
}
