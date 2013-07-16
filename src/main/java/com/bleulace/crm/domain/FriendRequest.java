package com.bleulace.crm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean(settersByDefault = false)
public class FriendRequest implements Serializable
{
	private static final long serialVersionUID = 6420332885690602003L;

	@Id
	@ManyToOne
	@JoinColumn(name = "INITIATOR_ID", nullable = false, updatable = false)
	private Account initiator;

	@Id
	@ManyToOne
	@JoinColumn(name = "ACCEPTOR_ID", nullable = false, updatable = false)
	private Account acceptor;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSent;

	@SuppressWarnings("unused")
	private FriendRequest()
	{
	}

	public FriendRequest(Account initiator, Account acceptor)
	{
		this.initiator = initiator;
		this.acceptor = acceptor;
	}
}