package com.bleulace.domain.crm.ui.profile;

class StatusUpdatedEvent
{
	private final String status;

	public StatusUpdatedEvent(String status)
	{
		this.status = status;
	}

	public String getStatus()
	{
		return status;
	}
}