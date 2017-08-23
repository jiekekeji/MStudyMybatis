package com.jk;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jk.mapper.TUserMapper;
import com.jk.result.OrdersUser;
import com.jk.utils.SqlSessionUtils;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testSelectItemsTUser() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		SqlSession sqlSession = SqlSessionUtils.getSqlSession();

		TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

		List<OrdersUser> tUsers = userMapper.selectItemsTUser();
		for (OrdersUser itemsUser : tUsers) {
			System.out.println("itemsUser="+itemsUser);
		}

		sqlSession.close();
	}
}
