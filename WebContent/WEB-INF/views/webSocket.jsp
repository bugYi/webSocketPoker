<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;  
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebScoketAPI</title>
</head>
<body>
<div>用户：${webSocketSessionId}</div>
<div>房间号：${roomCode}</div>
<button id="conBtn" onclick="connect()">连接</button>
<button onclick="send()">发送</button>
<button onclick="closeWebSocket()">退出</button>
<br>
<textarea id='text'></textarea>
<hr/>
<div id="message"></div>
</body>
<script type="text/javascript" src="<%=basePath%>/jquery.min.js"></script>
<script type="text/javascript">
var last_health;
var health_timeout = 10;
//心跳包
function keepalive(ws) {
    var time = new Date();
    if (last_health != -1 && (time.getTime() - last_health > health_timeout)) {

        // ws.close();
    } else {
        if (ws.bufferedAmount == 0) {
            ws.send('@heart');
        }
    }
}

    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    
    function connect(){
    	var userId = "${webSocketSessionId}"; 
    	if(null != userId && "" != userId){
    		if ('WebSocket' in window) {
                websocket = new WebSocket("ws://localhost:8080/webSocketPoker/websocket");
            }else {
                alert('当前浏览器 Not support websocket')
            }	
    		
    		//连接发生错误的回调方法
    	    websocket.onerror = function () {
    	        setMessageInnerHTML("WebSocket连接发生错误");
    	    };

    	    //连接成功建立的回调方法
    	    websocket.onopen = function () {
    	        setMessageInnerHTML("连接成功");
    	        $("#conBtn").hide();
    	        console.log('已连接');
                websocket.send("online"+userId);
                heartbeat_timer = setInterval(function() {
                    keepalive(websocket)
                }, 60000);
    	    }

    	    //接收到消息的回调方法
    	    websocket.onmessage = function (event) {
    	        setMessageInnerHTML(event.data);
    	    }

    	    //连接关闭的回调方法
    	    websocket.onclose = function () {
    	        setMessageInnerHTML("WebSocket连接关闭");
    	    }

    	    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    	    window.onbeforeunload = function () {
    	        closeWebSocket();
    	    }
    	}else{
    		alert('用户名不能为空！');
    	}
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
        $("#conBtn").show();
    }

    //发送消息
    function send() {
        var message = document.getElementById('text').value;
        websocket.send(message);
    }
</script>
</html>