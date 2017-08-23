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
