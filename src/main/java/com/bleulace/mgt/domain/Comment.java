package com.bleulace.mgt.domain;

import org.joda.time.DateTime;

import com.bleulace.crm.domain.Account;

public interface Comment
{
	public Account getAuthor();

	public String getContent();

	public DateTime getDatePosted();
}