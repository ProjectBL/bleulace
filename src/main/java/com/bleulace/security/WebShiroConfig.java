package com.bleulace.security;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({ "dev", "prod" })
public class WebShiroConfig implements SecurityProfile
{
	@Override
	@Bean(name = "securityManager")
	@DependsOn("realm")
	public SecurityManager securityManager(Realm realm)
	{
		return new DefaultWebSecurityManager(realm);
	}

	@Bean(name = "shiroFilter")
	@DependsOn("securityManager")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager)
	{
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		return bean;
	}
}