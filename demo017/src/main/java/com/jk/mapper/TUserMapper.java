package com.jk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.jk.pojo.Orders;
import com.jk.result.TUserOrders;

public interface TUserMapper {

	/**
	 * 根据用户ID查询用户信息
	 * 这里有列user_id和属性userId不一致，
	 * 所以用@Results映射结果
	 * 如果列和属性一直，不需要用@Results
	 * @param id
	 * @return
	 */
	@Select("SELECT * from orders WHERE user_id=#{id}")
	@Results({
		@Result(id=true,column="id",property="id"), 
		@Result(column="user_id",property="userId"),  
        @Result(column="number",property="number"),  
        @Result(column="createtime",property="createtime"), 
        @Result(column="note",property="note")
		})
	public Orders selectOrdersByUserId(Integer id);

	/**
	 * 查询订单信息， 返回值OrdersUser的属性user的值是通过selectTUserById查询的，注意看
	 * 
	 * @return
	 */
	@Select("SELECT * from t_user")
	@Results({ @Result(id = true, column = "id", property = "id"), @Result(column = "username", property = "username"),
			@Result(column = "birthday", property = "birthday"), @Result(column = "sex", property = "sex"),
			@Result(column = "address", property = "address"),
			@Result(column = "id", property = "orders", many = @Many(select = "com.jk.mapper.TUserMapper.selectOrdersByUserId") ) })
	List<TUserOrders> selectTUserOrders();

}