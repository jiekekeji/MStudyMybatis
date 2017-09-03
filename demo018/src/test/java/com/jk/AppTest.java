package com.jk;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jk.mapper.TUserMapper;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	public void testApp() {

		// 1. 创建 Spring 的 IOC 容器: 从类路径加载xml配置文件
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("springConfig.xml");

		// 2.从 IOC 容器中获取 bean 的实例:自动扫描出来的mapper的bean的id为mapper类名
		TUserMapper mapper = (TUserMapper) ctx.getBean("TUserMapper");

		System.out.println(mapper.selectTUserById(1));

	}
}
