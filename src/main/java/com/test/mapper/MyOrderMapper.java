package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.test.model.MyOrder;

public interface MyOrderMapper
{
	@Insert("INSERT INTO myorder (id, name) "
			+ "VALUES (#{id}, #{name})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public int insertMyOrder(MyOrder myOrder);
	
	@Select("SELECT id, "
			+ "name,"
			+ "attempt_num, "
			+ "cancel_num, "
			+ "cancel_date "
			+ "FROM myorder")	
	List<MyOrder> getMyOrders();
	
	@Select("SELECT id, "
			+ "name,"
			+ "attempt_num, "
			+ "cancel_num, "
			+ "cancel_date "
			+ "FROM myorder "
			+ "WHERE id = #{id}")	
	MyOrder getMyOrder(@Param("id") final int id);
	
	@Update("UPDATE myorder "
			+ "SET attempt_num = attempt_num + 1 "
			+ "WHERE id = #{id}")
	public int attemptMyOrder(@Param("id") int id);

	@Update("UPDATE myorder "
			+ "SET cancel_date = #{cancel_date},"
			+ "cancel_num = cancel_num + 1 "
			+ "WHERE id = #{id}")
	public int cancelMyOrder(@Param("id") int id, @Param("cancel_date") String cancel_date);
}
