package com.bleulace.accountRelations.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.modelmapper.ModelMapper;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.accountRelations.application.command.ChangePasswordCommand;
import com.bleulace.accountRelations.application.command.CreateAccountCommand;
import com.bleulace.accountRelations.application.event.AccountInfoUpdatedEvent;
import com.bleulace.accountRelations.application.event.AccountLoggedOutEvent;
import com.bleulace.accountRelations.application.event.AccountLoginAttemptedEvent;
import com.bleulace.accountRelations.application.event.PasswordChangedEvent;

@Entity
@RooJavaBean(settersByDefault = false)
public class Account extends AbstractAnnotatedAggregateRoot<String>
{
	private static final long serialVersionUID = -8047989744778433448L;

	@AggregateIdentifier
	private String id;

	private byte[] hash = null;
	private byte[] salt = null;

	@Column(nullable = false, unique = true, updatable = false)
	private String email;

	@Column
	private String firstName;

	@Column
	private String lastName;

	Account()
	{
	}

	@CommandHandler
	public Account(CreateAccountCommand command)
	{
		apply(new ModelMapper().map(command, AccountInfoUpdatedEvent.class));
		apply(new PasswordChangedEvent(command.getPassword()));
	}

	@Transient
	public String getName()
	{
		return firstName + " " + lastName;
	}

	// called by AuthenticationHandler
	public void onLogin(Boolean success)
	{
		apply(new AccountLoginAttemptedEvent(success));
	}

	@EventHandler
	public void onLoginAttemptedEvent(AccountLoginAttemptedEvent event)
	{
	}

	// called by AuthenticationHandler
	public void onLogout()
	{
		apply(new AccountLoggedOutEvent());
	}

	@EventHandler
	public void onLogoutAttemptedEvent(AccountLoggedOutEvent event)
	{
	}

	@CommandHandler
	public void handle(ChangePasswordCommand command)
	{
		apply(new PasswordChangedEvent(command.getValue()));
	}

	@EventHandler
	public void handle(PasswordChangedEvent event)
	{
		new ModelMapper().map(event, this);
	}
}