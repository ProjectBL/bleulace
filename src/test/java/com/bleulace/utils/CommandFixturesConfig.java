package com.bleulace.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.axonframework.domain.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.CreateAccountCommand;
import com.bleulace.mgt.application.command.AddBundleCommand;
import com.bleulace.mgt.application.command.AddManagerCommand;
import com.bleulace.mgt.application.command.AddTaskCommand;
import com.bleulace.mgt.application.command.CreateProjectCommand;
import com.bleulace.mgt.domain.ManagementLevel;

@Configuration
@Profile("test")
public class CommandFixturesConfig implements CommandGatewayAware
{
	@Autowired
	@Qualifier("createAccountCommands")
	private Iterable<CreateAccountCommand> createAccountCommands;

	@Bean
	@Scope("prototype")
	public CreateAccountCommand createAccountCommand()
	{
		return createAccountCommands.iterator().next();
	}

	@Bean
	@Scope("prototype")
	public CreateProjectCommand createProjectCommand()
	{
		String id = IdentifierFactory.getInstance().generateIdentifier();
		CreateProjectCommand command = new CreateProjectCommand(id);
		command.setTitle(RandomStringUtils.random(20, true, true));
		return command;
	}

	@Bean
	@Scope("prototype")
	public AddBundleCommand createBundleCommand(
			CreateProjectCommand createProjectCommand)
	{
		gateway().send(createProjectCommand);
		AddBundleCommand command = new AddBundleCommand(
				createProjectCommand.getId());
		command.setTitle(RandomStringUtils.random(20, true, true));
		return command;
	}

	@Bean
	@Scope("prototype")
	public AddManagerCommand addManagerCommand(
			CreateAccountCommand accountCommand,
			CreateProjectCommand createProjectCommand)
	{
		gateway().send(accountCommand);
		gateway().send(createProjectCommand);
		AddManagerCommand command = new AddManagerCommand(
				createProjectCommand.getId(), accountCommand.getId(),
				ManagementLevel.LOOP);
		return command;
	}

	@Bean
	@Scope("prototype")
	public AddTaskCommand addTaskCommand(AddBundleCommand addBundleCommand)
	{
		gateway().send(addBundleCommand);
		AddTaskCommand command = new AddTaskCommand(addBundleCommand.getId());
		command.setTitle("foo");
		return command;
	}
}