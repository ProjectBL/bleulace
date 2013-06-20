package com.bleulace.domain.calendar;

import com.bleulace.domain.account.Account;
import com.frugalu.api.messaging.jpa.EntityManagerReference;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.ui.components.calendar.ContainerEventProvider;

public class BLContainerEventProvider extends ContainerEventProvider
{
	private static final long serialVersionUID = 7849303393769338946L;

	public BLContainerEventProvider(Account account)
	{
		super(make(account));
	}

	private static JPAContainer<CalendarEntry> make(Account account)
	{
		JPAContainer<CalendarEntry> container = JPAContainerFactory.make(
				CalendarEntry.class, EntityManagerReference.get());
		container.sort(new Object[] { "start" }, new boolean[] { true });
		container
				.setQueryModifierDelegate(new BLQueryModifierDelegate(account));
		return container;
	}
}
