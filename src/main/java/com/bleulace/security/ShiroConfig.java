package com.bleulace.security;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ShiroConfig
{

	public static final String HASH_ALGO_NAME = "SHA-512";
	public static final String REALM_NAME = "realm";

	@Bean
	public RandomNumberGenerator randomNumberGenerator()
	{
		return new SecureRandomNumberGenerator();
	}

	@Bean
	public HashService hashService()
	{
		DefaultHashService service = new DefaultHashService();
		service.setHashAlgorithmName(HASH_ALGO_NAME);
		return service;
	}

	@Bean(name = REALM_NAME)
	public Realm realm()
	{
		JpaRealm realm = new JpaRealm();
		realm.setAuthenticationTokenClass(UsernamePasswordToken.class);
		realm.setCredentialsMatcher(new HashedCredentialsMatcher(HASH_ALGO_NAME));
		realm.setName(REALM_NAME);
		return realm;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public HashRequest.Builder hashRequestBuilder()
	{
		HashRequest.Builder builder = new HashRequest.Builder();
		builder.setAlgorithmName(HASH_ALGO_NAME);
		return builder;
	}

	@Bean(name = "securityManager")
	public SecurityManager securityManager(Realm realm)
	{
		return new DefaultWebSecurityManager(realm);
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager)
	{
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		return bean;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor()
	{
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor advisor(
			SecurityManager securityManager)
	{
		AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
		bean.setSecurityManager(securityManager);
		return bean;
	}
}