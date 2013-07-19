package com.bleulace.crm.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.domain.GenericDomainEventMessage;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.modelmapper.ModelMapper;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.cqrs.event.EventBusAware;
import com.bleulace.crm.application.command.ChangePasswordCommand;
import com.bleulace.crm.application.command.CreateAccountCommand;
import com.bleulace.crm.application.command.ReplyToFriendRequestCommand;
import com.bleulace.crm.application.command.SendFriendRequestCommand;
import com.bleulace.crm.application.event.AccountLoggedOutEvent;
import com.bleulace.crm.application.event.AccountLoginAttemptedEvent;
import com.bleulace.crm.application.event.FriendRequestSentEvent;
import com.bleulace.crm.application.event.RepliedToFriendRequestEvent;
import com.bleulace.crm.domain.event.AccountInfoUpdatedEvent;
import com.bleulace.crm.domain.event.PasswordChangedEvent;
import com.bleulace.crm.infrastructure.SecurityConfig;
import com.bleulace.persistence.EventSourcedAggregateRootMixin;

@Entity
@RooJavaBean
public class Account implements EventSourcedAggregateRootMixin, EventBusAware
{
	private static final long serialVersionUID = -8047989744778433448L;

	@Id
	private String id;

	private byte[] hash;
	private byte[] salt;

	@Column(nullable = false, unique = true, updatable = false)
	private String email;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column
	private byte[] image;

	@Column(nullable = false)
	private String school = "Marshall University";

	@Column(nullable = false)
	private String work = "BleuLace";

	@ManyToMany
	private List<Account> friends = new ArrayList<Account>();

	Account()
	{
	}

	public PrincipalCollection getPrincipalCollection()
	{
		return new SimplePrincipalCollection(id, SecurityConfig.REALM_NAME);
	}

	@CommandHandler
	public Account(CreateAccountCommand command)
	{
		id = command.getId();
		apply(new ModelMapper().map(command, AccountInfoUpdatedEvent.class));
		apply(new PasswordChangedEvent(command.getPassword()));
	}

	@Transient
	public String getName()
	{
		return firstName + " " + lastName;
	}

	@EventHandler
	public void on(AccountInfoUpdatedEvent event)
	{
		new ModelMapper().map(event, this);
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
		hash = event.getHash();
		salt = event.getSalt();
	}

	public void handle(SendFriendRequestCommand command)
	{
		apply(command, FriendRequestSentEvent.class);
	}

	public void on(FriendRequestSentEvent event)
	{
		System.out.println(event.getSagaId());
		eventBus().publish(GenericDomainEventMessage.asEventMessage(event));
	}

	public void handle(ReplyToFriendRequestCommand command)
	{
		apply(command, RepliedToFriendRequestEvent.class);
	}

	public void on(RepliedToFriendRequestEvent event)
	{
		System.out.println(event.getSagaId());
		eventBus().publish(GenericDomainEventMessage.asEventMessage(event));
	}
}