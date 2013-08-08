package com.bleulace.crm.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.mgt.domain.Comment;
import com.bleulace.mgt.domain.Commentable;
import com.bleulace.persistence.EventSourcedEntityMixin;

@Entity
@RooJavaBean(settersByDefault = false)
public class StatusUpdate implements EventSourcedEntityMixin, Commentable.Mixin
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4823420113636155286L;

	@Id
	@Column(nullable = false, updatable = false)
	private String id;

	@Embedded
	private Comment message;

	StatusUpdate()
	{
	}

	StatusUpdate(String id, Account author, String content)
	{
		this.id = id;
		message = new Comment(author, content);
	}
}