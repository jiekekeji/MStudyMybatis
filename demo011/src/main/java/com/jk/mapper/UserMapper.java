package com.jk.mapper;

import com.jk.bean.User;

public interface UserMapper {

	public User findUserById(Integer id);

	public void updateUserById(User user);
}
