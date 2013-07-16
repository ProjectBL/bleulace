package com.bleulace.mgt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;

@RooJavaBean(settersByDefault = false)
@Embeddable
public class Comment
{
	@ManyToOne
	@JoinColumn(updatable = false, nullable = false)
	private Account author;

	@Column(nullable = false, updatable = false)
	private String content;

	@Column(insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datePosted;

	@SuppressWarnings("unused")
	private Comment()
	{
	}

	public Comment(Account author, String content)
	{
		this.author = author;
		this.content = content;
	}
}