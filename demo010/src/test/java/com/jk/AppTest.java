package com.jk;

import java.util.List;

import com.jk.mapper.UserMapper;
import com.jk.result.Orders;
import com.jk.utils.SqlSessionUtils;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testSelectOrderAndUser2() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		List<Orders> orders = userMapper.selectOrderAndUser2();
		for (Orders orderUser2 : orders) {
			System.out.println(orderUser2.getUser().toString());
		}
		SqlSessionUtils.getSqlSession().close();
	}
}
