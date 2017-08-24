package com.jk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.jk.pojo.TUser;
import com.jk.result.OrdersUser;

public interface TUserMapper {
    
	/**
	 * 根据用户ID查询用户信息
	 * @param id
	 * @return
	 */
	@Select("SELECT * from t_user WHERE id=#{id}")
	public TUser selectTUserById(Integer id);

	@Select("SELECT * from orders")
	@Results({
		@Result(id=true,column="id",property="id"), 
		@Result(column="user_id",property="userId"),  
        @Result(column="number",property="number"),  
        @Result(column="createtime",property="createtime"), 
        @Result(column="note",property="note"), 
        @Result(column="user_id",property="user",one=@One(  
                select="com.jk.mapper.TUserMapper.selectTUserById")) 
	})
	List<OrdersUser> selectItemsTUser();

}