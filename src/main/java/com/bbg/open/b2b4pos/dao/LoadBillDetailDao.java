package com.bbg.open.b2b4pos.dao;

import java.util.List;

import com.bbg.open.b2b4pos.entity.LoadBillDetail;

public interface LoadBillDetailDao {
    int deleteByPrimaryKey(LoadBillDetail key);

    int insert(LoadBillDetail record);

    int insertSelective(LoadBillDetail record);

    LoadBillDetail selectByPrimaryKey(LoadBillDetail key);

    int updateByPrimaryKeySelective(LoadBillDetail record);

    int updateByPrimaryKey(LoadBillDetail record);

	List<LoadBillDetail> queryLoadBillDetailList(String loadCode);
}