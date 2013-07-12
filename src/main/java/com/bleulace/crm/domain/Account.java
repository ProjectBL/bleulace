package com.bleulace.crm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.modelmapper.ModelMapper;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.application.command.ChangePasswordCommand;
import com.bleulace.crm.application.command.CreateAccountCommand;
import com.bleulace.crm.application.event.AccountInfoUpdatedEvent;
import com.bleulace.crm.application.event.AccountLoggedOutEvent;
import com.bleulace.crm.application.event.AccountLoginAttemptedEvent;
import com.bleulace.crm.application.event.PasswordChangedEvent;

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

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
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

	/**
	 * Makes note of a login attempt for this account in the event store
	 * 
	 * @param success
	 *            whether the login attempt was a success
	 * @see LoginLogoutCommandHandler, the class which calls this method
	 */
	public void handleLoginAttempt(Boolean success)
	{
		apply(new AccountLoginAttemptedEvent(success));
	}

	@EventHandler
	public void on(AccountLoginAttemptedEvent event)
	{
		// TODO : lock account on x number of incorrect logins
	}

	/**
	 * Makes note of a logout for this account in the event store
	 * 
	 * @see LoginLogoutCommandHandler, the class which calls this method
	 */
	public void handleLogout()
	{
		apply(new AccountLoggedOutEvent());
	}

	@EventHandler
	public void onLogoutAttemptedEvent(AccountLoggedOutEvent event)
	{
		// TODO : I forgot what to do here, but I know I'm supposed to do
		// something!
	}

	@CommandHandler
	public void handle(ChangePasswordCommand command)
	{
		apply(new PasswordChangedEvent(command.getValue()));
	}

	@EventHandler
	public void on(PasswordChangedEvent event)
	{
		new ModelMapper().map(event, this);
	}
}