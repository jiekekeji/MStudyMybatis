package com.jk;

import java.util.List;

import com.jk.mapper.UserMapper;
import com.jk.result.Orders;
import com.jk.result.UserItems;
import com.jk.utils.SqlSessionUtils;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testSelectOrderAndUser2() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		List<UserItems> orderUser = userMapper.selectOrderAndUser2();
		for (UserItems orderUser2 : orderUser) {
			System.out.println(orderUser2.toString());
		}
		SqlSessionUtils.getSqlSession().close();
	}
}
