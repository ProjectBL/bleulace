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
import com.bleulace.crm.application.command.CreateGroupCommand;
import com.bleulace.crm.application.command.LoginCommand;
import com.bleulace.crm.application.command.LogoutCommand;
import com.bleulace.mgt.application.command.AddBundleCommand;
import com.bleulace.mgt.application.command.AddCommentCommand;
import com.bleulace.mgt.application.command.AssignManagerCommand;
import com.bleulace.mgt.application.command.AddTaskCommand;
import com.bleulace.mgt.application.command.CreateProjectCommand;
import com.bleulace.mgt.application.command.JoinGroupCommand;
import com.bleulace.mgt.domain.ManagementAssignment;

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
	public AssignManagerCommand addManagerCommand(
			CreateAccountCommand accountCommand,
			CreateProjectCommand createProjectCommand)
	{
		gateway().send(accountCommand);
		gateway().send(createProjectCommand);
		AssignManagerCommand command = new AssignManagerCommand(
				createProjectCommand.getId(), accountCommand.getId(),
				ManagementAssignment.LOOP);
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

	@Bean
	@Scope("prototype")
	public CreateGroupCommand createGroupCommand()
	{
		CreateGroupCommand command = new CreateGroupCommand();
		command.setTitle(RandomStringUtils.random(20));
		return command;
	}

	@Bean
	@Scope("prototype")
	public JoinGroupCommand joinGroupCommand(
			CreateGroupCommand createGroupCommand,
			CreateAccountCommand createAccountCommand)
	{
		gateway().send(createAccountCommand);
		gateway().send(createGroupCommand);
		JoinGroupCommand command = new JoinGroupCommand(
				createGroupCommand.getId(), createAccountCommand.getId());
		return command;
	}

	@Bean
	@Scope("prototype")
	public AddCommentCommand addCommentCommand(
			CreateAccountCommand createAccountCommand,
			CreateProjectCommand createProjectCommand)
	{
		gateway().send(createAccountCommand);
		gateway().send(createProjectCommand);
		if (!gateway().sendAndWait(
				new LoginCommand(createAccountCommand.getEmail(),
						createAccountCommand.getPassword())))
		{
			throw new RuntimeException();
		}
		AddCommentCommand command = new AddCommentCommand(
				createProjectCommand.getId());
		command.setContent("foo");
		gateway().send(new LogoutCommand());
		return command;
	}
}