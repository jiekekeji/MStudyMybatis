package com.jk;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jk.mapper.TUserMapper;
import com.jk.pojo.TUser;
import com.jk.utils.SqlSessionUtils;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testSelectTUserById() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		SqlSession sqlSession = SqlSessionUtils.getSqlSession();

		TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

		TUser tUser = userMapper.selectTUserById(1);
		System.out.println("tUser=" + tUser);

		sqlSession.close();
	}

	public void testTnsertTUser() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		SqlSession sqlSession = SqlSessionUtils.getSqlSession();

		TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

		TUser user = new TUser();
		user.setUsername("慕容燕燕");
		user.setBirthday(new Date().toLocaleString());
		user.setSex("1");
		user.setAddress("侨中路");

		userMapper.insertTUser(user);
		sqlSession.commit();

		sqlSession.close();
	}

	public void testUpdatetTUserById() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		SqlSession sqlSession = SqlSessionUtils.getSqlSession();

		TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

		TUser user = new TUser();
		user.setId(10);
		user.setUsername("上官飞燕");
		user.setBirthday(new Date().toLocaleString());
		user.setSex("1");
		user.setAddress("神剑山庄");

		userMapper.updatetTUserById(user);
		sqlSession.commit();

		sqlSession.close();
	}

	public void testDeleteTUserById() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		SqlSession sqlSession = SqlSessionUtils.getSqlSession();
		TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

		userMapper.deleteTUserById(12);
		sqlSession.commit();

		sqlSession.close();
	}

}
