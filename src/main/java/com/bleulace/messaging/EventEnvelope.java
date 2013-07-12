package com.bleulace.messaging;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.domain.Persistable;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.account.Account;

@Entity
@RooJavaBean(settersByDefault = false)
public class EventEnvelope
{
	@Id
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(nullable = false)
	private String entityId;

	@Column(nullable = false)
	private String entityType;

	@Column(nullable = false)
	private String eventType;

	@Column(nullable = false)
	private byte[] data;

	@ManyToOne
	private Account executingUser;

	private long timePosted;

	@SuppressWarnings("unused")
	private EventEnvelope()
	{
	}

	protected EventEnvelope(Persistable<String> entity, Serializable event)
	{
		entityId = entity.getId();
		entityType = typeString(entity);

		eventType = typeString(event);
		data = SerializationUtils.serialize(event);

		executingUser = Account.current();
		timePosted = System.currentTimeMillis();
	}

	private String typeString(Object target)
	{
		return target.getClass().getSimpleName();
	}
}