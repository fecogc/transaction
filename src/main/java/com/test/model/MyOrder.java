package com.test.model;

public class MyOrder
{
	private int id;
	private String name;
	private String cancel_date;
	private int attempt_num;
	private int cancel_num;
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getCancel_date()
	{
		return cancel_date;
	}
	
	public void setCancel_date(String cancel_date)
	{
		this.cancel_date = cancel_date;
	}

	public int getAttempt_num()
	{
		return attempt_num;
	}

	public void setAttempt_num(int attempt_num)
	{
		this.attempt_num = attempt_num;
	}

	public int getCancel_num()
	{
		return cancel_num;
	}

	public void setCancel_num(int cancel_num)
	{
		this.cancel_num = cancel_num;
	}
}
