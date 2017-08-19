mybatis-helloworld
--------------------

一、新建maven java工程,在pom.xml加入如下依赖：
---
```
!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.2.1</version>
</dependency>
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.6</version>
</dependency>
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.6.6</version>
</dependency>
```
二、配置log4j日志（如果不配置日志们看不到mybatis的执行过程），在resources目录下，新建log4j.properties文件，加入如下配置：
---
```
### set log levels ###
log4j.rootLogger = debug ,  stdout

### 输出到控制台 ###
log4j.appender.stdout = org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target = System.out
log4j.appender.stdout.layout = org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
```

三、配置mybatis的配置文件，在resources目录下，新建SqlMapperConfig.xml文件，加入如下配置：
---
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<!-- 和spring整合后environments配置将被废除 -->
	<environments default="development">
		<environment id="development">
			<!-- 使用jdbc事务管理 -->
			<transactionManager type="JDBC" />
			<!-- 配置数据库连接池 -->
			<dataSource type="POOLED">
				<property name="driver" value="com.mysql.jdbc.Driver" />
				<property name="url" value="jdbc:mysql://localhost:3306/mybatis_test" />
				<property name="username" value="root" />
				<property name="password" value="123456" />
			</dataSource>
		</environment>
	</environments>

	<!--映射文件-->
	<mappers>
		<mapper resource="sqlmap/User.xml" />
	</mappers>
</configuration>
```

四、根据第三步的配置（<mapper resource="sqlmap/User.xml" />），在resources下新建sqlmap目录，然后在sqlmap目录下新建User.xml文件：
---
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--命名空间(namespace)，作用就是对sql进行分类的管理，sql隔离-->
<mapper namespace="test">

	<!--select 标识通过查询执行该条语句-->
	<!--id:标识着该sql语句在该命名空间下的唯一标识-->
	<!--parameterType:指定输入的参数类型-->
	<!--#{value}：标识占位符-->
	<!--#{value}：如果输入参数为简单的数据类型，#{}中的参数可以任意，可以是value或其他类型-->
	<!--resultType：查询后返回的结果类型-->
	<select id="findUserById" parameterType="java.lang.Integer" resultType="com.jk.bean.User">
		<!-- 最后不能有分号 -->
		SELECT * FROM user WHERE id = #{value}
	</select>

</mapper>
```

五、创建java类User包名为com.jk.bean：
---
```
public class User {

	// 属性名和数据库表名一一对应
	private Integer id;
	private String username;
	private String sex;
	private Date birthday;
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
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
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
		return "User [id=" + id + ", username=" + username + ", sex=" + sex + ", birthday=" + birthday + ", address="
				+ address + "]";
	}
}
```
六、创建数据库表:
---
```
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '用户名称',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8
```

七、创建UserDao类作为测试：
---
```
public class UserDao {

	public void findUserById() throws IOException {

		// mybatis配置文件
		String resource = "SqlMapperConfig.xml";

		// 得到配置文件流
		ClassLoader classLoader = UserDao.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream(resource);

		// 创建会话工厂，向build方法中传入配置信息
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

		// 通过工厂得到SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 通过SqlSession操作数据库
		// 第一个参数是：映射文件中Statement的id，等于namespace+statement的id
		// 第二个参数：指定和映射文件中所匹配的parameterType类型的参数
		// 结果就是实体类,selectOne表示查询出一条记录
		User user = sqlSession.selectOne("test.findUserById", 1);

		System.out.println(user);

		// 释放资源
		sqlSession.close();
	}
}
```
八、在main方法中调用测试:
---
```
	public static void main(String[] args) {
		UserDao user = new UserDao();
		try {
			user.findUserById();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
```