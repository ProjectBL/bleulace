package com.bleulace.domain.crm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.PreRemove;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Persistable;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.Gender;
import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.utils.chrono.TimeZoneEnum;
import com.bleulace.utils.ctx.SpringApplicationContext;

@RooJavaBean
@Entity
public class Account implements Persistable<String>
{
	@Id
	@NotEmpty
	@Column(nullable = false, updatable = false, unique = true)
	private String id;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] avatar;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;

	@Embedded
	private HashedPassword password = new HashedPassword();

	@Embedded
	private ContactInformation contactInformation = new ContactInformation();

	@ElementCollection
	@MapKeyJoinColumn
	private Map<Account, FriendRequest> friendRequests = new HashMap<Account, FriendRequest>();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Account> friends = new ArrayList<Account>();

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TimeZoneEnum timeZone = TimeZoneEnum.DEFAULT;

	public Account()
	{
	}

	public String getUsername()
	{
		return id;
	}

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

	@Override
	public boolean equals(Object obj)
	{

		if (null == obj)
		{
			return false;
		}

		if (this == obj)
		{
			return true;
		}

		if (!getClass().equals(obj.getClass()))
		{
			return false;
		}

		Account that = (Account) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	@PreRemove
	public void preRemove()
	{
		for (Account friend : friends)
		{
			friends.remove(friend);
		}
	}

	@Override
	public boolean isNew()
	{
		return SpringApplicationContext.getBean(AccountDAO.class).exists(id);
	}
}