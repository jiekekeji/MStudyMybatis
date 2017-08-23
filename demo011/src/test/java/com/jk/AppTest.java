package com.jk;

import org.apache.ibatis.session.SqlSession;

import com.jk.bean.User;
import com.jk.mapper.UserMapper;
import com.jk.utils.SqlSessionUtils;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testFindUserByIdCache1() throws InterruptedException {
		SqlSession sqlSession;
		UserMapper userMapper;

		sqlSession = SqlSessionUtils.getSqlSession();
		userMapper = sqlSession.getMapper(UserMapper.class);

		// 同一个sqlSession获取的mapper
		User user1 = userMapper.findUserById(3);
		System.out.println("user1=" + user1);

		Thread.sleep(6 * 1000);

		// 同一个sqlSession获取的mapper
		User user2 = userMapper.findUserById(3);
		System.out.println("user2=" + user2);

		// sqlSession关闭
		sqlSession.close();
		System.out.println("sqlSession关闭,关闭之后重新获取sqlSession再查试试");

		// 获取另一个sqlSession
		sqlSession = SqlSessionUtils.getSqlSession();
		userMapper = sqlSession.getMapper(UserMapper.class);

		User user3 = userMapper.findUserById(3);
		System.out.println("user3=" + user3);
		sqlSession.close();

	}

	public void testFindUserByIdCache2() throws InterruptedException {
		SqlSession sqlSession1 = SqlSessionUtils.getSqlSession();
		SqlSession sqlSession2 = SqlSessionUtils.getSqlSession();

		// 1、查询 使用sqlSession1，同一个UserMapper
		UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
		// 第一次发起请求，查询id为1的用户
		User user1 = userMapper1.findUserById(1);
		System.out.println(user1);

		// 这里执行关闭操作，将sqlsession中的数据写到二级缓存区域
		sqlSession1.close();

		// 2、查询 使用sqlSession2，同一个UserMapper
		UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
		// 第二次发起请求，查询id为1的用户
		User user2 = userMapper2.findUserById(1);
		System.out.println(user2);

		sqlSession2.close();

	}

	public void testFindUserByIdCache3() throws InterruptedException {
		SqlSession sqlSession1 = SqlSessionUtils.getSqlSession();
		SqlSession sqlSession2 = SqlSessionUtils.getSqlSession();
		SqlSession sqlSession3 = SqlSessionUtils.getSqlSession();

		// 1、查询 使用sqlSession1，同一个UserMapper
		UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
		// 第一次发起请求，查询id为1的用户
		User user1 = userMapper1.findUserById(1);
		System.out.println(user1);

		// 这里执行关闭操作，将sqlsession中的数据写到二级缓存区域
		sqlSession1.close();

		// 2、使用sqlSession3执行commit()操作
		UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
		User user = userMapper3.findUserById(1);
		user.setUsername("张明明");
		userMapper3.updateUserById(user);
		// 执行提交，清空UserMapper下边的二级缓存
		sqlSession3.commit();
		sqlSession3.close();

		// 3、查询 使用sqlSession2，同一个UserMapper
		UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
		// 第二次发起请求，查询id为1的用户
		User user2 = userMapper2.findUserById(1);
		System.out.println(user2);

		sqlSession2.close();

	}
}
