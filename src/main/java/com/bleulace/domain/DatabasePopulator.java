package com.bleulace.domain;

import java.util.Date;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.account.Account;
import com.bleulace.domain.calendar.CalendarEntry;
import com.bleulace.domain.calendar.ParticipationStatus;
import com.frugalu.api.messaging.jpa.EntityManagerReference;

@Component
public class DatabasePopulator implements
		ApplicationListener<ContextRefreshedEvent>
{
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		Account a = makeAccount();
		a = (Account) a.save();

		Account b = makeAccount();
		b.setEmail("foo@bar.com");
		b.setFirstName("Jeff");
		b = (Account) b.save();

		CalendarEntry entry = new CalendarEntry();
		entry.setCaption("foo");
		entry.setDescription("bar");
		entry.setAllDay(true);
		entry.setStart(new Date());
		entry.getParticipants().put(a, ParticipationStatus.ACCEPTED);

		EntityManagerReference.get().persist(entry);
	}

	private Account makeAccount()
	{
		Account account = new Account();
		account.setFirstName("Arleigh");
		account.setLastName("Dickerson");
		account.setEmail("arleighdickerson@frugalu.com");
		account.setPassword("password");
		return account;
	}
}