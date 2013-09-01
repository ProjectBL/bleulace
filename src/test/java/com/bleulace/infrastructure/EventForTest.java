package com.bleulace.infrastructure;

import java.io.Serializable;

import org.axonframework.eventhandling.scheduling.quartz.QuartzEventScheduler;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
class EventForTest implements Runnable, Serializable
{
	@Autowired
	private transient QuartzEventScheduler quartzEventScheduler;
	private final Duration duration;

	public EventForTest(Duration duration)
	{
		this.duration = duration;
	}

	@Override
	public void run()
	{
		quartzEventScheduler.schedule(LocalDateTime.now().plus(duration)
				.toDateTime(), this);
	}
}