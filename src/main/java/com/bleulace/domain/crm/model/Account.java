package com.bleulace.domain.crm.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.axonframework.domain.MetaData;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.cqrs.MappingAspect;
import com.bleulace.domain.crm.command.CreateAccountCommand;
import com.bleulace.domain.crm.command.FriendRequestCommand;
import com.bleulace.domain.crm.command.UpdateContactInfoCommand;
import com.bleulace.domain.crm.event.AccountCreatedEvent;
import com.bleulace.domain.feed.model.FeedEntry;
import com.bleulace.domain.resource.model.AbstractRootResource;
import com.bleulace.utils.jpa.EntityManagerReference;

@Entity
@RooJavaBean
@Table(name = "ACCOUNT")
public class Account extends AbstractRootResource implements CommentableRoot,
		CommentableResource
{
	@Column(nullable = false, updatable = false, unique = true)
	private String username;

	private byte[] hash;
	private byte[] salt;

	@CascadeOnDelete
	@OneToMany(cascade = CascadeType.ALL)
	private List<JpaPermission> permissions = new ArrayList<JpaPermission>();

	@Embedded
	private ContactInformation contactInformation = new ContactInformation();

	@ManyToMany
	private Set<Account> friends = new HashSet<Account>();

	@OrderBy("dateCreated DESC")
	@ElementCollection
	private List<FeedEntry> feedEntries = new ArrayList<FeedEntry>();

	Account()
	{
	}

	public void setPassword(String password)
	{
		if (password != null)
		{
			MappingAspect.map(new Encryptor(password.toCharArray()), this);
		}
	}

	public Account(CreateAccountCommand command, MetaData metaData)
	{
		AccountCreatedEvent event = new AccountCreatedEvent();
		event.setId(getId());
		MappingAspect.map(command, event);
		apply(event, metaData);
	}

	public void handle(UpdateContactInfoCommand command, MetaData data)
	{
		apply(command, data);
	}

	public void on(UpdateContactInfoCommand command, MetaData data)
	{
		MappingAspect.map(command, contactInformation);
	}

	public void handle(FriendRequestCommand command, MetaData data)
	{
		apply(command, data);
	}

	public void on(FriendRequestCommand event)
	{
		event.getAction().execute(
				this,
				EntityManagerReference.load(Account.class,
						event.getRecipientId()));
	}

	@PreRemove
	public void preRemove()
	{
		for (Account friend : friends)
		{
			friends.remove(friend);
		}
	}
}