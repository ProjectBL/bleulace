package com.bleulace.domain.crm.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.apache.shiro.SecurityUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.utils.chrono.TimeZoneEnum;

@RooJavaBean
@Entity
@Table(name = "ACCOUNT")
@RooJpaActiveRecord
public class Account extends AbstractResource
{
	@Column(nullable = false, updatable = false, unique = true)
	private String username = "";

	@Embedded
	private HashedPassword password = new HashedPassword();

	@Embedded
	private ContactInformation contactInformation = new ContactInformation();

	@ElementCollection
	@MapKeyJoinColumn
	private Map<Account, FriendRequest> friendRequests = new HashMap<Account, FriendRequest>();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Account> friends = new HashSet<Account>();

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TimeZoneEnum timeZone = TimeZoneEnum.DEFAULT;

	public Account()
	{
	}

	@Override
	public String getTitle()
	{
		return contactInformation.getFirstName() + " "
				+ contactInformation.getLastName();
	}

	public TimeZone getTimeZone()
	{
		return timeZone.toTimeZone();
	}

	public void setTimeZone(TimeZone timeZone)
	{
		this.timeZone = TimeZoneEnum.fromTimeZone(timeZone);
	}

	public void setPassword(String password)
	{
		if (password != null)
		{
			this.password = new HashedPassword(password);
		}
	}

	@PreRemove
	public void preRemove()
	{
		for (Account friend : friends)
		{
			friends.remove(friend);
		}
	}

	public static Account getCurrent()
	{
		String id = (String) SecurityUtils.getSubject().getPrincipal();
		if (id != null)
		{
			return EntityManagerReference.load(Account.class, id);
		}
		return null;
	}
}