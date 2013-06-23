package com.bleulace.domain;

import org.joda.time.LocalDateTime;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.account.Account;
import com.bleulace.domain.calendar.JPACalendarEvent;
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

		JPACalendarEvent entry = new JPACalendarEvent();
		entry.setCaption("foo");
		entry.setDescription("bar");
		entry.setStart(LocalDateTime.now().plusHours(1).toDate());
		entry.setEnd(LocalDateTime.now().plusHours(3).toDate());
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