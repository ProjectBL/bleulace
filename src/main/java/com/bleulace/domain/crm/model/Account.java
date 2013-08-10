package com.bleulace.domain.crm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.command.CreateAccountCommand;
import com.bleulace.domain.crm.command.FriendRequestCommand;
import com.bleulace.domain.crm.command.UpdateContactInfoCommand;
import com.bleulace.domain.crm.event.AccountCreatedEvent;
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
	private ContactInformation contactInformation;

	@ManyToMany
	private List<Account> friends = new ArrayList<Account>();

	Account()
	{
	}

	public void setPassword(String password)
	{
		if (password != null)
		{
			map(new Encryptor(password.toCharArray()));
		}
	}

	public Account(CreateAccountCommand command)
	{
		AccountCreatedEvent event = new AccountCreatedEvent();
		event.setId(getId());
		mapper().map(command, event);
		apply(event);
	}

	public void on(AccountCreatedEvent event)
	{
		map(event);
	}

	public void handle(UpdateContactInfoCommand command)
	{
		apply(command);
	}

	public void on(UpdateContactInfoCommand event)
	{
		map(event);
	}

	public void handle(FriendRequestCommand command)
	{
		apply(command);
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