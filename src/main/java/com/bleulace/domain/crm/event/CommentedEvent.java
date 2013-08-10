package com.bleulace.domain.crm.event;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.command.CommentCommand;

@RooJavaBean
public class CommentedEvent extends CommentCommand
{
	private String accountId;

	private Date datePosted;

	public CommentedEvent(String id, Date datePosted)
	{
		super(id);
		this.datePosted = datePosted;
	}

}
