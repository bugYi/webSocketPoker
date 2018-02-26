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
	
	private ConcurrentHashMap<String, List<String>> roomMap = new ConcurrentHashMap<String,List<String>>();
	
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
	
	
	
	@RequestMapping("/toWebsocket")
	public String toWebsocket(String userId){
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("webSocketSessionId", userId);
		return "login";
	}
	
	
	/**
	 * 向房间里面的人发送消息
	 * @param roomCode
	 * @return
	 */
	@RequestMapping("/playFull")
	@ResponseBody
	public BaseResult playFull(String roomCode){
		//房间里面连接人sid列表
		List<String> sidList = querySidByRoomCode(roomCode);
		
		List<String> nameSidList = queryNameAndSidByRoomCode(roomCode);
		
		//发好的牌列表
		Exec exec = new Exec();//默认54张牌，留三张底牌，三人
		List<String> pokList = exec.getList();
		
		try {
			StringBuffer sTemp = new StringBuffer();
			for (String nameSid : nameSidList) {
				sTemp.append(nameSid + ";");
			}
			toRoomSendMsg(roomCode, "@100:" + sTemp.toString());
			for (int i = 0; i < 3; i++) {
				toSendMsg(sidList.get(i), "@100pokers-" + pokList.get(i));
			}
			toRoomSendMsg(roomCode, "@100pokers-" + pokList.get(3));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new BaseResult("9999", "失败");
		}
		return new BaseResult("0000", "成功");	
	}
	
	@RequestMapping("/ajaxGetRoomCode")
	@ResponseBody
	public Map<String, String> ajaxGetRoomCode(){
		Map<String, String> result = new HashMap<String, String>();
		String roomCode = "";
		List<String> list = new ArrayList<String>();
		String userId = (String) request.getSession().getAttribute("webSocketSessionId");
		if(StringUtils.isEmpty(userId)){
			result.put("msg", "请先登录");
			return result;	
		}
		list.add(userId);
		
		if(roomMap.isEmpty()){//当没有房间的时候随机创建一个
			roomCode = ((int) ((Math.random()*9+1)*100000))+"";
		}else{//如果存在房间则需要判断房间号是否重复
			boolean flag = true;//是否找到房间号
			while(flag){
				roomCode = ((int) ((Math.random()*9+1)*100000))+"";
				Enumeration<String> enu = roomMap.keys();//取map中所有房间号
				while (enu.hasMoreElements()) {
					String rooms = (String) enu.nextElement();
					if(roomCode.equals(rooms)){//如果房间号存在则跳出循环
						flag = true;
						break;
					}else{
						flag = false;
					}
				}
			}
		}
		roomMap.put(roomCode, list);
		request.getSession().setAttribute("roomCode", roomCode);
		result.put("roomCode", roomCode);
		return result;
	}
	
	
	@RequestMapping("/getRoomCode")
	public String getRoomCode(){
		String roomCode = "";
		List<String> list = new ArrayList<String>();
		String userId = (String) request.getSession().getAttribute("webSocketSessionId");
		if(StringUtils.isEmpty(userId)){
			request.setAttribute("msg", "请先登录");
			return "login";	
		}
		list.add(userId);
		
		if(roomMap.isEmpty()){//当没有房间的时候随机创建一个
			roomCode = ((int) ((Math.random()*9+1)*100000))+"";
		}else{//如果存在房间则需要判断房间号是否重复
			boolean flag = true;//是否找到房间号
			while(flag){
				roomCode = ((int) ((Math.random()*9+1)*100000))+"";
				Enumeration<String> enu = roomMap.keys();//取map中所有房间号
				while (enu.hasMoreElements()) {
					String rooms = (String) enu.nextElement();
					if(roomCode.equals(rooms)){//如果房间号存在则跳出循环
						flag = true;
						break;
					}else{
						flag = false;
					}
				}
			}
		}
		roomMap.put(roomCode, list);
		request.getSession().setAttribute("roomCode", roomCode);
		return "webSocket";	
	}
	
	
	@RequestMapping("/putRoomByCode")
	@ResponseBody
	public Map<String, String> putRoomByCode(String roomCode){
		Map<String, String> result = new HashMap<String, String>();
		boolean flag = false;//是否找到房间号
		Enumeration<String> enu = roomMap.keys();//取map中所有房间号
		while (enu.hasMoreElements()) {
			String rooms = (String) enu.nextElement();
			if(roomCode.equals(rooms)){//如果房间号存在则跳出循环
				flag = true;
				break;
			}else{
				flag = false;
			}
		}
		
		if(flag){//找到房间号，则进入房间并跳转连接接页面
			String userId = (String) request.getSession().getAttribute("webSocketSessionId");
			if(StringUtils.isEmpty(userId)){
				result.put("msg", "请先登录");
				return result;	
			}
			List<String> list = roomMap.get(roomCode);
			list.add(userId);
			roomMap.put(roomCode, list);
			request.getSession().setAttribute("roomCode", roomCode);
			result.put("roomCode", roomCode);
			return result;	
		}else{//否则回到输房间号页面
			result.put("msg", "房间号不正确");
			return result;	
		}
		
	}
	
	@RequestMapping("/putRoomCode")
	public String putRoomCode(String roomCode){
		boolean flag = false;//是否找到房间号
		Enumeration<String> enu = roomMap.keys();//取map中所有房间号
		while (enu.hasMoreElements()) {
			String rooms = (String) enu.nextElement();
			if(roomCode.equals(rooms)){//如果房间号存在则跳出循环
				flag = true;
				break;
			}else{
				flag = false;
			}
		}
		
		if(flag){//找到房间号，则进入房间并跳转连接接页面
			String userId = (String) request.getSession().getAttribute("webSocketSessionId");
			if(StringUtils.isEmpty(userId)){
				request.setAttribute("msg", "请先登录");
				return "login";	
			}
			List<String> list = roomMap.get(roomCode);
			list.add(userId);
			roomMap.put(roomCode, list);
			request.getSession().setAttribute("roomCode", roomCode);
			return "webSocket";	
		}else{//否则回到输房间号页面
			request.setAttribute("msg", "房间号不正确");
			return "login";	
		}
		
	}
	
	 /**
     * 查询所有在线人员的sid
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("/getAllOnline")
    @ResponseBody
    public List<String> getAllOnline(ModelMap modelMap,HttpSession session){
        List<String> ids=new ArrayList<String>();
        
        Enumeration<String> enu = EndPointServer.sessionMap.keys();
		while (enu.hasMoreElements()) {//遍历所有连接
			String key = (String) enu.nextElement();
			ids.add(key.split("-")[0]);//前面是名字，后面是sessionID
			//EndPointServer.sessionMap.get(key);
		}
        
        return ids;
    }

    /**
     * 向用户发送消息
     * @param modelMap
     * @param session
     * @param sid
     * @param msg
     * @return
     * @throws IOException 
     */
    @ResponseBody
    @RequestMapping("/toSendMsg")
    public BaseResult toSendMsg(String sid,String msg) throws IOException{
    	sendMsgBySid(sid, msg);
        return new BaseResult("0000","成功");
    }
    
    public void sendMsgBySid(String sid,String msg) throws IOException{
    	 if (StringUtils.isNotEmpty(sid)) {//sid不为空，则指定用户发送消息
         	Enumeration<String> enu = EndPointServer.sessionMap.keys();
     		while (enu.hasMoreElements()) {//遍历所有连接
     			String key = (String) enu.nextElement();
     			String tempSid = EndPointServer.sessionMap.get(key).session.getId();
     			if(tempSid.equals(sid)){
     				EndPointServer.sessionMap.get(key).sendMessage(msg);
     			}
     		}
         }else {
         	Enumeration<String> enu = EndPointServer.sessionMap.keys();
     		while (enu.hasMoreElements()) {//遍历所有连接
     			String key = (String) enu.nextElement();
     			EndPointServer.sessionMap.get(key).sendMessage(msg);
     		}
         }
    }
    
    
    /**
     * 向房间发送消息
     * @param modelMap
     * @param session
     * @param sid
     * @param msg
     * @return
     * @throws IOException 
     */
    @ResponseBody
    @RequestMapping("/toRoomSendMsg")
    public BaseResult toRoomSendMsg(String roomCode,String msg) throws IOException{
    	sendMsgByRoom(roomCode, msg);
        return new BaseResult("0000","发送成功");
    }
    
    
    public void sendMsgByRoom(String roomCode,String msg) throws IOException{
    	if (StringUtils.isNotEmpty(roomCode)) {//roomCode不为空，则指定房间发送消息
        	Enumeration<String> enu = EndPointServer.sessionMap.keys();
    		while (enu.hasMoreElements()) {//遍历所有连接
    			String key = (String) enu.nextElement();
    			String tempCode = (String) EndPointServer.sessionMap.get(key).httpSession.getAttribute("roomCode");
    			if(tempCode.equals(roomCode)){
    				EndPointServer.sessionMap.get(key).sendMessage(msg);
    			}
    		}
        }else {
        	Enumeration<String> enu = EndPointServer.sessionMap.keys();
    		while (enu.hasMoreElements()) {//遍历所有连接
    			String key = (String) enu.nextElement();
    			EndPointServer.sessionMap.get(key).sendMessage(msg);
    		}
        }
    }
    
    
    public List<String> querySidByRoomCode(String roomCode){
    	List<String> list = new ArrayList<String>();
    	Enumeration<String> enu = EndPointServer.sessionMap.keys();
		while (enu.hasMoreElements()) {//遍历所有连接
			String key = (String) enu.nextElement();
			String tempCode = (String) EndPointServer.sessionMap.get(key).httpSession.getAttribute("roomCode");
			if(tempCode.equals(roomCode)){
				String sid = EndPointServer.sessionMap.get(key).session.getId();
				list.add(sid);
			}
		}
		return list;
    }
    
    
    /**
     * 
     * @param roomCode
     * 根据房间号获取房间里面连接人的昵称和id
     * @return
     */
    public List<String> queryNameAndSidByRoomCode(String roomCode){
    	List<String> list = new ArrayList<String>();
    	Enumeration<String> enu = EndPointServer.sessionMap.keys();
		while (enu.hasMoreElements()) {//遍历所有连接
			String key = (String) enu.nextElement();
			String tempCode = (String) EndPointServer.sessionMap.get(key).httpSession.getAttribute("roomCode");
			if(tempCode.equals(roomCode)){
				String nameAndSid = EndPointServer.sessionMap.get(key).httpSession.getAttribute("webSocketSessionId")
						+ "," + EndPointServer.sessionMap.get(key).session.getId();
				list.add(nameAndSid);
			}
		}
		return list;
    }

    /**
     * 广播发送消息
     * @param modelMap
     * @param session
     * @param msg
     * @return
     * @throws IOException 
     */
    @ResponseBody
    @RequestMapping("/toBroadCastMsg")
    public ModelMap toBroadCastMsg(ModelMap modelMap,HttpSession session,String msg) throws IOException{
        if (msg != null && msg.length() > 0) {
        	Enumeration<String> enu = EndPointServer.sessionMap.keys();
    		while (enu.hasMoreElements()) {//遍历所有连接
    			String key = (String) enu.nextElement();
    			EndPointServer.sessionMap.get(key).sendMessage(msg);
    		}
        }else {
            modelMap.put("msg", -1);//参数错误
        }

        return modelMap;
    }


}
