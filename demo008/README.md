mybatis-高级映射之一对多（resultMap方式）
---

一、表结构：
===
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo008/preview/demo007-1.png)

二、查询订单及订单明细，一个订单有多个明细。一对多（resultMap方式）
===
1、sql查询语句,t2.id orderdetail_id取了个别名，映射时列为别名：
```
SELECT t1.*,t2.id orderdetail_id,t2.items_id,t2.items_num,t2.orders_id FROM orders t1,orderdetail t2 WHERE t1.id=t2.orders_id; 
```
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo008/preview/demo008-1.png)

2、定义接收结果集的java类Orders，一个订单有多个明细，加上private List<Orderdetail> orderdetails属性：
```
public class Orders {

    private Integer id;
    private Integer user_id;
    private String number;
    private String createtime;
    private String note;
    //一个订单有多个明细
    private List<Orderdetail> orderdetails;
	
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

    <!-- 
        关联订单明细信息 ,一个订单对应多个明细
        一个订单关联查询出了多条订单明细,要使用collection映射
        collection:对关联查询到的多条记录映射到集合中
        property:将关联查询到的多条记录映射到orders类的那个属性
        ofType:指定映射的集合属性中pojo的类型
    -->
    <collection  property="orderdetails" ofType="com.jk.bean.Orderdetail">
        <!-- 
             id:关联查询用户的唯一标识 
             column:指定唯一标识用户信息的列 
        -->
        <id column="orderdetail_id" property="id" />
        <result column="orders_id" property="orders_id" />
        <result column="items_id" property="items_id" />
        <result column="items_num" property="items_num" />
    </collection>
</resultMap>
```
4、编写mapper.xml文件，通过id引用定义的resultMap：
```
<!-- 查询订单，关联查询用户信息,使用resultMap实现 -->
<select id="selectOrderAndUser2" resultMap="OrdersUserResultMap">
    SELECT t1.*,t2.id orderdetail_id,t2.items_id,t2.items_num,t2.orders_id FROM orders t1,orderdetail t2 WHERE t1.id=t2.orders_id; 
</select>
```
5、编写mapper接口：
```
public interface UserMapper {
	public List<Orders> selectOrderAndUser2();
}
```
6、测试代码:
```
public void testSelectOrderAndUser2() {
    //// 获取SqlSession，通过SqlSession获取对应的接口
    UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
    List<Orders> orderUser = userMapper.selectOrderAndUser2();
    for (Orders orderUser2 : orderUser) {
        System.out.println(orderUser2.toString());
    }
    SqlSessionUtils.getSqlSession().close();
}
```
打印结果和查询的结果对比是正确的，id=1的订单有两条明细：
```
Orders[
    id=1,
    user_id=1,
    number=1000010,
    createtime=2015-06-0413: 22: 35.0,
    note=null,
    orderdetails=[
        Orderdetail[
            id=6,
            orders_id=1,
            items_id=1,
            items_num=1
        ],
        Orderdetail[
            id=7,
            orders_id=1,
            items_id=2,
            items_num=3
        ]
    ]
]
Orders[
    id=2,
    user_id=1,
    number=1000011,
    createtime=2015-07-0813: 22: 41.0,
    note=null,
    orderdetails=[
        Orderdetail[
            id=8,
            orders_id=2,
            items_id=3,
            items_num=4
        ]
    ]
]
Orders[
    id=3,
    user_id=2,
    number=1000012,
    createtime=2015-07-1714: 13: 23.0,
    note=null,
    orderdetails=[
        Orderdetail[
            id=9,
            orders_id=3,
            items_id=2,
            items_num=3
        ]
    ]
]
```

三、查询订单及订单明细，一个订单有多个明细。一对多，如果使用resultType方式方式那么多查询出来的结果有4条记录，将查询结果集按照数据库的列映射出来。
==