package com.bbg.open.b2b4pos.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bbg.open.b2b4pos.constants.Const;
import com.bbg.open.b2b4pos.entity.User;


/**
 * 用户信息工具
 * 
 * @author zqw
 * @version 1.0 date : 2015/10/13
 */

public class UserInfoUtil {

	/**
	 * 从session中获取当前用户对象
	 * 
	 * @param request
	 * @return
	 */
	public static User getUser(HttpServletRequest request) {
		return (User) getSession(request).getAttribute(Const.USER_SESSION_CACHE);
	}
	
	
	

	/**
	 * 将当前用户对象存放到session
	 * 
	 * @param request
	 * @param user
	 */
	public static void setUser(HttpServletRequest request, User user) {
		getSession(request).setAttribute(Const.USER_SESSION_CACHE, user);
	}
	
	
	

	public static void logout(HttpServletRequest request) {
		HttpSession sessioin = getSession(request);
		sessioin.removeAttribute(Const.USER_SESSION_CACHE);
		sessioin.invalidate();
	}

	public static HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}
}
