package com.bbg.open.b2b4pos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbg.open.b2b4pos.dao.LoadBillDetailDao;
import com.bbg.open.b2b4pos.entity.LoadBillDetail;
import com.bbg.open.b2b4pos.service.LoadBillDetailService;
import com.bbg.open.b2b4pos.web.param.SaleOrderParam;

@Service
@Transactional(readOnly=true)
public class LoadBillDetailServiceImpl implements LoadBillDetailService {

	@Autowired
	LoadBillDetailDao loadBillDetailDao;

	@Override
	public List<LoadBillDetail> querySaleOrderList(SaleOrderParam saleOrder) {
		// TODO Auto-generated method stub
		return loadBillDetailDao.queryLoadBillDetailList(saleOrder.getLoadCode());
	}
}
