package com.bleulace.domain.crm.model;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.roo.addon.javabean.RooJavaBean;

@Embeddable
@RooJavaBean(settersByDefault = false)
public class FriendRequest
{
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false)
	private Account requestor;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSent;

	@SuppressWarnings("unused")
	private FriendRequest()
	{
	}

	FriendRequest(Account requestor)
	{
		this.requestor = requestor;
	}
}