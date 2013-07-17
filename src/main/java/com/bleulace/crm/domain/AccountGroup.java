package com.bleulace.crm.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.application.command.CreateGroupCommand;
import com.bleulace.crm.application.command.JoinGroupCommand;
import com.bleulace.crm.application.event.GroupCreatedEvent;
import com.bleulace.crm.application.event.GroupJoinedEvent;
import com.bleulace.persistence.EventSourcedAggregateRootMixin;
import com.bleulace.utils.jpa.EntityManagerReference;

@Entity
@RooJavaBean
public class AccountGroup implements EventSourcedAggregateRootMixin
{
	private static final long serialVersionUID = 5467310829698131328L;

	@Id
	private String id;

	@Column(unique = true)
	private String title;

	// add permissions in shiro
	@ManyToMany
	private Set<Account> members = new HashSet<Account>();

	AccountGroup()
	{
	}

	public AccountGroup(CreateGroupCommand command)
	{
		id = command.getId();
		apply(command, GroupCreatedEvent.class);

		Account creator = command.getCreator();
		if (creator != null)
		{
			apply(new GroupJoinedEvent(id, creator.getId()));
		}
	}

	public void on(GroupCreatedEvent event)
	{
		map(event);
	}

	public void handle(JoinGroupCommand command)
	{
		apply(command, GroupJoinedEvent.class);
	}

	public void on(GroupJoinedEvent event)
	{
		members.add(EntityManagerReference.get().getReference(Account.class,
				event.getAccountId()));
	}
}