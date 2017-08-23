mybatis-使用逆向工程生成的代码
===


```
public void testSelectByPrimaryKey() throws InterruptedException {

	SqlSession sqlSession = SqlSessionUtils.getSqlSession();

	TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);

	TUser tUser = userMapper.selectByPrimaryKey(1);
	System.out.println(tUser);

	sqlSession.close();

}
```