package com.jk;

import java.util.ArrayList;
import java.util.List;

import com.jk.bean.Query;
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

        //获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		userMapper.insertUser(user);
		SqlSessionUtils.getSqlSession().commit();

		System.out.println("获取插入数据的主键:" + user.getId());

		SqlSessionUtils.getSqlSession().close();
	}

	public void testDelte() {
	   //获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		userMapper.deletetUserById(37);
		// 不要漏掉提交事务
		SqlSessionUtils.getSqlSession().commit();

		SqlSessionUtils.getSqlSession().close();
	}

	public void testUpdate() {
		User user = new User();
		user.setId(39);
//		user.setUsername("小红");
		user.setAddress("台北市");
//		user.setSex("女");
        //获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		userMapper.updateUser(user);
		// 不要漏掉提交事务
		SqlSessionUtils.getSqlSession().commit();

		SqlSessionUtils.getSqlSession().close();
	}

	public void testSelectUserById() {
	    //获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		User user = userMapper.selectUserById(38);
		System.out.println("userone=" + user);
		SqlSessionUtils.getSqlSession().close();
	}

	public void testSelectUsers() {
	    ////获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		List<User> users = userMapper.selectUsers();
		System.out.println("users=" + users);
		SqlSessionUtils.getSqlSession().close();
	}
	
	public void testUpdateUserById(){
		User user = new User();
		user.setId(39);
		user.setUsername("小可");
		user.setAddress("琼中县兴教路");
		user.setSex("女");
        //获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		
		userMapper.updateUserById(user);
		// 不要漏掉提交事务
		SqlSessionUtils.getSqlSession().commit();

		SqlSessionUtils.getSqlSession().close();
	}
	

	public void testsSelectUserByIds() {
	    ////获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		Query query=new Query();
		List<Integer> ids=new ArrayList<Integer>();
		ids.add(38);
		ids.add(40);
		query.setIds(ids);
		List<User> users = userMapper.selectUserByIds(query);
		System.out.println("users=" + users);
		SqlSessionUtils.getSqlSession().close();
	}
}
