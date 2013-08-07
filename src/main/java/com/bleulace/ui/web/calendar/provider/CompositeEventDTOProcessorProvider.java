package com.bleulace.ui.web.calendar.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bleulace.utils.ctx.SpringApplicationContext;

class CompositeEventDTOProcessorProvider implements EventDTOProcessorProvider
{
	private Class<? extends EventDTOProcessorProvider>[] children;

	@Override
	public EventDTOProcessor[] getProcessors(String ownerId, String viewerId)
	{
		List<EventDTOProcessor> processors = new ArrayList<EventDTOProcessor>();
		for (Class<? extends EventDTOProcessorProvider> child : children)
		{
			processors.addAll(Arrays.asList(SpringApplicationContext.getBean(
					child).getProcessors(ownerId, viewerId)));
		}
		return processors.toArray(new EventDTOProcessor[processors.size()]);
	}

	CompositeEventDTOProcessorProvider(
			Class<? extends EventDTOProcessorProvider>... providerClasses)
	{
		children = providerClasses;
	}
}