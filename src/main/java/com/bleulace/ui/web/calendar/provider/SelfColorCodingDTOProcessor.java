package com.bleulace.ui.web.calendar.provider;

import org.springframework.util.Assert;

import com.bleulace.mgt.presentation.EventDTO;

class SelfColorCodingDTOProcessor implements EventDTOProcessor
{
	private final String accountId;

	public SelfColorCodingDTOProcessor(String accountId)
	{
		Assert.notNull(accountId);
		this.accountId = accountId;
	}

	@Override
	public void process(EventDTO dto)
	{
		if (dto.isAttending(accountId))
		{
			dto.setStyleName("accepted");
		}
		else
		{
			dto.setStyleName("pending");
		}
	}
}
