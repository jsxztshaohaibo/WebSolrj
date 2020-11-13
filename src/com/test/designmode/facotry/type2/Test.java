package com.test.designmode.facotry.type2;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

public class Test {

	public static void main(String[] args) {
		/*
		 * 测试该部分代码，需要把 SpringConfig 类上的 @Configuration的注释去掉，把 两个实现类上的 @Component 注释掉
		 * AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(SpringConfig.class);
	    ctx.refresh();
		SmsSendService bean = ctx.getBean(SmsSendService.class);
		bean.send("123456","测试一下");*/
		
		ClassPathXmlApplicationContext cpCtx = new ClassPathXmlApplicationContext("spring2.xml");
		
		SmsSendService bean = cpCtx.getBean(SmsSendService.class);
		bean.send("123456","测试一下");
	}

}
