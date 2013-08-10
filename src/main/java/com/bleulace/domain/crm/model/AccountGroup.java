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
import com.bleulace.domain.crm.command.CreateGroupCommand;
import com.bleulace.domain.crm.command.GroupMembershipCommand;
import com.bleulace.domain.crm.event.GroupCreatedEvent;
import com.bleulace.domain.crm.event.GroupMembershipChangedEvent;
import com.bleulace.domain.resource.model.AbstractRootResource;

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
			GroupMembershipChangedEvent event = mapper().map(command,
					GroupMembershipChangedEvent.class);
			event.setId(getId());
			event.setAccountId(creatorId);
			event.setAction(GroupMembershipAction.JOIN);
			apply(event);
		}
	}

	public void on(GroupCreatedEvent event)
	{
		map(event);
	}

	public void handle(GroupMembershipCommand command)
	{
		for (String accountId : command.getAccountIds())
		{
			GroupMembershipChangedEvent event = mapper().map(command,
					GroupMembershipChangedEvent.class);
			event.setAccountId(accountId);
			apply(event);
		}
	}

	public void on(GroupMembershipChangedEvent event)
	{
		event.getAction().execute(this, event.getAccountId());
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