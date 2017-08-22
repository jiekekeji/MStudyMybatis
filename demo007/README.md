mybatis-高级映射之一对一（resultType方式和resultMap方式）
---

一、表结构：
===
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo007/preview/demo007-1.png)

二、查询订单信息，关联查询用户信息.一个订单只能由一个用户创建，一对一（resultType方式）
===
1、sql查询语句：
```
SELECT t1.*,t2.* FROM orders t1,t_user t2 WHERE t1.user_id=t2.id;
```
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo007/preview/demo007-2.png)

2、结果集中包含了Orders和User的字段，然后定义类OrderUser.java，OrderUser继承Orders：
```
public class OrderUser extends Orders {

	// 属性名和数据库表名一一对应
	//将user的属性拷贝过来
	private String username;
	private String sex;
	private String birthday;
	private String address;

	public OrderUser() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "OrderUser [username=" + username + ", sex=" + sex + ", birthday=" + birthday + ", address=" + address
				+ "]";
	}
}
```
3、编写mapper.xml文件，resultType值为刚才我们定义的com.jk.result.OrderUser:
```
<select id="selectOrderAndUser1" resultType="com.jk.result.OrderUser">
    SELECT t1.*,t2.* FROM orders t1,t_user t2 WHERE t1.user_id=t2.id;
</select>
```
4、编写mapper接口：
```
public interface UserMapper {

	public List<OrderUser> selectOrderAndUser1();
}
```
5、测试代码：
```
public void testSelectOrderAndUser1() {
    //// 获取SqlSession，通过SqlSession获取对应的接口
    UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
    List<OrderUser> orderUser = userMapper.selectOrderAndUser1();
    for (OrderUser orderUser2 : orderUser) {
        System.out.println("=======start===========");
        System.out.println("id=" + orderUser2.getId());
        System.out.println("User_id=" + orderUser2.getUser_id());
        System.out.println("Number=" + orderUser2.getNumber());
        System.out.println("Username=" + orderUser2.getUsername());
        System.out.println("Sex=" + orderUser2.getSex());
        System.out.println("=======end===========");
    }
    SqlSessionUtils.getSqlSession().close();
}
```

三、查询订单信息，关联查询用户信息.一个订单只能由一个用户创建，一对一（resultMap方式）
===
1、sql查询语句：
```
SELECT t1.*,t2.* FROM orders t1,t_user t2 WHERE t1.user_id=t2.id;
```
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo007/preview/demo007-2.png)

2、定义接收结果集的java类Orders，Orders除了有Orders的属性外，还持有User：
```
public class Orders {

	private Integer id;
	private Integer user_id;
	private String number;
	private String createtime;
	private String note;
	//在这持有User
	private User user;
	
	///set get方法要写上
}
```
3、编写mapper.xml文件，定义resultMap：
```
<!-- 定义查询订单关联用户的 resultMap,将整个的查询结果映射到com.jk.result.Orders中 -->
<resultMap type="com.jk.result.Orders" id="OrdersUserResultMap">
    <!-- 配置映射的订单信息 -->
    <!-- 
        id:查询列中的唯一标识,订单信息中的唯一标识，如果多列组成唯一标识(如：一般数据库设计中的字典表 使用联合主键)，就需要配置多个id 
        column:订单信息的唯一标识 列 
        property:订单信息的唯一标识列所映射到orders中的那个属性(假如：数据库中orders表中的主键为orders_id,而实体属性名称为ordersId, 
        则这个配置应为<id column="orders_id" property="ordersId"/>,类似hibernate实体映射文件配置)。
    -->
    <id column="id" property="id" />
    
    <result column="user_id" property="user_id" />
    
    <result column="number" property="number" />
    
    <result column="createtime" property="createtime" />

    <result column="note" property="note" />

    <!-- 配置映射的关联用户信息 -->
    <!--
         association:用于映射关联查询单个对象的信息 
         property:要将关联查询的用户信息映射到Orders中那个属性 20
    -->
    <association property="user" javaType="com.jk.bean.User">
        <!-- 
             id:关联查询用户的唯一标识 
             column:指定唯一标识用户信息的列 
             property:映射到user的那个属性 
        -->
        <id column="user_id" property="id" />
        <result column="username" property="username" />
        <result column="sex" property="sex" />
        <result column="address" property="address" />
    </association>
</resultMap>
```
4、编写mapper.xml文件，通过id引用定义的resultMap：
```
<!-- 查询订单，关联查询用户信息,使用resultMap实现 -->
<select id="selectOrderAndUser2" resultMap="OrdersUserResultMap">
    SELECT t1.*,t2.username,t2.sex,t2.address FROM orders t1,t_user t2 WHERE t1.user_id=t2.id
</select>
```
5、编写mapper接口：
```
public interface UserMapper {

	public List<Orders> selectOrderAndUser2();
}
```
6、测试代码：
```
public void testSelectOrderAndUser2() {
    //// 获取SqlSession，通过SqlSession获取对应的接口
    UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
    List<Orders> orderUser = userMapper.selectOrderAndUser2();
    for (Orders orderUser2 : orderUser) {
        System.out.println("=======start===========");
        System.out.println("id=" + orderUser2.getId());
        System.out.println("User_id=" + orderUser2.getUser_id());
        System.out.println("Number=" + orderUser2.getNumber());
        System.out.println("Username=" + orderUser2.getUser().toString());
        System.out.println("=======end===========");
    }
    SqlSessionUtils.getSqlSession().close();
}
```