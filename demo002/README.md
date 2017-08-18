mybatis-简单增删改查
--------------------
首先将公共的代理提出来新建一个类SqlSessionUtils.java:
```
package com.jk.utils;
import java.io.InputStream;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
public class SqlSessionUtils {
	// mybatis配置文件
	public static final String resource = "SqlMapperConfig.xml";
	public static SqlSession sqlSession;

	public static SqlSession getSqlSession() {

		if (null != sqlSession) {
			return sqlSession;
		}
		// 得到配置文件流
		ClassLoader classLoader = SqlSessionUtils.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream(resource);

		// 创建会话工厂，向build方法中传入配置信息
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

		// 通过工厂得到SqlSession
		sqlSession = sqlSessionFactory.openSession(true);
		return sqlSession;
	}

	public static void closeSqlSession() {
		if (null != sqlSession) {
			sqlSession.close();
		}
		sqlSession = null;
	}
}
```

一、插入记录
===
1、mapper文件 user.xml的写法：
```
<insert id="insertUser" parameterType="com.jk.bean.User">
	<selectKey keyProperty="id" order="AFTER" resultType="java.lang.Integer">
		select
		LAST_INSERT_ID()
	</selectKey>
	INSERT INTO user
	(username,birthday,sex,address) values
	(#{username},#{birthday},#{sex},#{address})
</insert>
```
对于
```
<selectKey keyProperty="id" order="AFTER" resultType="java.lang.Integer">
		select
		LAST_INSERT_ID()
	</selectKey>
```
的作用是将插入数据的主键返回，返回到user对象中，

SELECT LAST_INSERT_ID()：得到刚insert进去记录的主键值，只适用于自增主键

keyProperty：将查询到主键值设置到parameterType指定的对象的哪个属性,如将主键设置到user的id属性。

order：SELECT LAST_INSERT_ID()执行顺序，相对于insert语句来说它的执行顺序

resultType：指定SELECT LAST_INSERT_ID()的结果类型

2、测试：
```
    public void testInsert() {

        User user = new User();
        user.setUsername("小明");
        user.setBirthday("1993-12-20");
        user.setSex("男");
        user.setAddress("秀英区");

        int code = SqlSessionUtils.getSqlSession().insert("test.insertUser", user);
        SqlSessionUtils.getSqlSession().commit();

        System.out.println("获取插入数据的主键:" + user.getId());

        SqlSessionUtils.getSqlSession().close();
    }
```
二、删除
===
1、mapper文件user.xml的写法:
```
<delete id="deletetUserById" parameterType="java.lang.Integer">
    delete from user
    where id = #{id}
</delete>
```
2、测试：
```
public void testDelte() {
    int code = SqlSessionUtils.getSqlSession().insert("test.deletetUserById", 34);
    // 不要漏掉提交事务
    SqlSessionUtils.getSqlSession().commit();

    SqlSessionUtils.getSqlSession().close();
}
```
三、更新
===
1、mapper文件user.xml的写法:
```
<update id="updateUser" parameterType="com.jk.bean.User">
    update user set
    username=#{username} where id=#{id}
</update>
```
2、测试：
```
public void testUpdate() {
    User user = new User();
    user.setId(36);
    user.setUsername("小明明明");

    int code = SqlSessionUtils.getSqlSession().update("test.updateUser", user);
    // 不要漏掉提交事务
    SqlSessionUtils.getSqlSession().commit();

    SqlSessionUtils.getSqlSession().close();
}
```
四、查询单个
===
1、mapper文件user.xml的写法:
```
<select id="selectUserById" parameterType="java.lang.Integer"
    resultType="com.jk.bean.User">
    select * from user where id=#{id}
</select>
```
2、测试：
```
public void testSelectUserById() {
    User user = SqlSessionUtils.getSqlSession().selectOne("test.selectUserById", 38);
    System.out.println("user=" + user);
    SqlSessionUtils.getSqlSession().close();
}
```
五、查询多个
===
1、mapper文件user.xml的写法:
```
《!--查询多个返回值也是这么写-->
<select id="selectUsers" resultType="com.jk.bean.User">
    select * from user
</select>
```
2、测试：
```
public void testSelectUsers() {
    List<User> users = SqlSessionUtils.getSqlSession().selectList("test.selectUsers");
    System.out.println("users=" + users);
    SqlSessionUtils.getSqlSession().close();
}
```

六、使用#{}和${}的区别：
===
```
#{}表示一个占位符号，#{}接收输入参数，类型可以是简单类型，pojo、hashmap。
如果接收简单类型，#{}中可以写成value或其它名称。
#{}接收pojo对象值，通过OGNL读取对象中的属性值，通过属性.属性.属性...的方式获取对象属性值。
```
```
${}表示一个拼接符号，会引用sql注入，所以不建议使用${}。
${}接收输入参数，类型可以是简单类型，pojo、hashmap。
如果接收简单类型，${}中只能写成value。
${}接收pojo对象值，通过OGNL读取对象中的属性值，通过属性.属性.属性...的方式获取对象属性值。
```


