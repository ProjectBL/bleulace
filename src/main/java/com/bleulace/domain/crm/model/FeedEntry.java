package com.bleulace.domain.crm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.SerializationUtils;
import org.joda.time.DateTime;
import org.springframework.data.domain.Persistable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.jpa.EntityManagerReference;

@Entity
@RooJavaBean(settersByDefault = false)
public class FeedEntry implements Persistable<String>
{
	@Id
	@Column(updatable = false)
	private String id = UUID.randomUUID().toString();

	@Column(nullable = false)
	private Class<?> clazz;

	@Lob
	@Column(nullable = false)
	private byte[] data;

	@Column(nullable = false)
	private DateTime createdDate = new DateTime();

	private Account createdBy;

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<Account> accounts = new ArrayList<Account>();

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private FeedEntryScenario scenario;

	public FeedEntry(Serializable obj, Account createdBy,
			FeedEntryScenario scenario)
	{
		Assert.notNull(obj);
		Assert.notNull(scenario);
		clazz = obj.getClass();
		data = SerializationUtils.serialize(obj);
		this.scenario = scenario;
		this.createdBy = createdBy;
	}

	FeedEntry()
	{
	}

	@Override
	public boolean isNew()
	{
		return EntityManagerReference.get().find(getClass(), id) == null;
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

		FeedEntry that = (FeedEntry) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}
}