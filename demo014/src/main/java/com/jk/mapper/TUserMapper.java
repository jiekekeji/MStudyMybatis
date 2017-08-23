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

	@Insert("INSERT INTO t_user (username,birthday,sex,address) VALUES (#{username},#{birthday},#{sex},#{address})")
	public void insertTUser(TUser user);

	@Select("SELECT * from t_user WHERE id=#{id}")
	public TUser selectTUserById(Integer id);

	@Update("UPDATE t_user u SET u.username=#{username},u.birthday=#{birthday},u.sex=#{sex},u.address=#{address} WHERE u.id=#{id}")
	public void updatetTUserById(TUser user);

	@Delete("DELETE FROM t_user WHERE id=#{id}")
	public void deleteTUserById(Integer id);

}