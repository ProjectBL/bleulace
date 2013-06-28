package com.bleulace.domain.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.account.Account;

@Entity
@RooJavaBean
public class Message extends AbstractPersistable<Long>
{
	private static final long serialVersionUID = 704926750683987643L;

	@Column(nullable = false, updatable = false)
	private String title;

	@Column(nullable = false, updatable = false)
	private String body;

	@Temporal(TemporalType.TIMESTAMP)
	private Date sendTime;

	@ManyToOne
	private Account from;

	@ManyToMany
	private List<Account> to = new ArrayList<Account>();
}