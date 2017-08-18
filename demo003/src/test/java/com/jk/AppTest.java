package com.jk;

import java.util.List;

import com.jk.bean.User;
import com.jk.mapper.UserMapper;
import com.jk.utils.SqlSessionUtils;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testInsert() throws Exception {
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);

		User user = new User();
		user.setUsername("小白");
		user.setBirthday("1993-12-20");
		user.setSex("男");
		user.setAddress("秀英区");
		userMapper.insertUser(user);

		SqlSessionUtils.getSqlSession().commit();

		System.out.println("获取插入数据的主键:" + user.getId());
		SqlSessionUtils.getSqlSession().close();
	}

	public void testDelte() throws Exception {
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		userMapper.deletetUserById(37);
		// 不要漏掉提交事务
		SqlSessionUtils.getSqlSession().commit();

		SqlSessionUtils.getSqlSession().close();
	}

	public void testUpdate() {
		User user = new User();
		user.setId(36);
		user.setUsername("小明明明");

		int code = SqlSessionUtils.getSqlSession().update("test.updateUser", user);
		// 不要漏掉提交事务
		SqlSessionUtils.getSqlSession().commit();

		SqlSessionUtils.getSqlSession().close();
	}

	public void testSelectUserById() throws Exception {
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		User user = userMapper.selectUserById(37);

		System.out.println("user=" + user);
		SqlSessionUtils.getSqlSession().close();
	}

	public void testSelectUsers() {
		List<User> users = SqlSessionUtils.getSqlSession().selectList("test.selectUsers");
		System.out.println("users=" + users);
		SqlSessionUtils.getSqlSession().close();
	}
}
