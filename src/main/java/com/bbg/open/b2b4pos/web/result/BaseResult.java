package com.bbg.open.b2b4pos.web.result;

public class BaseResult {
	
	private String msgCode = "";
	private String msg = "";
	
	
	public BaseResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BaseResult(String msgCode, String msg) {
		super();
		this.msgCode = msgCode;
		this.msg = msg;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
