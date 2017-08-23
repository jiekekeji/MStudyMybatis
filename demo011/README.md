mybatis-查询缓存
---

一、查询缓存
===
1.1、 一级缓存是sqlSession级别的缓存。在操作数据库时需要构造sqlSession对象，在对象中有一个数据结构(HashMap),用于存储缓存数据。不同的sqlSession之间的缓存　　区域(HashMap)是互不影响的。

1.2、二级缓存是mapper级别的缓存，多个sqlSession去操作同一个Mapper的sql语句，多个SqlSession可以公用二级缓存，二级缓存是跨sqlSession的。

![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo011/preview/demo011-1.png)

二、一级缓存
===
2.1、一级缓存的工作原理大概是这样(其他缓存也都差不多原理):
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo011/preview/demo011-2.png)

第一次查询的时候，先查看缓存中是否有值; 如果没有值，去查询数据库,查询到数据后放入缓存中，返回结果，完成一次查询；如果缓存中有值，直接返回结果；

第二次查询的时候，先查看缓存中是否有值;有值，直接返回结果；

如果sqlSession去执行commit操作（执行插入、更新、删除），清空SqlSession中的一级缓存；

2.2、测试代码(一级缓存默认是开启的)

mapper.xml、根据用户ID查询用户信息：
```
<mapper namespace="com.jk.mapper.UserMapper">
	<select id="findUserById" parameterType="int" resultType="com.jk.bean.User">
		select * from t_user where id=#{id}
	</select>
</mapper>
```
mapper接口:
```
public interface UserMapper {
	public User findUserById(Integer id);
}
```
测试代码:
```
public void testFindUserById() throws InterruptedException {
    SqlSession sqlSession;
    UserMapper userMapper;

    sqlSession = SqlSessionUtils.getSqlSession();
    userMapper = sqlSession.getMapper(UserMapper.class);

    //同一个sqlSession获取的mapper
    User user1 = userMapper.findUserById(3);
    System.out.println("user1=" + user1);

    Thread.sleep(6 * 1000);

    //同一个sqlSession获取的mapper
    User user2 = userMapper.findUserById(3);
    System.out.println("user2=" + user2);

    //sqlSession关闭
    sqlSession.close();
    System.out.println("sqlSession关闭,关闭之后重新获取sqlSession再查试试");

    //获取另一个sqlSession
    sqlSession = SqlSessionUtils.getSqlSession();
    userMapper = sqlSession.getMapper(UserMapper.class);
    
    User user3 = userMapper.findUserById(3);
    System.out.println("user3=" + user3);

}
```
打印结果:
```
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo011/preview/demo011-3.png)
```