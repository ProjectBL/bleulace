package com.bleulace.mgt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import com.bleulace.crm.domain.Account;

@Embeddable
public class BasicComment implements Comment
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
	private BasicComment()
	{
	}

	public BasicComment(Account author, String content)
	{
		this.author = author;
		this.content = content;
	}

	@Override
	public Account getAuthor()
	{
		return author;
	}

	@Override
	public String getContent()
	{
		return content;
	}

	@Override
	public DateTime getDatePosted()
	{
		return new DateTime(datePosted);
	}
}