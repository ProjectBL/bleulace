package com.bleulace.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.crm.command.CreateAccountCommand;

@Configuration
public class CommandFixtures
{
	@Bean
	@Scope("prototype")
	public CreateAccountCommand createAccountCommand()
	{
		CreateAccountCommand c = new CreateAccountCommand();
		c.setUsername(randomString());
		c.setPassword(randomString());
		return c;
	}

	private String randomString()
	{
		return RandomStringUtils.random(20);
	}
}