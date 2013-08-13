package com.bleulace.infrastructure;

import java.util.concurrent.Executor;

import org.joda.time.Duration;
import org.joda.time.Seconds;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class EventSchedulerTest
{
	@Autowired
	private Executor executor;

	@Autowired
	private EventListenerForTest listener;

	@Test
	public void eventScheduledAndFired() throws Exception
	{
		executor.execute(new EventForTest(EVENT_DELAY));
		Thread.sleep(GRACE_PERIOD.getMillis());
		Assert.assertTrue(listener.isCaught());
	}

	private static final Duration EVENT_DELAY = Seconds.seconds(2)
			.toStandardDuration();
	private static final Duration GRACE_PERIOD = Seconds.seconds(2)
			.toStandardDuration();
}
