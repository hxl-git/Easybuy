package com.dao;

import java.sql.SQLException;

import com.entity.EasybuyOrderDetail;

public interface OrderDetailDao {
	//保存订单详情
    public void saveOrderDetail(EasybuyOrderDetail detail) throws SQLException ;
}
