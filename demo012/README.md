mybatis-逆向工程
===
针对数据库表自动生成mybatis执行所需要的代码（mapper.java,mapper.xml、pojo)。

使用示例：
---

1、新建maven java工程，在pom.xml中加入如下依赖:
```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>jiekekeji</groupId>
	<artifactId>demo012</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>demo001</name>
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
		<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
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
		<!-- https://mvnrepository.com/artifact/org.mybatis.generator/mybatis-generator-core -->
		<dependency>
			<groupId>org.mybatis.generator</groupId>
			<artifactId>mybatis-generator-core</artifactId>
			<version>1.3.2</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/cglib/cglib -->
		<dependency>
			<groupId>cglib</groupId>
			<artifactId>cglib</artifactId>
			<version>2.2.2</version>
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
	</dependencies>
</project>
```
2、在类路径resources下配置日志文件log4j.properties，此举方便查看执行过程:
```
### set log levels ###
log4j.rootLogger = debug ,  stdout

### 输出到控制台 ###
log4j.appender.stdout = org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target = System.out
log4j.appender.stdout.layout = org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
```

3、在类路径resources下配置数据库连接文件db.properties：
```
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.connectionURL=jdbc:mysql://23.105.214.207:3306/mybatis_test
jdbc.userId=root
jdbc.password=jieke123
```

3、在类路径resources下配置逆向生成代码的文件generatorConfig.xml：
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
  PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
  "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>

    <!--导入属性配置 同目录下的generator.properties文件-->
	<properties resource="db.properties"></properties>
	
	<context id="testTables" targetRuntime="MyBatis3">
		<!--pojo自动生成toString方法 -->
        <plugin type="org.mybatis.generator.plugins.ToStringPlugin"/>
        
        <!--pojo自动生成equals方法和hashcode方法 -->
        <plugin type="org.mybatis.generator.plugins.EqualsHashCodePlugin"/>
        
		<commentGenerator>
			<!-- 是否去除自动生成的注释 true：是 ： false:否 -->
			<property name="suppressAllComments" value="true" />
		</commentGenerator>

		<!--数据库连接的信息：驱动类、连接地址、用户名、密码 -->
		 <jdbcConnection driverClass="${jdbc.driverClass}"
                         connectionURL="${jdbc.connectionURL}" 
                         userId="${jdbc.userId}"
                         password="${jdbc.password}">
        </jdbcConnection>

		<!-- 
		            默认false:把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，
		           为 true时:把JDBC DECIMAL 和 NUMERIC 类型解析为java.math.BigDecimal
		 -->
		<javaTypeResolver>
			<property name="forceBigDecimals" value="false" />
		</javaTypeResolver>

		<!-- targetProject:生成PO类和Example类的位置 -->
		<javaModelGenerator targetPackage="com.jk.pojo" targetProject="src/main/java">
		    <!-- 是否对model添加 构造函数 -->
            <property name="constructorBased" value="false"/>
            <!-- 是否允许子包，即targetPackage.schemaName.tableName -->
            <property name="enableSubPackages" value="false"/>
            <!-- 建立的Model对象是否 不可改变  即生成的Model对象不会有 setter方法，只有构造方法 -->
            <property name="immutable" value="false"/>
            <!-- 给Model添加一个父类 -->
            <!--<property name="rootClass" value="com.store.model.BaseBean"/>-->
            <!-- 是否对类CHAR类型的列的数据进行trim操作 -->
            <property name="trimStrings" value="true"/>
		</javaModelGenerator>
		
		<!-- targetProject:mapper.xml映射文件生成的位置 -->
		<sqlMapGenerator targetPackage="com.jk.mapper" targetProject="src/main/java">
			<!-- enableSubPackages:是否让schema作为包的后缀 -->
			<property name="enableSubPackages" value="false" />
		</sqlMapGenerator>
		
		<!-- targetPackage：mapper接口生成的位置 -->
		<!--
                type="ANNOTATEDMAPPER",生成Java Model 和基于注解的Mapper对象
                type="MIXEDMAPPER",生成基于注解的Java Model 和相应的Mapper对象
                type="XMLMAPPER",生成SQLMap XML文件和独立的Mapper接口
        -->
		<javaClientGenerator type="XMLMAPPER" targetPackage="com.jk.mapper" targetProject="src/main/java">
			<!-- enableSubPackages:是否让schema作为包的后缀 -->
			<property name="enableSubPackages" value="false" />
			<!--rootInterface:为生成的接口添加父接口  -->
            <property name="rootInterface" value=""/>
		</javaClientGenerator>
		
		<!-- 对所有的表进行转换  tableName="%"-->
		<!-- <table tableName="%"></table> -->
		
		<!-- 转换指定数据库表 -->
		<table tableName="t_user"></table>
		<table tableName="items"></table>

	</context>
</generatorConfiguration>
```

4、测试代码，执行生成操作：
```
public void testGenerator()
        throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {

    String config_file_name = "generatorConfig.xml";
    ClassLoader classLoader = AppTest.class.getClassLoader();
    URL url = classLoader.getResource(config_file_name);

    List<String> warnings = new ArrayList<String>();
    boolean overwrite = true;
    File configFile = new File(url.getPath());

    ConfigurationParser cp = new ConfigurationParser(warnings);
    Configuration config = cp.parseConfiguration(configFile);
    DefaultShellCallback callback = new DefaultShellCallback(overwrite);
    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
    myBatisGenerator.generate(null);
    System.out.println("end");

}
```
5、执行完成后刷新工程：
![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo012/preview/demo012-1.png)
