package com.bleulace.domain.project.calendar;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ContextConfiguration(locations = { "classpath:META-INF/spring/applicationContext.xml" })
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class CalendarEntryTest
{
	public void testCalendarEntry()
	{

	}
}
