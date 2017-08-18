package com.jk.mapper;

import java.util.List;

import com.jk.bean.User;

public interface UserMapper {

	public User selectUserById(Integer id);

	public int insertUser(User user);

	public int deletetUserById(Integer id);

	public int updateUser(User user);

	public List<User> selectUsers();
}
