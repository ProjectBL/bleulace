package com.bleulace.domain;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.bleulace.domain.account.Account;
import com.bleulace.domain.calendar.CalendarEntry;

@Component
public class DatabasePopulator implements
		ApplicationListener<ContextRefreshedEvent>
{
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		Account a = makeAccount();
		CalendarEntry e = new CalendarEntry();
		e.setCaption("Event Title");
		e.setDescription("Event Description");
	}

	private Account makeAccount()
	{
		Account account = new Account();
		account.setFirstName("Arleigh");
		account.setLastName("Dickerson");
		account.setEmail("arleighdickerson@frugalu.com");
		account.setPassword("password");
		return (Account) account.save();
	}
}