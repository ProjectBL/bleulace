package com.bleulace.domain.crm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.jpa.EntityManagerReference;

@Entity
@RooJavaBean(settersByDefault = false)
public class AuthenticationTrace extends AbstractPersistable<Long>
{
	@Column(updatable = false)
	private String username;

	@Column(updatable = false)
	private boolean success;

	@Column(updatable = false)
	private String host;

	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private AuthenticationTrace()
	{
	}

	private AuthenticationTrace(String username, boolean success, String host)
	{
		this();
		this.username = username;
		this.success = success;
		this.host = host;
	}

	@Transactional
	public static void log(String username, boolean success, String host)
	{
		EntityManager em = EntityManagerReference.get();
		em.persist(new AuthenticationTrace(username, success, host));
		em.flush();
	}
}