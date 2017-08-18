package com.jk.utils;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionUtils {
	// mybatis配置文件
	public static final String resource = "SqlMapperConfig.xml";
	public static SqlSession sqlSession;

	public static SqlSession getSqlSession() {

		if (null != sqlSession) {
			return sqlSession;
		}
		// 得到配置文件流
		ClassLoader classLoader = SqlSessionUtils.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream(resource);

		// 创建会话工厂，向build方法中传入配置信息
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

		// 通过工厂得到SqlSession
		sqlSession = sqlSessionFactory.openSession(true);
		return sqlSession;
	}

	public static void closeSqlSession() {
		if (null != sqlSession) {
			sqlSession.close();
		}
		sqlSession = null;
	}
}
