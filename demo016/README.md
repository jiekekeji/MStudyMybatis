mybatis-基于注解的一对一查询
---

1、第一步：新建类TUserProvider，在里面编写动态的sql语句：
```
package com.jk.provider;

import org.apache.ibatis.jdbc.SQL;

import com.jk.pojo.TUser;

public class TUserProvider {

	// 插入语句 动态 sql,t_user:表名
	public String insertTUser(final TUser user) {
		return new SQL() {
			{
				INSERT_INTO("t_user");
				if (user.getUsername() != null) {
					VALUES("username", "#{username}");
				}
				if (user.getBirthday() != null) {
					VALUES("birthday", "#{birthday}");
				}
				if (user.getSex() != null) {
					VALUES("sex", "#{sex}");
				}
				if (user.getAddress() != null) {
					VALUES("address", "#{address}");
				}
			}
		}.toString();
	}

	// 更新语句 动态 sql,t_user:表名
	public String updateTUser(final TUser user) {
		return new SQL() {
			{
				UPDATE("t_user");
				if (user.getUsername() != null) {
					SET("username = #{username}");
				}
				if (user.getBirthday() != null) {
					SET("birthday = #{birthday}");
				}
				if (user.getSex() != null) {
					SET("sex = #{sex}");
				}
				if (user.getAddress() != null) {
					SET("address = #{address}");
				}
				WHERE("id = #{id}");
			}
		}.toString();
	}

	// 删除语句 动态 sql,t_user:表名
	public String deleteTUser(int id) {
		return new SQL() {
			{
				DELETE_FROM("t_user");
				WHERE("id = #{id}");
			}
		}.toString();
	}
	
	// 查询语句 动态 sql,t_user:表名
	public String selectTUserById(int id) {
		return new SQL() {
			{
				SELECT("*");
				FROM("t_user");
				WHERE("id = #{id}");
			}
		}.toString();
	}

	// 查询语句 动态 sql,t_user:表名
	public String selectTUserById3() {
		return new SQL() {
			{
				SELECT("*");
				FROM("t_user");
				ORDER_BY("id DESC");
			}
		}.toString();
	}
}
```

2、第二步：编写mapper接口，新建TUserMapper接口，通过 @InsertProvider、@UpdateProvider、
@DeleteProvider、@SelectProvider注解引用 第一步 定义的sql语句：
```
public interface TUserMapper {

	// **************引用动态SQL*********************************

	@InsertProvider(type = TUserProvider.class, method = "insertTUser")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertTUser2(TUser user);

	@UpdateProvider(type = TUserProvider.class, method = "updateTUser")
	int updateTUser2(TUser user);

	@DeleteProvider(type = TUserProvider.class, method = "deleteTUser")
	int deleteTUser2(int id);

	@SelectProvider(type = TUserProvider.class, method = "selectTUserById")
	TUser selectTUserById2(int id);

	@SelectProvider(type = TUserProvider.class, method = "selectTUserById3")
	List<TUser> selectTUserById3();

}

```

3、编写测试代码：
```
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

```