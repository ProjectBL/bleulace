package com.bleulace.domain.management.model;

import java.io.Serializable;

import org.junit.Assert;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * VALUE OBJECT
 * 
 * @author Arleigh Dickerson
 * 
 */
@RooEquals
@RooToString
public class ProgressValue implements Progress, Serializable
{
	private final int completed;
	private final int total;

	public ProgressValue(int completed, int total)
	{
		assertValid(completed, total);
		this.completed = completed;
		this.total = total;
	}

	@Override
	public Integer countCompleted()
	{
		return completed;
	}

	@Override
	public Integer countTotal()
	{
		return total;
	}

	private void assertValid(int completed, int total)
	{
		assertNotNegative(completed);
		assertNotNegative(total);
		Assert.assertTrue(completed <= total);
	}

	private void assertNotNegative(int i)
	{
		Assert.assertTrue(i >= 0);
	}

	@Override
	public Float getValue()
	{
		if (total == 0)
		{
			return new Float(0);
		}
		return new Float(completed) / new Float(total);
	}

	@Override
	public boolean isComplete()
	{
		return total != 0 && total == completed;
	}
}
