package com.bbg.open.b2b4pos.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbg.open.b2b4pos.utils.Exec;
import com.bbg.open.b2b4pos.utils.StringUtils;
import com.bbg.open.b2b4pos.web.result.BaseResult;

@Controller
public class LoginController {
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpServletResponse response;
	
	//private ConcurrentHashMap<String, List<String>> roomMap = new ConcurrentHashMap<String,List<String>>();
	
	/*@RequestMapping("/index")
	public String toIndex(){
		return "index";
	}*/
	
	@RequestMapping("/toLogin")
	@ResponseBody
	public String toLogin(String userId){
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("webSocketSessionId", userId);
		return userId;
	}
	
	@RequestMapping("/queryUserName")
	@ResponseBody
	public String queryUserName(){
		String userId = (String) request.getSession().getAttribute("webSocketSessionId");
		return userId;
	}
	
	@RequestMapping("/queryRoomCode")
	@ResponseBody
	public String queryRoomCode(){
		String roomCode = (String) request.getSession().getAttribute("roomCode");
		return roomCode;
	}
	
	@RequestMapping("/toWebsocket")
	public String toWebsocket(String userId){
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("webSocketSessionId", userId);
		return "login";
	}


}
