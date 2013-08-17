package com.bleulace.domain.crm.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;

import org.axonframework.domain.MetaData;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.command.CreateGroupCommand;
import com.bleulace.domain.crm.command.GroupMembershipCommand;
import com.bleulace.domain.crm.event.GroupCreatedEvent;
import com.bleulace.domain.crm.event.GroupMembershipChangedEvent;
import com.bleulace.domain.resource.model.AbstractRootResource;
import com.bleulace.utils.dto.Mapper;

@Entity
@RooJavaBean
public class AccountGroup extends AbstractRootResource implements
		CommentableRoot, CommentableResource
{
	@Column(unique = true, nullable = false)
	private String title;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Account> members = new HashSet<Account>();

	AccountGroup()
	{
	}

	public AccountGroup(CreateGroupCommand command, MetaData metaData)
	{
		String creatorId = metaData.getSubjectId();
		apply(Mapper.map(command, GroupCreatedEvent.class), metaData);
		if (creatorId != null)
		{
			GroupMembershipChangedEvent event = new GroupMembershipChangedEvent(
					creatorId, GroupMembershipAction.JOIN);
			apply(event, metaData);
		}
	}

	public void handle(GroupMembershipCommand command, MetaData metaData)
	{
		for (String accountId : command.getAccountIds())
		{
			apply(Mapper.map(command, new GroupMembershipChangedEvent(
					accountId, command.getAction())), metaData);
		}
	}

	public void on(GroupMembershipChangedEvent event)
	{
		event.getAction().execute(this, event.getAccountId());
		if (members.isEmpty())
		{
			flagForDeletion();
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