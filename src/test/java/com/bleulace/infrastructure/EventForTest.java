package com.bleulace.infrastructure;

import java.io.Serializable;

import org.axonframework.eventhandling.scheduling.quartz.QuartzEventScheduler;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;

import com.bleulace.utils.ctx.SpringApplicationContext;

class EventForTest implements Runnable, Serializable
{
	private final Duration duration;

	public EventForTest(Duration duration)
	{
		this.duration = duration;
	}

	@Override
	public void run()
	{
		SpringApplicationContext.getBean(QuartzEventScheduler.class).schedule(
				LocalDateTime.now().plus(duration).toDateTime(), this);
	}
}