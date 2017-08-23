package com.jk;

import org.apache.ibatis.session.SqlSession;

import com.jk.bean.User;
import com.jk.mapper.UserMapper;
import com.jk.utils.SqlSessionUtils;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testFindUserById() throws InterruptedException {
		SqlSession sqlSession;
		UserMapper userMapper;

		sqlSession = SqlSessionUtils.getSqlSession();
		userMapper = sqlSession.getMapper(UserMapper.class);

		//同一个sqlSession获取的mapper
		User user1 = userMapper.findUserById(3);
		System.out.println("user1=" + user1);

		Thread.sleep(6 * 1000);

		//同一个sqlSession获取的mapper
		User user2 = userMapper.findUserById(3);
		System.out.println("user2=" + user2);

		//sqlSession关闭
		sqlSession.close();
		System.out.println("sqlSession关闭,关闭之后重新获取sqlSession再查试试");

		//获取另一个sqlSession
		sqlSession = SqlSessionUtils.getSqlSession();
		userMapper = sqlSession.getMapper(UserMapper.class);
		
		User user3 = userMapper.findUserById(3);
		System.out.println("user3=" + user3);

	}
}
