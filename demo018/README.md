mybatis-mybatis(基于注解)和spring的整合(mapper接口代理方式)
===

1、创建maven的java项目，创建过程如下：

![image](https://github.com/jiekekeji/MStudySpring4/blob/master/demo018/prevew/demo001-1.gif)

创建完成的项目是没有 src/main/resources 目录的，该目录要以Source Folder 的方式创建，该目录是maven的classpath目录。

2、在pom.xml添加spring的依赖如下：

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>jiekekeji</groupId>
	<artifactId>demo018</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>demo018</name>
	<url>http://maven.apache.org</url>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

	<dependencies>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>3.8.1</version>
			<scope>test</scope>
		</dependency>

		<!-- https://mvnrepository.com/artifact/org.springframework/spring-core -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<version>4.2.5.RELEASE</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>4.2.5.RELEASE</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-beans -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-beans</artifactId>
			<version>4.2.5.RELEASE</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-expression -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-expression</artifactId>
			<version>4.2.5.RELEASE</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
			<version>4.3.10.RELEASE</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/commons-logging/commons-logging -->
		<dependency>
			<groupId>commons-logging</groupId>
			<artifactId>commons-logging</artifactId>
			<version>1.1.3</version>
		</dependency>

		<!--mybatis -->
		<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis</artifactId>
			<version>3.2.1</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/cglib/cglib -->
		<dependency>
			<groupId>cglib</groupId>
			<artifactId>cglib</artifactId>
			<version>2.2.2</version>
		</dependency>

		<!-- mysql驱动包 -->
		<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.6</version>
		</dependency>

		<!-- 日志用的东西 -->
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

		<!-- dbcp数据源 -->
		<!-- https://mvnrepository.com/artifact/commons-dbcp/commons-dbcp -->
		<dependency>
			<groupId>commons-dbcp</groupId>
			<artifactId>commons-dbcp</artifactId>
			<version>1.4</version>
		</dependency>

		<!-- mybatis和spring整合包 -->
		<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis-spring</artifactId>
			<version>1.2.2</version>
		</dependency>

	</dependencies>
</project>

```

最终工程目录如下：
![image](https://github.com/jiekekeji/MStudySpring4/blob/master/demo018/prevew/demo018-1.png)


3、在resources下配置日志文件log4j.properties:
```
### set log levels ###
log4j.rootLogger = debug ,  stdout

### 输出到控制台 ###
log4j.appender.stdout = org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target = System.out
log4j.appender.stdout.layout = org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
```

4、在resources下配置数据库连接文件db.properties:
```
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis_test
jdbc.username=root
jdbc.password=123456
```

5、在resources下新建mybatis文件夹，新建mybatis的配置文件SqlMapperConfig.xml：
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

</configuration>

```

6、在resources下配置spring的配置文件springConfig.xml：
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">

	<!-- 加载数据库配置文件 -->
	<context:property-placeholder location="classpath:db.properties" />
	<!-- 数据源,使用dbcp -->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource"
		destroy-method="close">
		<property name="driverClassName" value="${jdbc.driver}" />
		<property name="url" value="${jdbc.url}" />
		<property name="username" value="${jdbc.username}" />
		<property name="password" value="${jdbc.password}" />
		<property name="maxActive" value="10" />
		<property name="maxIdle" value="5" />
	</bean>

	<!-- sqlSessinFactory -->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<!-- 加载mybatis的配置文件 -->
		<property name="configLocation" value="mybatis/SqlMapperConfig.xml" />
		<!-- 数据源 -->
		<property name="dataSource" ref="dataSource" />
	</bean>
	
	<!-- 
	mapper批量扫描,从mapper包中扫描出mapper接口,自动创建代理对象并且在spring容器中注册 
	遵循规范:将mapper.java和mapper.xml映射文件名称保持一致,且在一个目录 中 
	自动扫描出来的mapper的bean的id为mapper类名
	-->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<!-- 指定扫描的包名 如果扫描多个包,每个包中间使用半角逗号分隔 -->
		<property name="basePackage" value="com.jk.mapper" />
		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
	</bean>
</beans>
```

7、测试程序:
```
public class AppTest extends TestCase {
	public void testApp() {

		// 1. 创建 Spring 的 IOC 容器: 从类路径加载xml配置文件
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("springConfig.xml");

		// 2.从 IOC 容器中获取 bean 的实例:自动扫描出来的mapper的bean的id为mapper类名
		TUserMapper mapper = (TUserMapper) ctx.getBean("TUserMapper");

		System.out.println(mapper.selectTUserById(1));

	}
}
```