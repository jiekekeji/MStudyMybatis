package com.jk.mapper;

import java.util.List;

import com.jk.result.OrderUser;
import com.jk.result.Orders;

public interface UserMapper {

	public List<OrderUser> selectOrderAndUser1();

	public List<Orders> selectOrderAndUser2();
}
