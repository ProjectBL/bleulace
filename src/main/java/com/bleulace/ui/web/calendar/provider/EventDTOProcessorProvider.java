package com.bleulace.ui.web.calendar.provider;

interface EventDTOProcessorProvider
{
	EventDTOProcessor[] getProcessors(String ownerId, String viewerId);
}