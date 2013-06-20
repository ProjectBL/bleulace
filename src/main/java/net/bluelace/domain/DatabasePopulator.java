package net.bluelace.domain;

import net.bluelace.domain.account.Account;
import net.bluelace.domain.calendar.CalendarEntry;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

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