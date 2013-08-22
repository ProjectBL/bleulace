package com.bleulace.domain.management.ui.calendar.view;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.ui.calendar.view.EventDTOProvider.EventDTOProcessor;
import com.bleulace.domain.management.ui.calendar.view.EventDTOProvider.EventDTOProcessorFactory;
import com.bleulace.utils.dto.Factory;

@Factory(makes = EventDTOProcessor.class)
class EventDTOProcessorFactoryImpl implements EventDTOProcessorFactory
{
	@Override
	public EventDTOProcessor make(String ownerId, String viewerId)
	{
		if (ownerId.equals(viewerId))
		{
			return new SelfEventDTOProcessor(ownerId);
		}
		return new ClientEventDTOProcessor(ownerId, viewerId);
	}

	static class ClientEventDTOProcessor extends SelfEventDTOProcessor
	{
		private final String viewerId;

		ClientEventDTOProcessor(String ownerId, String viewerId)
		{
			super(ownerId);
			this.viewerId = viewerId;
		}

		public String getViewerId()
		{
			return viewerId;
		}
	}

	static class SelfEventDTOProcessor implements EventDTOProcessor
	{
		private final String ownerId;

		SelfEventDTOProcessor(String ownerId)
		{
			this.ownerId = ownerId;
		}

		@Override
		public void process(EventDTO dto)
		{
			dto.setStyleName(dto.getRsvpStatus(ownerId).getStyleName());
		}

		public String getOwnerId()
		{
			return ownerId;
		}
	}
}