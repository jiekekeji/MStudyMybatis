package com.jk.mapper;

import java.util.List;
import java.util.Map;

import com.jk.bean.User;

public interface UserMapper {

	public User selectUserById(Map<String, Object> map);

	public int insertUser(User user);

	public int deletetUserById(Integer id);

	public int updateUser(User user);

	public List<User> selectUsers();

	public int findUserCount();
	
	public User selectUserMapById(Integer id);
}
