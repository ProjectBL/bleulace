package com.bleulace.domain.crm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.resource.model.AbstractChildResource;

@Entity
@RooJavaBean
public class Comment extends AbstractChildResource
{
	@Column(nullable = false, updatable = false)
	private String content;

	@ManyToOne
	@JoinColumn(nullable = false, updatable = false)
	private Account author;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date datePosted;

	Comment()
	{
	}
}
