package com.bleulace.ui.web.calendar.provider;

import org.springframework.util.Assert;

import com.bleulace.crm.domain.AccountDAO;
import com.bleulace.utils.ctx.SpringApplicationContext;

@SuppressWarnings("unchecked")
public enum CalendarViewType implements EventDTOProcessorProvider
{
	//@formatter:off
	OWN(DefaultEventProcessorProvider.class,SelfEventProcessorProvider.class), 
	FRIEND(DefaultEventProcessorProvider.class,FriendEventProcessorProvider.class), 
	OTHER(DefaultEventProcessorProvider.class,OtherEventProcessorProvider.class);
	//@formatter:on

	private final EventDTOProcessorProvider provider;

	CalendarViewType(
			Class<? extends EventDTOProcessorProvider>... providerClasses)
	{
		this.provider = new CompositeEventDTOProcessorProvider(providerClasses);
	}

	public static CalendarViewType determineCalendarType(String ownerId,
			String viewerId)
	{
		Assert.notNull(ownerId);
		Assert.notNull(viewerId);

		if (ownerId.equals(viewerId))
		{
			return OWN;
		}

		if (SpringApplicationContext.getBean(AccountDAO.class).areFriends(
				ownerId, viewerId))
		{
			return FRIEND;
		}

		return OTHER;
	}

	public static EventDTOProcessor[] acquireProcessors(String ownerId,
			String viewerId)
	{
		return determineCalendarType(ownerId, viewerId).getProcessors(ownerId,
				viewerId);
	}

	@Override
	public EventDTOProcessor[] getProcessors(String ownerId, String viewerId)
	{
		return provider.getProcessors(ownerId, viewerId);
	}
}