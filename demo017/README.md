mybatis-基于注解高级查询(一对多)
---
表结构：
===
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo017/preview/demo007-1.png)

一、一对多查询，查询用户并查询用户的订单，一个用户可以下多个订单：
===
1、sql查询语句：
```
SELECT t1.*,t2.* FROM t_user t1,orders t2 WHERE t1.id=t2.user_id;
```
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo017/preview/demo017-1.png)

2、根据查询结果，定义TUserOrders类用于接收查询的结果，一个用户可以下多个订单，加一个属性private List<Orders> orders;
```
public class TUserOrders {
	private Integer id;

	private String username;

	private String birthday;

	private String sex;

	private String address;

	private List<Orders> orders;
	
	//set get方法
}
```
Orders.java:
```
public class Orders {
    private Integer id;

    private Integer userId;

    private String number;

    private Date createtime;

    private String note;
    
    //set get方法
}
```
3、定义mapper接口，一个用户有多个订单，用@Many关联：
```
public interface TUserMapper {

	/**
	 * 根据用户ID查询用户订单
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
	 * 查询用户，用@Many关联用户的多个订单
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
```
4、测试代码：
```
public void testSelectItemsTUser() {
    //// 获取SqlSession，通过SqlSession获取对应的接口
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();

    TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

    List<TUserOrders> tUserOrders = userMapper.selectTUserOrders();
    for (TUserOrders tUserOrder : tUserOrders) {
        System.out.println("tUserOrder=" + tUserOrder);
    }

    sqlSession.close();
}
```
5、打印结果:
```
2017-08-24 08:51:53  [ main:142 ] - [ DEBUG ]  Openning JDBC Connection
2017-08-24 08:51:55  [ main:1903 ] - [ DEBUG ]  Created connection 1648791923.
2017-08-24 08:51:55  [ main:2081 ] - [ DEBUG ]  Setting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:56  [ main:2258 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:56  [ main:2260 ] - [ DEBUG ]  ==>  Preparing: SELECT * from t_user 
2017-08-24 08:51:56  [ main:2313 ] - [ DEBUG ]  ==> Parameters: 
2017-08-24 08:51:56  [ main:2504 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:56  [ main:2504 ] - [ DEBUG ]  ==>  Preparing: SELECT * from orders WHERE user_id=? 
2017-08-24 08:51:56  [ main:2505 ] - [ DEBUG ]  ==> Parameters: 1(Integer)
2017-08-24 08:51:56  [ main:2687 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:56  [ main:2687 ] - [ DEBUG ]  ==>  Preparing: SELECT * from orders WHERE user_id=? 
2017-08-24 08:51:56  [ main:2687 ] - [ DEBUG ]  ==> Parameters: 2(Integer)
2017-08-24 08:51:56  [ main:2866 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:56  [ main:2866 ] - [ DEBUG ]  ==>  Preparing: SELECT * from orders WHERE user_id=? 
2017-08-24 08:51:56  [ main:2866 ] - [ DEBUG ]  ==> Parameters: 3(Integer)
2017-08-24 08:51:56  [ main:3050 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:56  [ main:3050 ] - [ DEBUG ]  ==>  Preparing: SELECT * from orders WHERE user_id=? 
2017-08-24 08:51:56  [ main:3051 ] - [ DEBUG ]  ==> Parameters: 4(Integer)
2017-08-24 08:51:56  [ main:3250 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:56  [ main:3250 ] - [ DEBUG ]  ==>  Preparing: SELECT * from orders WHERE user_id=? 
2017-08-24 08:51:56  [ main:3251 ] - [ DEBUG ]  ==> Parameters: 5(Integer)
2017-08-24 08:51:57  [ main:3429 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:57  [ main:3429 ] - [ DEBUG ]  ==>  Preparing: SELECT * from orders WHERE user_id=? 
2017-08-24 08:51:57  [ main:3430 ] - [ DEBUG ]  ==> Parameters: 6(Integer)
2017-08-24 08:51:57  [ main:3609 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:57  [ main:3609 ] - [ DEBUG ]  ==>  Preparing: SELECT * from orders WHERE user_id=? 
2017-08-24 08:51:57  [ main:3611 ] - [ DEBUG ]  ==> Parameters: 7(Integer)
2017-08-24 08:51:57  [ main:3792 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:57  [ main:3793 ] - [ DEBUG ]  ==>  Preparing: SELECT * from orders WHERE user_id=? 
2017-08-24 08:51:57  [ main:3793 ] - [ DEBUG ]  ==> Parameters: 8(Integer)
2017-08-24 08:51:57  [ main:3969 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:57  [ main:3970 ] - [ DEBUG ]  ==>  Preparing: SELECT * from orders WHERE user_id=? 
2017-08-24 08:51:57  [ main:3970 ] - [ DEBUG ]  ==> Parameters: 9(Integer)
2017-08-24 08:51:57  [ main:4156 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:57  [ main:4156 ] - [ DEBUG ]  ==>  Preparing: SELECT * from orders WHERE user_id=? 
2017-08-24 08:51:57  [ main:4156 ] - [ DEBUG ]  ==> Parameters: 10(Integer)
2017-08-24 08:51:58  [ main:4347 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:58  [ main:4347 ] - [ DEBUG ]  ==>  Preparing: SELECT * from orders WHERE user_id=? 
2017-08-24 08:51:58  [ main:4348 ] - [ DEBUG ]  ==> Parameters: 11(Integer)
2017-08-24 08:51:58  [ main:4524 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@62469173]
2017-08-24 08:51:58  [ main:4524 ] - [ DEBUG ]  ==>  Preparing: SELECT * from orders WHERE user_id=? 
2017-08-24 08:51:58  [ main:4525 ] - [ DEBUG ]  ==> Parameters: 14(Integer)
tUserOrder=TUser [id=1, username=张明明, birthday=, sex=2, address=null, orders=[Orders [Hash = 9065594, id=1, userId=1, number=1000010, createtime=Thu Jun 04 13:22:35 CST 2015, note=null], Orders [Hash = 881478763, id=2, userId=1, number=1000011, createtime=Wed Jul 08 13:22:41 CST 2015, note=null]]]
tUserOrder=TUser [id=2, username=张三, birthday=, sex=1, address=北京市, orders=[Orders [Hash = -687480644, id=3, userId=2, number=1000012, createtime=Fri Jul 17 14:13:23 CST 2015, note=null]]]
tUserOrder=TUser [id=3, username=张小明, birthday=null, sex=1, address=河南郑州, orders=[Orders [Hash = 1376439964, id=4, userId=3, number=1000012, createtime=Thu Jul 16 18:13:23 CST 2015, note=null]]]
tUserOrder=TUser [id=4, username=陈小明, birthday=null, sex=1, address=神剑山庄, orders=[Orders [Hash = -1189390852, id=5, userId=4, number=1000012, createtime=Wed Jul 15 19:13:23 CST 2015, note=null]]]
tUserOrder=TUser [id=5, username=张三丰, birthday=null, sex=1, address=河南郑州, orders=[Orders [Hash = 204913884, id=6, userId=5, number=1000012, createtime=Tue Jul 14 17:13:23 CST 2015, note=null]]]
tUserOrder=TUser [id=6, username=陈小明, birthday=null, sex=1, address=河南郑州, orders=[Orders [Hash = 1710850364, id=7, userId=6, number=1000012, createtime=Mon Jul 13 16:13:23 CST 2015, note=null]]]
tUserOrder=TUser [id=7, username=王五, birthday=null, sex=null, address=null, orders=[]]
tUserOrder=TUser [id=8, username=小小, birthday=, sex=2, address=滨海大道23号, orders=[]]
tUserOrder=TUser [id=9, username=小B, birthday=, sex=2, address=北京, orders=[]]
tUserOrder=TUser [id=10, username=上官飞燕, birthday=, sex=1, address=神剑山庄, orders=[]]
tUserOrder=TUser [id=11, username=小D, birthday=, sex=2, address=北京, orders=[]]
tUserOrder=TUser [id=14, username=古树慕容, birthday=null, sex=null, address=侨中路, orders=[]]
```