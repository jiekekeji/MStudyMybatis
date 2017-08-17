package com.jk.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.jk.bean.User;

public class UserDao {

	public void findUserById() throws IOException {

		// mybatis配置文件
		String resource = "SqlMapperConfig.xml";

		// 得到配置文件流
		ClassLoader classLoader = UserDao.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream(resource);

		// 创建会话工厂，向build方法中传入配置信息
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

		// 通过工厂得到SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 通过SqlSession操作数据库
		// 第一个参数是：映射文件中Statement的id，等于namespace+statement的id
		// 第二个参数：指定和映射文件中所匹配的parameterType类型的参数
		// 结果就是实体类,selectOne表示查询出一条记录
		User user = sqlSession.selectOne("test.findUserById", 1);

		System.out.println(user);

		// 释放资源
		sqlSession.close();
	}

}
