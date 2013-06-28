package com.bleulace.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.account.Account;
import com.bleulace.domain.calendar.JPACalendarEvent;
import com.bleulace.domain.calendar.ParticipationStatus;

@Profile({ "dev", "prod" })
@Component
public class DatabasePopulator implements
		ApplicationListener<ContextRefreshedEvent>
{
	@Autowired
	@Qualifier("mockAccountIterator")
	private Iterable<Account> accountData;

	private List<Account> accounts = new ArrayList<Account>();

	@PersistenceContext
	private EntityManager entityManager;

	private final Random random = new Random(System.currentTimeMillis());

	private JpaRepository<Account, String> accountDAO;

	private static final Integer NUMBER_OF_ACCOUNTS = 20;

	@PostConstruct
	protected void init()
	{
		accountDAO = new SimpleJpaRepository<Account, String>(Account.class,
				entityManager);
	}

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
	{
		if (accountDAO.count() < NUMBER_OF_ACCOUNTS)
		{
			Iterator<Account> it = accountData.iterator();
			for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++)
			{
				accounts.add(accountDAO.save(it.next()));
			}

			for (Account host : accounts)
			{
				addParticipants(makeEventForHost(host));
			}
		}
	}

	protected boolean shouldPopulate()
	{
		return accountDAO.count() < NUMBER_OF_ACCOUNTS;
	}

	private JPACalendarEvent makeEventForHost(Account host)
	{
		JPACalendarEvent event = new JPACalendarEvent();
		event.setCaption("Test Event Title");
		event.setDescription("Test Event Description");

		LocalDateTime now = LocalDateTime.now().plusMinutes(1);
		LocalDateTime startDT = now.plusMinutes(getRandom(0, 59))
				.plusHours(getRandom(0, 23)).plusDays(getRandom(0, 4));
		LocalDateTime endDT = startDT.plusMinutes(getRandom(30, 180));

		event.setStart(startDT.toDate());
		event.setEnd(endDT.toDate());

		event.getParticipants().put(host, ParticipationStatus.HOST);
		entityManager.persist(event);
		return event;
	}

	private JPACalendarEvent addParticipants(JPACalendarEvent event)
	{
		ParticipationStatus[] statuses = new ParticipationStatus[] {
				ParticipationStatus.ACCEPTED, ParticipationStatus.DECLINED,
				ParticipationStatus.PENDING };

		for (int i = 0; i < getRandom(1, 3); i++)
		{
			for (ParticipationStatus status : statuses)
			{
				Account account;
				do
				{
					account = randomAccount();
				}
				while (event.getParticipants().keySet().contains(account));
				event.getParticipants().put(account, status);
			}
		}
		return entityManager.merge(event);
	}

	private Account randomAccount()
	{
		return accounts.get(getRandom(0, accounts.size() - 1));
	}

	private int getRandom(int min, int max)
	{
		return random.nextInt(max - min + 1) + min;
	}
}