package com.bbg.open.b2b4pos.web;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbg.open.b2b4pos.constants.MsgCode;
import com.bbg.open.b2b4pos.service.LoadBillDetailService;
import com.bbg.open.b2b4pos.utils.StringUtils;
import com.bbg.open.b2b4pos.web.param.SaleOrderParam;
import com.bbg.open.b2b4pos.web.result.SaleOrderResult;

@Controller
public class LoadBillDetailController {

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpServletResponse response;
	
	@Autowired
	LoadBillDetailService loadBillDetailService;
	
	@RequestMapping("/saleOrderList")
	@ResponseBody
	public SaleOrderResult saleOrderList(SaleOrderParam saleOrder){        
		if (StringUtils.isEmpty(saleOrder.getLoadCode())) {
			new SaleOrderResult("装载单号不能为空", MsgCode.FAIL_CODE);
		}
		SaleOrderResult result = new SaleOrderResult();
		result.setOrderList(loadBillDetailService.querySaleOrderList(saleOrder));
		return result;
	}
	
}
