package com.jk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jk.bean.User;
import com.jk.mapper.UserMapper;
import com.jk.utils.SqlSessionUtils;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testInsert() {

		User user = new User();
		user.setUsername("小明");
		user.setBirthday("1993-12-20");
		user.setSex("男");
		user.setAddress("秀英区");

		// 获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		userMapper.insertUser(user);
		SqlSessionUtils.getSqlSession().commit();

		System.out.println("获取插入数据的主键:" + user.getId());

		SqlSessionUtils.getSqlSession().close();
	}

	public void testDelte() {
		// 获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		userMapper.deletetUserById(37);
		// 不要漏掉提交事务
		SqlSessionUtils.getSqlSession().commit();

		SqlSessionUtils.getSqlSession().close();
	}

	public void testUpdate() {
		User user = new User();
		user.setId(39);
		user.setUsername("小明明明");
		// 获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		userMapper.updateUser(user);
		// 不要漏掉提交事务
		SqlSessionUtils.getSqlSession().commit();

		SqlSessionUtils.getSqlSession().close();
	}

	public void testSelectUserById() {
		// 获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);

		Map map = new HashMap<String, Object>();
		map.put("id", 38);
		map.put("username", "小明");

		User user = userMapper.selectUserById(map);

		System.out.println("userone=" + user);
		SqlSessionUtils.getSqlSession().close();
	}

	public void testSelectUsers() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		List<User> users = userMapper.selectUsers();
		System.out.println("users=" + users);
		SqlSessionUtils.getSqlSession().close();
	}

	public void testFindUserCount() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		int count = userMapper.findUserCount();
		System.out.println("count=" + count);
		SqlSessionUtils.getSqlSession().close();
	}

	public void testSelectUserMapById() {
		// 获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);

		User user = userMapper.selectUserMapById(39);

		System.out.println("user=" + user);
		SqlSessionUtils.getSqlSession().close();
	}

}
