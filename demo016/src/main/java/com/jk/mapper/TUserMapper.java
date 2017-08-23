package com.jk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.jk.result.OrdersUser;

public interface TUserMapper {

	@Select("SELECT t1.*,t2.* FROM orders t1,t_user t2 WHERE t1.user_id=t2.id;")
	List<OrdersUser> selectItemsTUser();

}