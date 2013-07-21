package com.bleulace.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.CreateAccountCommand;
import com.bleulace.crm.application.command.CreateGroupCommand;
import com.bleulace.crm.application.command.JoinGroupCommand;
import com.bleulace.crm.application.command.LoginCommand;
import com.bleulace.crm.application.command.LogoutCommand;
import com.bleulace.mgt.application.command.AddBundleCommand;
import com.bleulace.mgt.application.command.AddCommentCommand;
import com.bleulace.mgt.application.command.AddTaskCommand;
import com.bleulace.mgt.application.command.AssignManagerCommand;
import com.bleulace.mgt.application.command.AssignTaskCommand;
import com.bleulace.mgt.application.command.CreateEventCommand;
import com.bleulace.mgt.application.command.CreateProjectCommand;
import com.bleulace.mgt.application.command.InviteGuestsCommand;
import com.bleulace.mgt.application.command.MoveEventCommand;
import com.bleulace.mgt.application.command.ResizeEventCommand;
import com.bleulace.mgt.application.command.RsvpCommand;
import com.bleulace.mgt.domain.ManagementAssignment;
import com.bleulace.mgt.domain.TaskAssignment;

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
		CreateAccountCommand command = createAccountCommands.iterator().next();
		command.setPassword("password");
		return command;
	}

	@Bean
	@Scope("prototype")
	public CreateProjectCommand createProjectCommand()
	{
		CreateProjectCommand command = new CreateProjectCommand();
		command.setTitle(RandomStringUtils.random(20, true, true));
		return command;
	}

	@Bean
	@Scope("prototype")
	public AddBundleCommand addBundleCommand(
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
	public AssignManagerCommand assignManagerCommand(
			CreateAccountCommand createAccountCommand,
			CreateProjectCommand createProjectCommand)
	{
		gateway().send(createAccountCommand);
		gateway().send(createProjectCommand);
		AssignManagerCommand command = new AssignManagerCommand(
				createProjectCommand.getId(), createAccountCommand.getId(),
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
	public AssignTaskCommand assignTaskCommand(AddTaskCommand addTaskCommand,
			CreateAccountCommand createAccountCommand)
	{
		gateway().send(addTaskCommand);
		gateway().send(createAccountCommand);
		return new AssignTaskCommand(addTaskCommand.getId(),
				createAccountCommand.getId(), TaskAssignment.ASSIGNEE);
	}

	@Bean
	@Scope("prototype")
	public CreateGroupCommand createGroupCommand(
			CreateAccountCommand createAccountCommand)
	{
		gateway().send(createAccountCommand);
		if (!gateway().sendAndWait(
				new LoginCommand(createAccountCommand.getEmail(), "password")))
		{
			throw new AuthenticationException();
		}
		CreateGroupCommand command = new CreateGroupCommand();
		command.setTitle(RandomStringUtils.random(20));
		gateway().send(new LogoutCommand());
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

	@Bean
	@Scope("prototype")
	public CreateEventCommand createEventCommand()
	{
		CreateEventCommand command = new CreateEventCommand();
		command.setTitle("foo");
		command.setStart(DateTime.now().plusMinutes(15).toDate());
		command.setEnd(DateTime.now().plusMinutes(75).toDate());
		return command;
	}

	@Bean
	@Scope("prototype")
	public MoveEventCommand moveEventCommand(
			CreateEventCommand createEventCommand)
	{
		gateway().send(createEventCommand);
		MoveEventCommand command = new MoveEventCommand(
				createEventCommand.getId());
		command.setNewStart(LocalDateTime.now().plusHours(1).toDate());
		return command;
	}

	@Bean
	@Scope("prototype")
	public ResizeEventCommand resizeEventCommand(
			CreateEventCommand createEventCommand)
	{
		gateway().send(createEventCommand);
		ResizeEventCommand command = new ResizeEventCommand(
				createEventCommand.getId());
		command.setStart(DateTime.now().plusMinutes(15).toDate());
		command.setEnd(DateTime.now().plusMinutes(75).toDate());
		return command;
	}

	@Bean
	@Scope("prototype")
	public InviteGuestsCommand inviteGuestsCommand(
			CreateEventCommand createEventCommand,
			CreateAccountCommand createAccountCommand)
	{
		gateway().send(createAccountCommand);
		gateway().send(createEventCommand);
		InviteGuestsCommand command = new InviteGuestsCommand(
				createEventCommand.getId());
		command.getGuestIds().add(createAccountCommand.getId());
		return command;
	}

	@Bean
	@Scope("prototype")
	public RsvpCommand rsvpCommand(InviteGuestsCommand inviteGuestsCommand)
	{
		gateway().send(inviteGuestsCommand);
		RsvpCommand command = new RsvpCommand(inviteGuestsCommand.getId(),
				inviteGuestsCommand.getGuestIds().iterator().next());
		return command;
	}
}