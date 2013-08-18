package com.bleulace.domain.management.model;

public interface Progress
{
	public Integer countCompleted();

	public Integer countTotal();

	public Float getValue();

	public boolean isComplete();
}