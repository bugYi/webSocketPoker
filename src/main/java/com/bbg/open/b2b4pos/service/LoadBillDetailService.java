package com.bbg.open.b2b4pos.service;

import java.util.List;

import com.bbg.open.b2b4pos.entity.LoadBillDetail;
import com.bbg.open.b2b4pos.web.param.SaleOrderParam;

public interface LoadBillDetailService {

	List<LoadBillDetail> querySaleOrderList(SaleOrderParam saleOrder);

}
