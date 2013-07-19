package com.bleulace.mgt.domain;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.mgt.application.command.CreateEventCommand;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class EventCommandTest implements CommandGatewayAware
{
	@Autowired
	private CreateEventCommand createEventCommand;

	@PersistenceContext
	private EntityManager em;

	@PostConstruct
	protected void init()
	{
		repo = new SimpleJpaRepository<Event, String>(Event.class, em);
	}

	private JpaRepository<Event, String> repo;

	@Test
	public void testCreateEventCommand()
	{
		long count = repo.count();
		gateway().send(createEventCommand);
		Assert.assertEquals(count + 1, repo.count());
	}
}
