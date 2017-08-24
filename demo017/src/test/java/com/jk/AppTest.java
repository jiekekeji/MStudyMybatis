package com.jk;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jk.mapper.TUserMapper;
import com.jk.result.TUserOrders;
import com.jk.utils.SqlSessionUtils;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testSelectItemsTUser() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		SqlSession sqlSession = SqlSessionUtils.getSqlSession();

		TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

		List<TUserOrders> tUserOrders = userMapper.selectTUserOrders();
		for (TUserOrders tUserOrder : tUserOrders) {
			System.out.println("tUserOrder=" + tUserOrder);
		}

		sqlSession.close();
	}
}
