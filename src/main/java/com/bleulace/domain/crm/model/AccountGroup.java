package com.bleulace.domain.crm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;

import org.axonframework.common.annotation.MetaData;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.cqrs.ShiroMetaData;
import com.bleulace.domain.crm.command.ChangeGroupMembershipCommand;
import com.bleulace.domain.crm.command.CreateGroupCommand;
import com.bleulace.domain.crm.event.GroupCreatedEvent;
import com.bleulace.domain.crm.event.GroupJoinedEvent;
import com.bleulace.domain.crm.event.GroupLeftEvent;
import com.bleulace.domain.resource.model.AbstractRootResource;
import com.bleulace.utils.jpa.EntityManagerReference;

@Entity
@RooJavaBean
public class AccountGroup extends AbstractRootResource
{
	@Column(unique = true, nullable = false)
	private String title;

	@ManyToMany
	private List<Account> members = new ArrayList<Account>();

	AccountGroup()
	{
	}

	public AccountGroup(CreateGroupCommand command,
			@MetaData(ShiroMetaData.SUBJECT_ID) String creatorId)
	{
		apply(command, GroupCreatedEvent.class);
		if (creatorId != null)
		{
			GroupJoinedEvent event = new GroupJoinedEvent();
			event.setAccountId(creatorId);
			apply(event);
		}
	}

	public void on(GroupCreatedEvent event)
	{
		map(event);
	}

	public void handle(ChangeGroupMembershipCommand command)
	{
		if (command.isJoining())
		{
			for (String accountId : command.getAccountIds())
			{
				GroupJoinedEvent event = new GroupJoinedEvent();
				event.setAccountId(accountId);
				apply(event);
			}
		}
		else
		{
			for (String accountId : command.getAccountIds())
			{
				GroupLeftEvent event = new GroupLeftEvent();
				event.setAccountId(accountId);
				apply(event);
			}
		}
	}

	public void on(GroupJoinedEvent event)
	{
		members.add(EntityManagerReference.load(Account.class,
				event.getAccountId()));
	}

	public void on(GroupLeftEvent event)
	{
		members.remove(EntityManagerReference.load(Account.class,
				event.getAccountId()));
		if (members.isEmpty())
		{
			markForDeletion();
		}
	}

	@PreRemove
	protected void preRemove()
	{
		for (Account member : members)
		{
			members.remove(member);
		}
	}
}