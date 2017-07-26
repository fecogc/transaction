package com.test.service;

import java.util.List;

import com.test.model.MyOrder;

public interface MyOrderService
{
	int insertMyOrder(MyOrder myOrder);
	int attemptMyOrder(int id);
	int cancelMyOrder(int id) throws Exception;
	List<MyOrder> getMyOrders();
	MyOrder getMyOrder(int id);
}
