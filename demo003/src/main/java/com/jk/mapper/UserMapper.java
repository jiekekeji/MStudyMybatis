package com.jk.mapper;

import com.jk.bean.User;

public interface UserMapper {

	public Integer insertUser(User user) throws Exception;

	public void deletetUserById(Integer id) throws Exception;

	public User selectUserById(Integer id) throws Exception;
}
