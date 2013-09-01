package com.bleulace.infrastructure;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.ContextTest;

public class EventSchedulerTest implements ContextTest
{
	@Autowired
	private EventListenerForTest listener;

	@Test
	public void eventScheduledAndFired() throws Exception
	{
		new Thread(new EventForTest(EVENT_DELAY)).run();
		Thread.sleep(GRACE_PERIOD.getMillis());
		Assert.assertTrue(listener.isCaught());
	}

	private static final Duration EVENT_DELAY = Period.millis(100)
			.toStandardDuration();
	private static final Duration GRACE_PERIOD = Period.millis(500)
			.toStandardDuration();
}
