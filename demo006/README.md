mybatis-动态SQL
---

一、if条件：
===
UserMapper.xml给sql语句加上if条件判断：

1、where部分条件判断：

```
<update id="updateUser" parameterType="com.jk.bean.User">
    update user set address=#{address} 
    <where>
       <if test="sex!=null">
            and sex=#{sex}
       </if>
       <if test="username!=null">
            and username=#{username}
       </if>
    </where>
</update>
```
如果参数User的sex属性和username属性都不为null时，拼接为：
```
update user set address=? WHERE sex=? and username=? 
```
如果参数User的sex属性为null,username属性都不为null时，拼接为：
```
update user set address=? WHERE username=? 
```
如果参数User的sex属性为null,username属性也为null时，拼接为：
```
update user set address=? 
```

2、也可以加载sql的其他位置 set条件判断：
```
<update id="updateUserById" parameterType="com.jk.bean.User">
    update user
    <set>
        <if test="username!=null">
            username=#{username},
        </if>
        <if test="address!=null">
            address=#{address},
        </if>
    </set>
    where id=#{id}
</update>
```
set方式的另一种写法采用 
```
<update id="updateUserById" parameterType="com.jk.bean.User">
    update user
    <trim prefix="SET" suffixOverrides=",">
        <if test="username!=null">
            username=#{username},
        </if>
        <if test="address!=null">
            address=#{address},
        </if>
    </trim>
    where id=#{id}
</update>

```
这<trim prefix="SET" suffixOverrides=",">内容</trim>的意思是在内容前加上SET，如果内容最后为逗号，去掉最后的逗号。

二、SQL片段
===

比如原来的sql是这样的：
```
<update id="updateUserById" parameterType="com.jk.bean.User">
    update user
    <set>
        <if test="username!=null">
            username=#{username},
        </if>
        <if test="address!=null">
            address=#{address},
        </if>
    </set>
    where id=#{id}
</update>
```
然后我们把set里面的内容提出来，标识一个id，然后使用的时候通过include id引用：
```
<!--提取的sql片段，可重用 -->
<sql id="update_user">
    <if test="username!=null">
        username=#{username},
    </if>
    <if test="address!=null">
        address=#{address},
    </if>
</sql>
<!--使用上面的sql片段  -->
<update id="updateUserById" parameterType="com.jk.bean.User">
    update user
    <set>
        <include refid="update_user"></include>
    </set>
    where id=#{id}
</update>
```

三、foreach语句：
===
比如我们出入的是数组时，类似下面的语句：
```
SELECT * FROM user WHERE id=1 OR id=10 OR id=16
   
SELECT * FROM user WHERE id IN(1,10,16)

```
1、先定义一个查询参数类：
```
public class Query {
	
	private List<Integer> ids;

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
}
```
2、定义mapper.xml:
```
<select id="selectUserByIds" parameterType="com.jk.bean.Query"
    resultType="user">
    select * from user
    <where>
        <if test="ids!=null">
            <!-- 
                使用foreach遍历ids 
                collection:指定输入对象的集合属性 
                item:每个遍历生成对象中 
                open：开始遍历时拼接的串 
                close:结束遍历时拼接的串 
                separator:遍历的两个对象中需要拼接的串 -->
            <foreach collection="ids" item="user_id" open="AND (" close=")"
                separator="or">
                id=#{user_id}
            </foreach>
        </if>
    </where>
</select>
```
拼接出来的语句类似：
```
Preparing: select * from user WHERE ( id=? or id=? ) 
```
3、测试：
```
public void testsSelectUserByIds() {
    ////获取SqlSession，通过SqlSession获取对应的接口
    UserMapper userMapper = SqlSessionUtils.getSqlSession().getMapper(UserMapper.class);
    Query query=new Query();
    List<Integer> ids=new ArrayList<Integer>();
    ids.add(38);
    ids.add(40);
    query.setIds(ids);
    List<User> users = userMapper.selectUserByIds(query);
    System.out.println("users=" + users);
    SqlSessionUtils.getSqlSession().close();
}
```
4、如果需要拼接出来的语句为：
```
select * from user where id in(1,2,3)
```

mapper.xml的写法为：
```
<select id="selectUserByIds" parameterType="com.jk.bean.Query"
    resultType="user">
    select * from user
    <where>
        <if test="ids!=null">
            <!-- 
                使用foreach遍历ids 
                collection:指定输入对象的集合属性 
                item:每个遍历生成对象中 
                open：开始遍历时拼接的串 
                close:结束遍历时拼接的串 
                separator:遍历的两个对象中需要拼接的串 -->
            <foreach collection="ids" item="user_id" 
                     open="AND id in (" 
                     close=")" 
                     separator=",">
                id=#{user_id}
            </foreach>
        </if>
    </where>
</select>
```