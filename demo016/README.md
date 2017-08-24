mybatis-基于注解高级查询(一对一)
---
表结构：
===
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo016/preview/demo007-1.png)

一、一对一查询，查询订单信息关联查询用户信息：
===
1、sql查询语句：
```
SELECT t1.*,t2.* FROM orders t1,t_user t2 WHERE t1.user_id=t2.id;
```
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo007/preview/demo007-2.png)

2、根据查询结果，定义OrdersUser类用于接收查询的结果，OrdersUser除了有Orders的属性外，还持有TUser：
```
public class OrdersUser {
	private Integer id;

	private Integer userId;

	private String number;

	private Date createtime;

	private String note;

	private TUser user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number == null ? null : number.trim();
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "OrdersUser [id=" + id + ", userId=" + userId + ", number=" + number + ", createtime=" + createtime
				+ ", note=" + note + ", user=" + user + "]";
	}

}
```
TUser.java:
```
public class TUser {
	private Integer id;

	private String username;

	private String birthday;

	private String sex;

	private String address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}
```
3、定义mapper接口：
```
public interface TUserMapper {
    
	/**
	 * 根据用户ID查询用户信息
	 * @param id
	 * @return
	 */
	@Select("SELECT * from t_user WHERE id=#{id}")
	public TUser selectTUserById(Integer id);

	/**
	 * 查询订单信息，
	 * 返回值OrdersUser的属性user的值是通过selectTUserById查询的，注意看
	 * @return
	 */
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
```
4、测试代码：
```
public void testSelectItemsTUser() {
    //// 获取SqlSession，通过SqlSession获取对应的接口
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();

    TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

    List<OrdersUser> tUsers = userMapper.selectItemsTUser();
    for (OrdersUser itemsUser : tUsers) {
        System.out.println("itemsUser=" + itemsUser);
    }

    sqlSession.close();
}
```
5、打印结果:
```
2017-08-24 08:07:17  [ main:141 ] - [ DEBUG ]  Openning JDBC Connection
2017-08-24 08:07:19  [ main:1942 ] - [ DEBUG ]  Created connection 407046192.
2017-08-24 08:07:19  [ main:2117 ] - [ DEBUG ]  Setting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@18430830]
2017-08-24 08:07:19  [ main:2297 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@18430830]
2017-08-24 08:07:19  [ main:2299 ] - [ DEBUG ]  ==>  Preparing: SELECT * from orders 
2017-08-24 08:07:19  [ main:2337 ] - [ DEBUG ]  ==> Parameters: 
2017-08-24 08:07:20  [ main:2540 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@18430830]
2017-08-24 08:07:20  [ main:2540 ] - [ DEBUG ]  ==>  Preparing: SELECT * from t_user WHERE id=? 
2017-08-24 08:07:20  [ main:2540 ] - [ DEBUG ]  ==> Parameters: 1(Integer)
2017-08-24 08:07:20  [ main:2721 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@18430830]
2017-08-24 08:07:20  [ main:2721 ] - [ DEBUG ]  ==>  Preparing: SELECT * from t_user WHERE id=? 
2017-08-24 08:07:20  [ main:2721 ] - [ DEBUG ]  ==> Parameters: 2(Integer)
2017-08-24 08:07:20  [ main:2900 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@18430830]
2017-08-24 08:07:20  [ main:2900 ] - [ DEBUG ]  ==>  Preparing: SELECT * from t_user WHERE id=? 
2017-08-24 08:07:20  [ main:2901 ] - [ DEBUG ]  ==> Parameters: 3(Integer)
2017-08-24 08:07:20  [ main:3082 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@18430830]
2017-08-24 08:07:20  [ main:3083 ] - [ DEBUG ]  ==>  Preparing: SELECT * from t_user WHERE id=? 
2017-08-24 08:07:20  [ main:3083 ] - [ DEBUG ]  ==> Parameters: 4(Integer)
2017-08-24 08:07:20  [ main:3265 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@18430830]
2017-08-24 08:07:20  [ main:3265 ] - [ DEBUG ]  ==>  Preparing: SELECT * from t_user WHERE id=? 
2017-08-24 08:07:20  [ main:3265 ] - [ DEBUG ]  ==> Parameters: 5(Integer)
2017-08-24 08:07:21  [ main:3445 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@18430830]
2017-08-24 08:07:21  [ main:3445 ] - [ DEBUG ]  ==>  Preparing: SELECT * from t_user WHERE id=? 
2017-08-24 08:07:21  [ main:3445 ] - [ DEBUG ]  ==> Parameters: 6(Integer)
itemsUser=OrdersUser [id=1, userId=1, number=1000010, createtime=Thu Jun 04 13:22:35 CST 2015, note=null, user=TUser [Hash = 558362958, id=1, username=张明明, birthday=, sex=2, address=null]]
itemsUser=OrdersUser [id=2, userId=1, number=1000011, createtime=Wed Jul 08 13:22:41 CST 2015, note=null, user=TUser [Hash = 558362958, id=1, username=张明明, birthday=, sex=2, address=null]]
itemsUser=OrdersUser [id=3, userId=2, number=1000012, createtime=Fri Jul 17 14:13:23 CST 2015, note=null, user=TUser [Hash = 1661449268, id=2, username=张三, birthday=, sex=1, address=北京市]]
itemsUser=OrdersUser [id=4, userId=3, number=1000012, createtime=Thu Jul 16 18:13:23 CST 2015, note=null, user=TUser [Hash = -952409885, id=3, username=张小明, birthday=null, sex=1, address=河南郑州]]
itemsUser=OrdersUser [id=5, userId=4, number=1000012, createtime=Wed Jul 15 19:13:23 CST 2015, note=null, user=TUser [Hash = -338855119, id=4, username=陈小明, birthday=null, sex=1, address=神剑山庄]]
itemsUser=OrdersUser [id=6, userId=5, number=1000012, createtime=Tue Jul 14 17:13:23 CST 2015, note=null, user=TUser [Hash = -153058947, id=5, username=张三丰, birthday=null, sex=1, address=河南郑州]]
itemsUser=OrdersUser [id=7, userId=6, number=1000012, createtime=Mon Jul 13 16:13:23 CST 2015, note=null, user=TUser [Hash = -432953026, id=6, username=陈小明, birthday=null, sex=1, address=河南郑州]]
2017-08-24 08:07:21  [ main:3630 ] - [ DEBUG ]  Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@18430830]
2017-08-24 08:07:21  [ main:3631 ] - [ DEBUG ]  Returned connection 407046192 to pool.
```