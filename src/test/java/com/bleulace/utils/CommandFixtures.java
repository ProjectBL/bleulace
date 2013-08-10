package com.bleulace.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.crm.command.CreateAccountCommand;
import com.bleulace.domain.crm.command.CreateGroupCommand;
import com.bleulace.domain.management.command.CreateBundleCommand;
import com.bleulace.domain.management.command.CreateProjectCommand;
import com.bleulace.domain.management.command.CreateTaskCommand;
import com.bleulace.domain.management.model.Bundle;
import com.bleulace.domain.management.model.Project;

@Profile("test")
@Configuration
public class CommandFixtures implements CommandGatewayAware
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

	@Bean
	@Scope("prototype")
	public CreateProjectCommand createProjectCommand()
	{
		CreateProjectCommand c = new CreateProjectCommand();
		c.setTitle(randomString());
		return c;
	}

	@Bean
	@Scope("prototype")
	public CreateBundleCommand createBundleCommand(CreateProjectCommand cp)
	{
		sendAndWait(cp);
		CreateBundleCommand c = new CreateBundleCommand(Locator.locate(
				Project.class).getId());
		c.setTitle(randomString());
		return c;
	}

	@Bean
	@Scope("prototype")
	public CreateTaskCommand createTaskCommand(CreateBundleCommand cb)
	{
		sendAndWait(cb);
		CreateTaskCommand c = new CreateTaskCommand(Locator
				.locate(Bundle.class).getId());
		c.setTitle(randomString());
		return c;
	}

	@Bean
	@Scope("prototype")
	public CreateGroupCommand createGroupCommand()
	{
		CreateGroupCommand c = new CreateGroupCommand();
		c.setTitle(randomString());
		return c;
	}

	private String randomString()
	{
		return RandomStringUtils.randomAlphabetic(20);
	}
}