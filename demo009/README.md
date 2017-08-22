mybatis-高级映射之多对多（resultMap方式）
---

一、表结构：
===
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo009/preview/demo007-1.png)

二、查询用户以及用户购买的商品信息。一个用户可以购买多种商品，一种商品可以被一对多用户购买。多对多（resultMap方式）。
===
1、sql查询语句,t4.id items_id 取了个别名，映射时列为别名：
```
SELECT t1.*,t4.id items_id,t4.itemsname,t4.price,t4.detail FROM t_user t1,orders t2,orderdetail t3,items t4 WHERE t1.id=t2.user_id AND t3.orders_id=t2.id AND t3.items_id=t4.id ORDER BY id ASC;
```
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo009/preview/demo009-1.png)

2、定义接收结果集的java类UserItems，一个用户可以购买多种商品，加上private List<Items> items;属性：
```
public class UserItems {

	private Integer id;
	private String username;
	private String sex;
	private String birthday;
	private String address;

	private List<Items> items;
	///set get方法要写上
}
```
3、编写mapper.xml文件，定义resultMap：
```
<!-- 定义查询用户信息及用户购买的商品的 resultMap,将整个的查询结果映射到com.jk.result.UserItems中 -->
<resultMap type="com.jk.result.UserItems" id="OrdersUserResultMap">
    <!-- 配置映射的用户信息 -->
    <!-- 
        id:查询列中的唯一标识,订单信息中的唯一标识，如果多列组成唯一标识(如：一般数据库设计中的字典表 使用联合主键)，就需要配置多个id 
        column:订单信息的唯一标识 列 
        property:订单信息的唯一标识列所映射到orders中的那个属性(假如：数据库中orders表中的主键为orders_id,而实体属性名称为ordersId, 
        则这个配置应为<id column="orders_id" property="ordersId"/>,类似hibernate实体映射文件配置)。
     -->
    <id column="id" property="id" />

    <result column="username" property="username" />

    <result column="sex" property="sex" />

    <result column="birthday" property="birthday" />

    <result column="address" property="address" />

    <!-- 
         一个用户可能会购买多个商品：要使用collection映射
        collection:对关联查询到的多条记录映射到集合中
        property:将关联查询到的多条记录映射到orders类的那个属性
        ofType:指定映射的集合属性中pojo的类型
    -->
    <collection  property="items" ofType="com.jk.bean.Items">
        <!-- 
             id:关联查询用户的唯一标识 
             column:指定唯一标识用户信息的列 
        -->
        <id column="items_id" property="id" />
        <result column="itemsname" property="itemsname" />
        <result column="price" property="price" />
        <result column="detail" property="detail" />
        <result column="pic" property="pic" />
        <result column="createtime" property="createtime" />
    </collection>
</resultMap>
```
4、编写mapper.xml文件，通过id引用定义的resultMap：
```
<!-- 查询订单，关联查询用户信息,使用resultMap实现 -->
<select id="selectOrderAndUser2" resultMap="OrdersUserResultMap">
    SELECT t1.*,t4.id items_id,t4.itemsname,t4.price,t4.detail FROM t_user t1,orders t2,orderdetail t3,items t4 WHERE t1.id=t2.user_id AND t3.orders_id=t2.id AND t3.items_id=t4.id ORDER BY id ASC;
</select>
```
5、编写mapper接口，会有多个User返回返回值为List：
```
public interface UserMapper {

	public List<UserItems> selectOrderAndUser2();
}
```
6、测试代码:
```
public void testSelectOrderAndUser2() {
    //// 获取SqlSession，通过SqlSession获取对应的接口
    UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
    List<UserItems> orderUser = userMapper.selectOrderAndUser2();
    for (UserItems orderUser2 : orderUser) {
        System.out.println(orderUser2.toString());
    }
    SqlSessionUtils.getSqlSession().close();
}
```
打印结果和查询的结果对比是正确的，id=1的订单有两条明细：
```
UserItems[
    id=1,
    username=王五,
    sex=2,
    birthday=null,
    address=null,
    items=[
        Items[
            id=1,
            itemsname=台式机,
            price=3000,
            detail=该电脑质量非常好！,
            pic=null,
            createtime=null
        ],
        Items[
            id=2,
            itemsname=笔记本,
            price=6000,
            detail=笔记本性能好，质量好！,
            pic=null,
            createtime=null
        ],
        Items[
            id=3,
            itemsname=背包,
            price=200,
            detail=名牌背包，容量大质量好！,
            pic=null,
            createtime=null
        ]
    ]
]
UserItems[
    id=2,
    username=张三,
    sex=1,
    birthday=2014-07-10,
    address=北京市,
    items=[
        Items[
            id=2,
            itemsname=笔记本,
            price=6000,
            detail=笔记本性能好，质量好！,
            pic=null,
            createtime=null
        ]
    ]
]
UserItems[
    id=3,
    username=张小明,
    sex=1,
    birthday=null,
    address=河南郑州,
    items=[
        Items[
            id=3,
            itemsname=背包,
            price=200,
            detail=名牌背包，容量大质量好！,
            pic=null,
            createtime=null
        ]
    ]
]
UserItems[
    id=4,
    username=陈小明,
    sex=1,
    birthday=null,
    address=河南郑州,
    items=[
        Items[
            id=2,
            itemsname=笔记本,
            price=6000,
            detail=笔记本性能好，质量好！,
            pic=null,
            createtime=null
        ]
    ]
]
UserItems[
    id=5,
    username=张三丰,
    sex=1,
    birthday=null,
    address=河南郑州,
    items=[
        Items[
            id=1,
            itemsname=台式机,
            price=3000,
            detail=该电脑质量非常好！,
            pic=null,
            createtime=null
        ]
    ]
]
UserItems[
    id=6,
    username=陈小明,
    sex=1,
    birthday=null,
    address=河南郑州,
    items=[
        Items[
            id=2,
            itemsname=笔记本,
            price=6000,
            detail=笔记本性能好，质量好！,
            pic=null,
            createtime=null
        ]
    ]
]
```