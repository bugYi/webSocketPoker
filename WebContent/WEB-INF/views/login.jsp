<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>
<script type="text/javascript" src="js/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
<div>用户：${webSocketSessionId}</div>
<form id="login" action="/webSocketPoker/toWebsocket">
	<input name="userId"/>
	<button type="submit">提交</button>
</form>
<form id="getRoom" action="/webSocketPoker/getRoomCode">
	<button type="submit">获取房间号</button>
</form>
<form id="putRoom" action="/webSocketPoker/putRoomCode">
	<input name="roomCode"/>
	<button type="submit">进入房间</button>
</form>
</body>
<script type="text/javascript">
var msg = "${msg}";
if("" != msg && null != msg){
	alert(msg);
}

var userName = "${webSocketSessionId}"
if("" == userName || null == userName){
	$("#login").show();
	$("#getRoom").hide();
	$("#putRoom").hide();
}else{	
	$("#login").hide();
	$("#getRoom").show();
	$("#putRoom").show();
}
</script>
</html>