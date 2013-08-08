package com.bleulace.mgt.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.crm.domain.Account;

@RooJavaBean(settersByDefault = false)
@Embeddable
public class Comment implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1437726217208679449L;

	@ManyToOne
	@JoinColumn(name = "AUTHOR_ID", updatable = false, nullable = false)
	private Account author;

	@Column(nullable = false, updatable = false)
	private String content;

	@Column(insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datePosted;

	public Comment()
	{
	}

	public Comment(Account author, String content)
	{
		Assert.notNull(author);
		Assert.notNull(content);
		this.author = author;
		this.content = content;
	}
}