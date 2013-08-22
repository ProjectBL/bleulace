package com.bleulace.domain.management.presentation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.utils.spec.CompositeSpecification;
import com.bleulace.utils.spec.Specification;

public enum ScheduleStatus
{
	//@formatter:off
	BUSY(new RsvpSpec(RsvpStatus.ACCEPTED)),
	TENTATIVE(BUSY.spec.not().and(new RsvpSpec(RsvpStatus.PENDING))),
	AVAILABLE(TENTATIVE.spec.not());
	//@formatter:on

	private final Specification<Set<RsvpStatus>> spec;

	private ScheduleStatus(Specification<Set<RsvpStatus>> spec)
	{
		this.spec = spec;
	}

	boolean is(Set<RsvpStatus> rsvps)
	{
		return spec.isSatisfiedBy(rsvps);
	}

	public static ScheduleStatus getStatus(Set<RsvpStatus> rsvps)
	{
		for (ScheduleStatus status : ScheduleStatus.values())
		{
			if (status.is(rsvps))
			{
				return status;
			}
		}
		throw new IllegalStateException();
	}

	static ScheduleStatus getStatus(RsvpStatus... rsvps)
	{
		return getStatus(new HashSet<RsvpStatus>(Arrays.asList(rsvps)));
	}

	private static class RsvpSpec extends
			CompositeSpecification<Set<RsvpStatus>>
	{
		private final RsvpStatus status;

		RsvpSpec(RsvpStatus status)
		{
			this.status = status;
		}

		@Override
		public boolean isSatisfiedBy(Set<RsvpStatus> candidate)
		{
			return candidate.contains(status);
		}
	}
}