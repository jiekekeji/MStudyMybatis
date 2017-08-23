mybatis-基于注解的简单增删查改
---

使用注解，省去写mapper.xml文件，直接在mapper接口上加入注解即可。

1、使用注解mybatis 的配置文件SqlMapperConfig.xml 和使用xml没有区别,使用package映射接口的包名：
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<!-- 加载数据库文件db.properties -->
	<properties resource="db.properties"></properties>

	<!-- 别名定义 -->
	<typeAliases>
		<!-- 给com.jk.pojo包下的bean都定义别名，别名就是类名(首字母大写或小写都可以) -->
		<package name="com.jk.pojo" />
	</typeAliases>

	<!-- 和spring整合后environments配置将被废除 -->
	<environments default="development">
		<environment id="development">
			<!-- 使用jdbc事务管理 -->
			<transactionManager type="JDBC" />
			<!-- 配置数据库连接池 ，使用db.properties的配置 -->
			<dataSource type="POOLED">
				<property name="driver" value="${jdbc.driver}" />
				<property name="url" value="${jdbc.url}" />
				<property name="username" value="${jdbc.username}" />
				<property name="password" value="${jdbc.password}" />
			</dataSource>
		</environment>
	</environments>

	<!--加载映射文件 -->
	<mappers>
		<package name="com.jk.mapper" />
	</mappers>
</configuration>
```

2、接口文件编写:
```
package com.jk.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jk.pojo.TUser;

public interface TUserMapper {

	/**
	 * sql语句中 #{id} 中的id是参数对象中的属性：
	 * 
	 * 如：@Insert(
	 * "INSERT INTO t_user (username,birthday,sex,address) VALUES (#{username},#{birthday},#{sex},#{address})"
	 * )
	 * 
	 * #{username} username 是 类user中的属性
	 * 
	 * 其他类似
	 * 
	 */

	// **************基础*********************************

     //增
	@Insert("INSERT INTO t_user (username,birthday,sex,address) VALUES (#{username},#{birthday},#{sex},#{address})")
	public void insertTUser(TUser user);

     //查
	@Select("SELECT * from t_user WHERE id=#{id}")
	public TUser selectTUserById(Integer id);

     //更
	@Update("UPDATE t_user u SET u.username=#{username},u.birthday=#{birthday},u.sex=#{sex},u.address=#{address} WHERE u.id=#{id}")
	public void updatetTUserById(TUser user);

     //删
	@Delete("DELETE FROM t_user WHERE id=#{id}")
	public void deleteTUserById(Integer id);

}
```

3、测试代码:
```
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
```