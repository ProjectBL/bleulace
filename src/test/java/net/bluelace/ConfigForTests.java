package net.bluelace;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigForTests
{
	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean(Realm realm)
	{
		DefaultSecurityManager manager = new DefaultSecurityManager(realm);
		MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
		bean.setArguments(new Object[] { manager });
		bean.setTargetClass(SecurityUtils.class);
		bean.setTargetMethod("setSecurityManager");
		return bean;
	}
}
