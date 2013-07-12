package com.bleulace.accountRelations.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

public interface SecurityProfile
{
	@Bean(name = "securityManager")
	@DependsOn("realm")
	public SecurityManager securityManager(Realm realm);
}
