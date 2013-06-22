package com.bleulace.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestShiroConfig implements SecurityProfile
{
	@Override
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
