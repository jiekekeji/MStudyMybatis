mybatis-基于注解的动态SQL
---

一、表结构：
===
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo009/preview/demo007-1.png)

二、需求：查询订单信息关联查询用户信息，延迟加载用户信息：
===
1、先编写一个java类用来装订单信息和user信息Orders.java:
```
public class Orders {

	private Integer id;
	private Integer user_id;
	private String number;
	private String createtime;
	private String note;
	//user信息
	private User user;
	
	//set get别漏了
}
```
2、在mapper.xml中配置resultMap,通过association的select指定延迟加载需要执行的statement的id，查询的参数为association的column="user_id"：
```
<!-- 定义查询订单关联查询用户，用户信息按需延迟加载 的 resultMap定义,将整个的查询结果映射到com.jk.result.Orders中 -->
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
    <result column="createtime" property="createtime" />
    <result column="note" property="note" />

    <!-- 
              关联订单的用户信息，单个使用association 
        collection:对关联查询到的多条记录映射到集合中
        property:将关联查询到的多条记录映射到orders类的那个属性
        javaType:指定映射的集合属性中pojo的类型
        select：指定延迟加载需要执行的statement的id（是根据user_id查询用户信息的statement） 
        column：订单信息中关联用户信息查询的列，是user_id
    -->
    <association property="user" javaType="com.jk.bean.User" select="findUserById" column="user_id">
        <!-- 
             id:关联查询用户的唯一标识 
             column:指定唯一标识用户信息的列 
        -->
        <id column="id" property="id" />
        <result column="itemsname" property="itemsname" />
        <result column="price" property="price" />
        <result column="detail" property="detail" />
        <result column="pic" property="pic" />
        <result column="createtime" property="createtime" />
    </association >
</resultMap>
```

3、在mapper.xml中配置订单查询和用户查询的statement的id：
```
<!-- 查询订单信息 -->
<select id="selectOrderAndUser2" resultMap="OrdersUserResultMap">
        select * from orders
</select> 

<!-- 
   根据订单中的user_id查询用户信息,
   被
   <association property="user" javaType="com.jk.bean.User" select="findUserById" column="user_id">
   引用
   不需要编写该statement的接口
  association的select属性使用,
  association的select属性column="user_id"为参数
 -->
<select id="findUserById" parameterType="int" resultType="com.jk.bean.User" >
        select * from t_user where id=#{id}
</select>
```
4、mapper接口编写,只需编写查询订单的接口：
```
public interface UserMapper {

	public List<Orders> selectOrderAndUser2();
}
```
5、在mybatis的配置文件SqlMapperConfig.xml中配置setting节点，开启懒加载：
```
<!-- 全局参数的配置 -->
<settings>
    <!--打开延迟加载的开关 -->
    <setting name="lazyLoadingEnabled" value="true" />
    <!--将积极加载改为消极加载及按需加载 -->
    <setting name="aggressiveLazyLoading" value="false" />
</settings>
```
6、测试代码：
```
	public void testSelectOrderAndUser2() {
		//// 获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		List<Orders> orders = userMapper.selectOrderAndUser2();
		for (Orders orderUser2 : orders) {
			System.out.println(orderUser2.getUser().toString());
		}
		SqlSessionUtils.getSqlSession().close();
	}
```
打印结果，在需要使用到user的时候才回去查询user：
```
2017-08-22 17:04:27  [ main:197 ] - [ DEBUG ]  Openning JDBC Connection
2017-08-22 17:04:33  [ main:6586 ] - [ DEBUG ]  Created connection 393106457.
2017-08-22 17:04:34  [ main:6776 ] - [ DEBUG ]  Setting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@176e5419]
2017-08-22 17:04:34  [ main:6961 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@176e5419]
2017-08-22 17:04:34  [ main:6962 ] - [ DEBUG ]  ==>  Preparing: select * from orders 
2017-08-22 17:04:34  [ main:7001 ] - [ DEBUG ]  ==> Parameters: 
2017-08-22 17:04:34  [ main:7261 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@176e5419]
2017-08-22 17:04:34  [ main:7261 ] - [ DEBUG ]  ==>  Preparing: select * from t_user where id=? 
2017-08-22 17:04:34  [ main:7261 ] - [ DEBUG ]  ==> Parameters: 1(Integer)
User [id=1, username=王五, sex=2, birthday=null, address=null]
User [id=1, username=王五, sex=2, birthday=null, address=null]
2017-08-22 17:04:34  [ main:7448 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@176e5419]
2017-08-22 17:04:34  [ main:7448 ] - [ DEBUG ]  ==>  Preparing: select * from t_user where id=? 
2017-08-22 17:04:34  [ main:7449 ] - [ DEBUG ]  ==> Parameters: 2(Integer)
User [id=2, username=张三, sex=1, birthday=2014-07-10, address=北京市]
2017-08-22 17:04:35  [ main:8068 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@176e5419]
2017-08-22 17:04:35  [ main:8068 ] - [ DEBUG ]  ==>  Preparing: select * from t_user where id=? 
2017-08-22 17:04:35  [ main:8069 ] - [ DEBUG ]  ==> Parameters: 3(Integer)
User [id=3, username=张小明, sex=1, birthday=null, address=河南郑州]
2017-08-22 17:04:35  [ main:8263 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@176e5419]
2017-08-22 17:04:35  [ main:8264 ] - [ DEBUG ]  ==>  Preparing: select * from t_user where id=? 
2017-08-22 17:04:35  [ main:8264 ] - [ DEBUG ]  ==> Parameters: 4(Integer)
User [id=4, username=陈小明, sex=1, birthday=null, address=河南郑州]
2017-08-22 17:04:35  [ main:8462 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@176e5419]
2017-08-22 17:04:35  [ main:8462 ] - [ DEBUG ]  ==>  Preparing: select * from t_user where id=? 
2017-08-22 17:04:35  [ main:8463 ] - [ DEBUG ]  ==> Parameters: 5(Integer)
User [id=5, username=张三丰, sex=1, birthday=null, address=河南郑州]
2017-08-22 17:04:36  [ main:9069 ] - [ DEBUG ]  ooo Using Connection [com.mysql.jdbc.JDBC4Connection@176e5419]
2017-08-22 17:04:36  [ main:9069 ] - [ DEBUG ]  ==>  Preparing: select * from t_user where id=? 
2017-08-22 17:04:36  [ main:9070 ] - [ DEBUG ]  ==> Parameters: 6(Integer)
User [id=6, username=陈小明, sex=1, birthday=null, address=河南郑州]
2017-08-22 17:04:36  [ main:9262 ] - [ DEBUG ]  Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@176e5419]
2017-08-22 17:04:36  [ main:9262 ] - [ DEBUG ]  Returned connection 393106457 to pool.
```