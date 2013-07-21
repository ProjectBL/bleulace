package com.bleulace.feed;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;
import com.bleulace.utils.ctx.SpringApplicationContext;

@RooJavaBean
public class FeedSubject implements Serializable
{
	private static final long serialVersionUID = -4977473248870257642L;

	private byte[] image;
	private String firstName;
	private String lastName;

	FeedSubject(Account account)
	{
		SpringApplicationContext.getBean(ModelMapper.class).map(account, this);
	}

	@SuppressWarnings("unused")
	private FeedSubject()
	{
	}
}
