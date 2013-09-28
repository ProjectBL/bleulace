package com.bleulace.domain.crm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.resource.model.AbstractResource;

@Entity
@RooJavaBean(settersByDefault = false)
public class Comment extends AbstractResource
{
	@Column(nullable = false, updatable = false)
	private String content;

	@ManyToOne
	@JoinColumn(nullable = false, updatable = false)
	private Account author;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date datePosted;

	public Comment(String content, Account author, Date datePosted)
	{
		this.content = content;
		this.author = author;
		this.datePosted = datePosted;
	}

	@SuppressWarnings("unused")
	private Comment()
	{
	}

	@Override
	public String getTitle()
	{
		return content;
	}
}
