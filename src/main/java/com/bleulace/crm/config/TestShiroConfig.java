package com.bleulace.crm.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

/**
 * A security config for VM singleton environments. Specifically, we use this
 * configuration for unit and integration testing outside of the servlet
 * environment.
 * 
 * @author Arleigh Dickerson
 * 
 */
@Configuration
@Profile("test")
public class TestShiroConfig
{
	@Bean(name = "securityManager")
	@DependsOn("realm")
	public SecurityManager securityManager(Realm realm)
	{
		return new DefaultSecurityManager(realm);
	}

	@Bean
	@DependsOn("securityManager")
	public MethodInvokingFactoryBean methodInvokingFactoryBean(
			SecurityManager securityManager)
	{
		MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
		bean.setArguments(new Object[] { securityManager });
		bean.setTargetClass(SecurityUtils.class);
		bean.setTargetMethod("setSecurityManager");
		return bean;
	}
}
