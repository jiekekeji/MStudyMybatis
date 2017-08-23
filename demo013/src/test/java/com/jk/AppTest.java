package com.jk;

import org.apache.ibatis.session.SqlSession;

import com.jk.mapper.TUserMapper;
import com.jk.pojo.TUser;
import com.jk.utils.SqlSessionUtils;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testSelectByPrimaryKey() throws InterruptedException {

		SqlSession sqlSession = SqlSessionUtils.getSqlSession();

		TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

		TUser tUser = userMapper.selectByPrimaryKey(1);
		System.out.println(tUser);

		sqlSession.close();

	}

}
