mybatis-输入映射和输出映射
---

一、输入类型：通过parameterType指定输入参数的类型，类型可以是简单类型、hashmap、pojo的包装类型。
==
1、简单数据类型：parameterType="java.lang.Integer"
```
<select id="selectUserById" parameterType="java.lang.Integer"
	resultType="user">
	select * from user where id=#{id}
</select>
```
2、hashmap数据类型：
mapper.xml:
```
<select id="selectUserById" parameterType="java.util.Map"
	resultType="user">
	<!--通过map的key取值，如这里去id和username  -->
	select * from user where id=#{id} and username=#{username}
</select>
```
mapper接口：
```
public interface UserMapper {

  public User selectUserById(Map<String, Object> map);
}
```
传参数：
```
public void testSelectUserById() {
    //获取SqlSession，通过SqlSession获取对应的接口
	UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
	
	Map map=new HashMap<String, Object>();
	map.put("id", 38);
	map.put("username", "小明");
	
	User user = userMapper.selectUserById(map);
	
	System.out.println("userone=" + user);
	SqlSessionUtils.getSqlSession().close();
}
```
3、类 类型数据：
mapper.xml：
```
<update id="updateUser" parameterType="com.jk.bean.User">
    <!--通过类的属性名取值，如这里User有id和usernmae等属性  -->
	update user set username=#{username} where id=#{id}
</update>
```
mapper接口：
```
public interface UserMapper {

	public int updateUser(User user);
}
```
传参数：
```
public void testUpdate() {
	User user = new User();
	user.setId(39);
	user.setUsername("小明明明");
    //获取SqlSession，通过SqlSession获取对应的接口
	UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
	userMapper.updateUser(user);
	// 不要漏掉提交事务
	SqlSessionUtils.getSqlSession().commit();

	SqlSessionUtils.getSqlSession().close();
}
```

二、输出映射：使用resultType进行输出映射。
===

1、输出简单的数据类型。
mapper.xml：
```
<select id="findUserCount" resultType="int">
	select count(*) from user 
</select>
```
mapper接口：
```
public interface UserMapper {

	public int findUserCount();
}
```
测试返回值:
```
public void testFindUserCount() {
    ////获取SqlSession，通过SqlSession获取对应的接口
	UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
	int count = userMapper.findUserCount();
	System.out.println("count=" + count);
	SqlSessionUtils.getSqlSession().close();
}
```
2、输出类 类型数据。如下输出的类型为:com.jk.bean.User
mapper.xml:
```
<select id="selectUserById" parameterType="int"
	resultType="com.jk.bean.User">
	select * from user where id=#{id}
</select>
```
mapper接口:
```
public interface UserMapper {

   public User selectUserById(Integer id);
}
```
测试:
```
public void testSelectUserById() {
    //获取SqlSession，通过SqlSession获取对应的接口
	UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
	User user = userMapper.selectUserById(38);
	System.out.println("userone=" + user);
	SqlSessionUtils.getSqlSession().close();
}
```
3、输出列表List 类型数据。和第2 输出类差不多：mapper.xml不变，结果的返回类型更改为List<User>
mapper.xml:
```
	<select id="selectUsers" resultType="com.jk.bean.User">
		select * from user
	</select>
```
mapper接口:
```
public interface UserMapper {

   public List<User> selectUsers();
}
```
测试:
```
public void testSelectUsers() {
    ////获取SqlSession，通过SqlSession获取对应的接口
   UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
	
   List<User> users = userMapper.selectUsers();
	
   System.out.println("users=" + users);
   SqlSessionUtils.getSqlSession().close();
}
```

4、输出使用resultMap而不是使用resultType,如：
```
<select id="selectUserMapById" parameterType="int" resultMap="userResultMap">
    select * from user where id=#{id}
</select>
```

步骤1：在mapper.xml文件中定义resultMap：
```
<resultMap type="com.jk.bean.User" id="userResultMap">
    <id column="id" property="id" />
    <result column="username" property="username" />
</resultMap>
```

对该配置的解释


```
<!--
   id：表示唯一的resultMap，
   type：表示该resultMap最终映射的java对象类型,可以使用别名 
   如这里把userResultMap这个resultMap映射成com.jk.bean.User类型
   -->
<resultMap type="com.jk.bean.User" id="userResultMap">
    <!-- id：表示查询结果集中唯一标识
        column:查询出来的列名 
        property:type指定的pojo类型中的属性名
         最终resultMap对column和property作一个映射关系 (对应关系)
     -->
    <id column="id" property="id" />
    <!--
       result:对普通名映射定义
       column:查询出来的列名 
       property:type指定的pojo类型中的属性名 
       最终resultMap对column和property作一个映射关系 (对应关系)
     -->
    <result column="username" property="username" />
</resultMap>
```

步骤2：使用步骤1定义的resultMap，通过id引用：
```
	<select id="selectUserMapById" parameterType="int" resultMap="userResultMap">
		select * from user where id=#{id}
	</select>

```

步骤2：mapper接口，返回类型为userResultMap的type值com.jk.bean.User：
```
public interface UserMapper {

	public User selectUserMapById(Integer id);
}

```
测试：
```
public void testSelectUserMapById() {
    // 获取SqlSession，通过SqlSession获取对应的接口
    UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);

    User user = userMapper.selectUserMapById(39);

    System.out.println("user=" + user);
    SqlSessionUtils.getSqlSession().close();
}

```