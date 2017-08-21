package com.jk;

import java.util.List;

import com.jk.mapper.UserMapper;
import com.jk.result.OrderUser;
import com.jk.result.Orders;
import com.jk.utils.SqlSessionUtils;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testSelectOrderAndUser1() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		List<OrderUser> orderUser = userMapper.selectOrderAndUser1();
		for (OrderUser orderUser2 : orderUser) {
			System.out.println("=======start===========");
			System.out.println("id=" + orderUser2.getId());
			System.out.println("User_id=" + orderUser2.getUser_id());
			System.out.println("Number=" + orderUser2.getNumber());
			System.out.println("Username=" + orderUser2.getUsername());
			System.out.println("Sex=" + orderUser2.getSex());
			System.out.println("=======end===========");
		}
		SqlSessionUtils.getSqlSession().close();
	}

	public void testSelectOrderAndUser2() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		List<Orders> orderUser = userMapper.selectOrderAndUser2();
		for (Orders orderUser2 : orderUser) {
			System.out.println("=======start===========");
			System.out.println("id=" + orderUser2.getId());
			System.out.println("User_id=" + orderUser2.getUser_id());
			System.out.println("Number=" + orderUser2.getNumber());
			System.out.println("Username=" + orderUser2.getUser().toString());
			System.out.println("=======end===========");
		}
		SqlSessionUtils.getSqlSession().close();
	}
}
