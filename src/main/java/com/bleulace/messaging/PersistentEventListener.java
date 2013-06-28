package com.bleulace.messaging;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.frugalu.api.messaging.annotation.RegisterBus;
import com.google.common.eventbus.Subscribe;

@Component
@RegisterBus
public class PersistentEventListener
{
	@PersistenceContext
	private EntityManager entityManager;

	@Subscribe
	@Transactional
	public void onPersistentEvent(PersistentEvent event)
	{
		entityManager.persist(event);
	}
}