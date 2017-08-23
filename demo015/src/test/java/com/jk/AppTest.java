package com.jk;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jk.mapper.TUserMapper;
import com.jk.pojo.TUser;
import com.jk.utils.SqlSessionUtils;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	
	public void testTnsertTUser2() {
		SqlSession sqlSession = SqlSessionUtils.getSqlSession();
		TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

		TUser user = new TUser();
		user.setUsername("古树慕容");
		user.setAddress("侨中路");

		userMapper.insertTUser2(user);
		sqlSession.commit();

		sqlSession.close();
	}

	public void testUpdatetTUserById2() {
		SqlSession sqlSession = SqlSessionUtils.getSqlSession();
		TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

		TUser user = new TUser();
		user.setId(4);
		// user.setUsername("上官飞燕");
		// user.setBirthday(new Date());
		user.setSex("1");
		user.setAddress("神剑山庄");

		userMapper.updateTUser2(user);
		sqlSession.commit();

		sqlSession.close();
	}

	public void testDeleteTUserById2() {
		SqlSession sqlSession = SqlSessionUtils.getSqlSession();
		TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

		userMapper.deleteTUser2(13);
		sqlSession.commit();

		sqlSession.close();
	}

	public void testSelectTUserById2() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		SqlSession sqlSession = SqlSessionUtils.getSqlSession();

		TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

		TUser tUser = userMapper.selectTUserById2(1);
		System.out.println("tUser=" + tUser);

		sqlSession.close();
	}

	public void testSelectTUserById3() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		SqlSession sqlSession = SqlSessionUtils.getSqlSession();

		TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

		List<TUser> tUsers = userMapper.selectTUserById3();
		System.out.println("tUsers=" + tUsers);

		sqlSession.close();
	}
}
