package com.jk.mapper;

import org.apache.ibatis.annotations.Select;

import com.jk.pojo.TUser;

public interface TUserMapper {

	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * from t_user WHERE id=#{id}")
	public TUser selectTUserById(Integer id);

}