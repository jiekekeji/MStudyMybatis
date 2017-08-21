package com.jk;

import java.util.List;

import com.jk.mapper.UserMapper;
import com.jk.result.OrderUser;
import com.jk.utils.SqlSessionUtils;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testSelectUsers() {
	    ////获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		List<OrderUser> orderUser = userMapper.selectOrderAndUser();
		System.out.println("orderUser=" + orderUser);
		SqlSessionUtils.getSqlSession().close();
	}

}
