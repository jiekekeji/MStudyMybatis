package com.jk.utils;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionUtils {
	// mybatis配置文件
	public static final String resource = "SqlMapperConfig.xml";
	public static SqlSession sqlSession;

	// 得到配置文件流
	private static ClassLoader classLoader;
	private static InputStream is;

	private static SqlSessionFactory sqlSessionFactory;

	static {
		// 得到配置文件流
		classLoader = SqlSessionUtils.class.getClassLoader();
		is = classLoader.getResourceAsStream(resource);
		// 创建会话工厂，向build方法中传入配置信息
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
	}

	public static SqlSession getSqlSession() {
		sqlSession = sqlSessionFactory.openSession(true);
		return sqlSession;
	}

}
