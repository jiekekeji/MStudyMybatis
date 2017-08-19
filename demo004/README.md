mybatis-SqlMapConfig.xml全局配置文件
---

SqlMapConfig.xml配置文件的内容和配置顺序如下：
===
```
1、properties(属性)
2、settings(全局配置参数)
3、typeAiases(类型别名)
4、typeHandlers(类型处理器)
5、objectFactory(对象工厂)
6、plugins(插件)
7、environments(环境集合属性对象)mappers(映射器)
    7.1、environment(环境子属性对象)
    7.2、transactionManager(事物管理)
    7.3、datesource(数据源
8、mappers(映射器)
```

一、properties(属性)：
===

1、第一种方式：将dataSource的配置属性提取放到properties中 ，在需要的地方通过${name}的方式引用properties中的配置：

```
<!-- 将dataSource的配置属性提取放到properties中 -->

<properties>
    <property name="driver" value="com.mysql.jdbc.Driver" />
    <property name="url"
        value="jdbc:mysql://locahost:3306/mybatis_test" />
    <property name="username" value="root" />
    <property name="password" value="123456" />
</properties>

<environments default="development">
    <environment id="development">
        <transactionManager type="JDBC" />
        
        <!-- 通过${name}的方式引用properties中的配置 -->
        <dataSource type="POOLED">
            <property name="driver" value="${driver}" />
            <property name="url" value="${url}" />
            <property name="username" value="${username}" />
            <property name="password" value="${password}" />
        </dataSource>
    </environment>
</environments>
```

2、第二种方式：将dataSource的配置属性提取独立的文件db.properties中，通过resource="db.properties"引入：

db.properties:
```
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://locahost:3306/mybatis_test
jdbc.username=root
jdbc.password=123456
```

SqlMapConfig.xml:
 
```
<!-- 加载数据库文件db.properties -->
<properties resource="db.properties"></properties>

<!-- 和spring整合后environments配置将被废除 -->
<environments default="development">
    <environment id="development">
        <!-- 使用jdbc事务管理 -->
        <transactionManager type="JDBC" />
        <!-- 配置数据库连接池 ，使用db.properties的配置-->
        <dataSource type="POOLED">
            <property name="driver" value="${jdbc.driver}" />
            <property name="url" value="${jdbc.url}" />
            <property name="username" value="${jdbc.username}" />
            <property name="password" value="${jdbc.password}" />
        </dataSource>
    </environment>
</environments>
```

properties特性：

注意：

        在properties元素体内定义的属性优先读取。
        
        然后读取properties元素中resource或url加载的属性，它会覆盖已读取的同名属性。
        
        最后读取parameterType传递的属性，它会覆盖已读取的同名属性

建议：

    　　不要在properties元素体内添加任何属性值，只将属性值定义在properties文件中。
    
    　　在properties文件中定义属性名要有一定的特殊性，如xxxx.xxxx(jdbc.driver)
    
二、settings全局参数配置：
===

mybatis框架运行时可以调整一些运行参数。比如，开启二级缓存，开启延迟加载等等。全局参数会影响mybatis的运行行为。

官方配置例子：
```
<setting name="cacheEnabled" value="true"/>
    <setting name="lazyLoadingEnabled" value="true"/>
    <setting name="multipleResultSetsEnabled" value="true"/>
    <setting name="useColumnLabel" value="true"/>
    <setting name="useGeneratedKeys" value="false"/>
    <setting name="autoMappingBehavior" value="PARTIAL"/>
    <setting name="defaultExecutorType" value="SIMPLE"/>
    <setting name="defaultStatementTimeout" value="25"/>
    <setting name="safeRowBoundsEnabled" value="false"/>
    <setting name="mapUnderscoreToCamelCase" value="false"/>
    <setting name="localCacheScope" value="SESSION"/>
    <setting name="jdbcTypeForNull" value="OTHER"/>
    <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
</settings>
```

三、typeAiases(别名)：
===

1、之前在映射文件中写sql，会类似下面的方式,parameterType和resultType都采用全类名的方式：

```
<select id="selectUserById" parameterType="java.lang.Integer"
    resultType="com.jk.bean.User">
    select * from user where id=#{id}
</select>
```

另外parameterType和resultType还可以采用别名的方式。

mybatis默认支持的别名：

![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo004/preview/demo004-1.png)

因此，java.lang.Integer类型可以用int代替，形如parameterType="int"：

```
<select id="selectUserById" parameterType="int"
    resultType="com.jk.bean.User">
    select * from user where id=#{id}
</select>
```

2、自定义别名：

2.1、单个别名定义(在SqlMapConfig.xml)，将com.jk.bean.User别名定义为user：
```
<typeAliases>
    <typeAlias type="com.jk.bean.User" alias="user" />
</typeAliases>
```
那么resultType可以写成：resultType="user"：
```
<select id="selectUserById" parameterType="int"
    resultType="user">
    select * from user where id=#{id}
</select>
```

2.2、批量别名定义(在SqlMapConfig.xml)，将com.jk.bean包下的类都定义别名，别名就是类名(首字母大写或小写都可以)：
```
<typeAliases>
    <!-- 给com.jk.bean包下的bean都定义别名，别名就是类名(首字母大写或小写都可以)-->
    <package name="com.jk.bean"/>
    <!--如果也要给其他包下的类定义别名  -->
    <package name="其他包名"/>
</typeAliases>
```

四、typeHandlers(类型处理器)
===

mybatis中通过typeHandlers完成jdbc类型和java类型的转换。

通常情况下，mybatis提供的类型处理器满足日常需要，不需要自定义.

五、mappers(映射配置)：
===

1、通过resource加载单个映射文件：
```
<!-- 加载映射文件 -->
<mappers>
    <mapper resource="sqlmap/UserMapper.xml" />
    <!--如果有其他映射文件  -->
    <mapper resource="sqlmap/OtherMapper.xml" />
</mappers>
```

2、通过mapper接口加载单个映射文件。前提是：使用的是mapper代理方法;遵循一定的规范：需要将mapper接口类名和mapper.xml映射文件名称保持一致，且在一个目录中；

![image](https://github.com/jiekekeji/MStudyMybatis/blob/master/demo004/preview/demo004-2.png)
                            
```
<mappers>
    <!-- 
         通过mapper接口加载单个映射配置文件 
         遵循一定的规范：需要将mapper接口类名和mapper.xml映射文件名称保持一致，且在一个目录中； 
         上边规范的前提是：使用的是mapper代理方法; 
    -->
    <mapper class="com.jk.mapper.UserMapper" />
</mappers>
```

3、批量加载mapper(推荐使用)：

```
<mappers>
     <!-- 
        批量加载映射配置文件,mybatis自动扫描包下面的mapper接口进行加载
        遵循一定的规范：需要将mapper接口类名和mapper.xml映射文件名称保持一致，且在一个目录中；
        上边规范的前提是：使用的是mapper代理方法;
     -->		
     <package name="com.jk.mapper"/> 
     <!--如果有其他包  -->
     <package name="其他包名"/> 
</mappers>
```