package com.bbg.open.b2b4pos.web.result;

import java.util.List;

import com.bbg.open.b2b4pos.entity.LoadBillDetail;

public class SaleOrderResult extends BaseResult {

	public SaleOrderResult(){}
	
	public SaleOrderResult(String msg, String msgCode) {
		// TODO Auto-generated constructor stub
		super(msgCode, msg);
	}
	
	private List<LoadBillDetail> orderList;
	
	public List<LoadBillDetail> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<LoadBillDetail> orderList) {
		this.orderList = orderList;
	}
}
