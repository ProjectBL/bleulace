package com.bleulace.crm.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.application.command.CreateGroupCommand;
import com.bleulace.crm.application.command.JoinGroupCommand;
import com.bleulace.crm.domain.event.GroupCreatedEvent;
import com.bleulace.crm.domain.event.GroupJoinedEvent;
import com.bleulace.feed.NewsFeedEnvelope;
import com.bleulace.persistence.EventSourcedAggregateRootMixin;
import com.bleulace.utils.jpa.EntityManagerReference;

@Entity
@RooJavaBean
public class AccountGroup implements EventSourcedAggregateRootMixin
{
	private static final long serialVersionUID = 5467310829698131328L;

	@Id
	private String id;

	@Column(unique = true, nullable = false)
	private String title;

	// add permissions in shiro
	@ManyToMany
	private List<Account> members = new ArrayList<Account>();

	AccountGroup()
	{
	}

	public AccountGroup(CreateGroupCommand command)
	{
		apply(command, GroupCreatedEvent.class);
		String creatorId = command.getCreatorId();
		{
			apply(new GroupJoinedEvent(creatorId));
		}
	}

	public void on(GroupCreatedEvent event)
	{
		map(event);
		String creatorId = event.getCreatorId();
		if (creatorId != null)
		{
			new NewsFeedEnvelope().addFriends(creatorId)
					.withPayloads(this, event).send();
		}
	}

	public void handle(JoinGroupCommand command)
	{
		apply(command, GroupJoinedEvent.class);
	}

	public void on(GroupJoinedEvent event)
	{
		Account account = EntityManagerReference.load(Account.class,
				event.getAccountId());
		members.add(account);

		new NewsFeedEnvelope().addFriends(account).addAccounts(members)
				.withPayloads(this, event).send();
	}
}