package com.bbg.open.b2b4pos.utils;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/*
 * @auto zl lenovo
 * 
 * @date 2014-2-25
 * 
 * @description 对map进行排序  para 入参 sort 是否排序  encode 是否转换格式  charset转换为什么格式
 */
public class SortUtil {

	/**
	 * 对para进行ascii码排序
	 * @param para
	 * @param sort 是否排序
	 * @param encode
	 * @param charset
	 * @return
	 */
	public static String createLinkString(Map<String, String> para, boolean sort,
					boolean encode, String charset) {

		List<String> keys = new ArrayList<String>(para.keySet());

		if (sort) {
			Collections.sort(keys);
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = para.get(key);

			if (encode) {
				try {
					value = URLEncoder.encode(value, charset);
				} catch (UnsupportedEncodingException e) {
				}
			}

			if (i == keys.size() - 1) {
				sb.append(key).append("=").append(value);
			} else {
				sb.append(key).append("=").append(value).append("&");
			}
		}

		return sb.toString();
	}
}
