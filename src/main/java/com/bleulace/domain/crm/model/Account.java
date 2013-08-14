package com.bleulace.domain.crm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.axonframework.domain.MetaData;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.cqrs.MappingAspect;
import com.bleulace.domain.crm.command.CreateAccountCommand;
import com.bleulace.domain.crm.command.EditInfoCommand;
import com.bleulace.domain.crm.command.FriendRequestCommand;
import com.bleulace.domain.crm.event.AccountCreatedEvent;
import com.bleulace.domain.feed.model.FeedEntry;
import com.bleulace.domain.resource.model.AbstractRootResource;

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

	@Embedded
	private ContactInformation contactInfo = ContactInformation.defaultValues();

	@ElementCollection
	@MapKeyColumn
	private Map<Account, FriendRequest> friendRequests = new HashMap<Account, FriendRequest>();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
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
			Encryptor en = new Encryptor(password.toCharArray());
			this.salt = en.getSalt();
			this.hash = en.getHash();
		}
	}

	public Account(CreateAccountCommand command, MetaData metaData)
	{
		AccountCreatedEvent event = new AccountCreatedEvent();
		event.setId(getId());
		MappingAspect.map(command, event);
		apply(event, metaData);
	}

	public void handle(EditInfoCommand command, MetaData data)
	{
		apply(command, data);
	}

	public void on(EditInfoCommand command, MetaData data)
	{
		contactInfo = command.getInformation();
		setPassword(command.getPassword());
	}

	public void handle(FriendRequestCommand command, MetaData data)
	{
		apply(command, data);
	}

	public void on(FriendRequestCommand event, MetaData metaData)
	{
		event.getAction().execute(this, metaData);
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