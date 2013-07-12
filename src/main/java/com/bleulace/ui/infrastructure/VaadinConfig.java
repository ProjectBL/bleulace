package com.bleulace.ui.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.xpoft.vaadin.SpringApplicationContext;

@Configuration
public class VaadinConfig
{
	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public MethodInvokingFactoryBean xpoftApplicationContextSetter()
	{
		MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
		bean.setTargetClass(SpringApplicationContext.class);
		bean.setTargetMethod("setApplicationContext");
		bean.setArguments(new Object[] { applicationContext });
		return bean;
	}
}