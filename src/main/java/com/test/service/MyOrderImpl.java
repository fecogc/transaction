package com.test.service;

import java.time.Instant;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.test.mapper.MyOrderMapper;
import com.test.model.MyOrder;

@Service
@Transactional
public class MyOrderImpl implements MyOrderService
{

	private final Logger log = Logger.getLogger(MyOrderImpl.class);
			
	@Autowired
	MyOrderMapper myOrderMapper;
	
	@Override
	public int insertMyOrder(MyOrder myOrder)
	{
		return myOrderMapper.insertMyOrder(myOrder);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int cancelMyOrder(int id) throws Exception
	{
		attemptMyOrder(id);
		
		MyOrder m = getMyOrder(id);
		if(m == null)
			throw new Exception("Doesnt Exist");
		
		log.info("cancel_date: " + m.getCancel_date());
		
		if(!StringUtils.isEmpty(m.getCancel_date()))
			throw new Exception("Already cancelled");
		
		return myOrderMapper.cancelMyOrder(id, Instant.now().toString());
	}

	@Override
	public MyOrder getMyOrder(int id)
	{
		return myOrderMapper.getMyOrder(id);
	}

	@Override
	public List<MyOrder> getMyOrders()
	{
		return myOrderMapper.getMyOrders();
	}

	@Override
	public int attemptMyOrder(int id)
	{
		return myOrderMapper.attemptMyOrder(id);
	}
}
