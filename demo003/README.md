mybatis-mapper代理开发方法-开发规范
---
一、开发规范
===
1、映射文件mapper.xml中namespace等于mapper接口地址。
===

如在com.jk.mapper下新建了接口UserMapper.java，
```
package com.jk.mapper;
public interface UserMapper {

}
```
那么UserMapper.xml的命名空间为：com.jk.mapper.UserMapper：
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--命名空间(namespace)，作用就是对sql进行分类的管理，sql隔离 -->
<mapper namespace="com.jk.mapper.UserMapper">

</mapper>
```

2、mapper.java接口中的方法名和mapper.xml中statement的id一致。
===
如UserMapper.xml中定义了id=selectUserById 的sql语句：
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--命名空间(namespace)，作用就是对sql进行分类的管理，sql隔离 -->
<mapper namespace="com.jk.mapper.UserMapper">
	<select id="selectUserById" parameterType="java.lang.Integer"
		resultType="com.jk.bean.User">
		select * from user where id=#{id}
	</select>
</mapper>
```
那么接口UserMapper.java中的方法也必须为selectUserById：
```
package com.jk.mapper;
import com.jk.bean.User;
public interface UserMapper {

	public User selectUserById(Integer id);

}
```
3、接口UserMapper.java中的方法输入参数类型和UserMapper.xml中statement的parameterType指定的类型一致。
===
如UserMapper.xml中statement的id为selectUserById的parameterType类型为java.lang.Integer
```
<select id="selectUserById" parameterType="java.lang.Integer"
    resultType="com.jk.bean.User">
    select * from user where id=#{id}
</select>
```
那么接口UserMapper.java中的方法selectUserById的参数也必须为java.lang.Integer类型：
```
package com.jk.mapper;
import com.jk.bean.User;
public interface UserMapper {

	public User selectUserById(Integer id);

}
```
4、接口UserMapper.java中的方法返回值类型和UserMapper.xml中statement的resultType指定的类型一致。
===
如UserMapper.xml中statement的id为selectUserById的resultType类型为com.jk.bean.User：
```
<select id="selectUserById" parameterType="java.lang.Integer"
    resultType="com.jk.bean.User">
    select * from user where id=#{id}
</select>
```
那么接口UserMapper.java中的方法selectUserById的返回值也必须为com.jk.bean.User类型：
```
package com.jk.mapper;
import com.jk.bean.User;
public interface UserMapper {

	public User selectUserById(Integer id);

}
```

二、在mybatis的配置文件中加载映射文件：
===
```
<mappers>
    <mapper resource="sqlmap/UserMapper.xml" />
</mappers>
```

三、mapper接口的使用：
===
```
package com.jk;
import java.util.List;
import com.jk.bean.User;
import com.jk.mapper.UserMapper;
import com.jk.utils.SqlSessionUtils;
import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testInsert() {

		User user = new User();
		user.setUsername("小明");
		user.setBirthday("1993-12-20");
		user.setSex("男");
		user.setAddress("秀英区");

        //获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		userMapper.insertUser(user);
		SqlSessionUtils.getSqlSession().commit();

		System.out.println("获取插入数据的主键:" + user.getId());

		SqlSessionUtils.getSqlSession().close();
	}

	public void testDelte() {
	   //获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		userMapper.deletetUserById(37);
		// 不要漏掉提交事务
		SqlSessionUtils.getSqlSession().commit();

		SqlSessionUtils.getSqlSession().close();
	}

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

	public void testSelectUserById() {
	    //获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		User user = userMapper.selectUserById(38);
		System.out.println("userone=" + user);
		SqlSessionUtils.getSqlSession().close();
	}

	public void testSelectUsers() {
	    ////获取SqlSession，通过SqlSession获取对应的接口
		UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
		List<User> users = userMapper.selectUsers();
		System.out.println("users=" + users);
		SqlSessionUtils.getSqlSession().close();
	}
}
```