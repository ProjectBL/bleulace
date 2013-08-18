package com.bleulace.domain.management.presentation;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import com.bleulace.UnitTest;
import com.bleulace.domain.management.model.RsvpStatus;

public class ScheduleStatusTest implements UnitTest
{
	@Test
	public void testAll()
	{
		doAssertion(true, ScheduleStatus.BUSY, RsvpStatus.ACCEPTED);
		doAssertion(true, ScheduleStatus.BUSY, RsvpStatus.ACCEPTED,
				RsvpStatus.PENDING);
		doAssertion(false, ScheduleStatus.BUSY, RsvpStatus.PENDING);
		doAssertion(true, ScheduleStatus.TENTATIVE, RsvpStatus.PENDING);
	}

	private void doAssertion(boolean value, ScheduleStatus status,
			RsvpStatus... rsvps)
	{
		Assert.assertTrue(value == status.is(new HashSet<RsvpStatus>(Arrays
				.asList(rsvps))));
	}
}
