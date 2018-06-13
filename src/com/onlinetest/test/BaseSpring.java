package com.onlinetest.test;

import org.apache.catalina.core.ApplicationContext;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class BaseSpring {

public static ClassPathXmlApplicationContext context;
	
	@Before
	public void startSpring(){
		context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
	}
}
