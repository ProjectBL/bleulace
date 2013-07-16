package com.bleulace.persistence;

import com.bleulace.utils.spec.Specification;

public interface EventFilterSpecification<T> extends Specification<T>
{
	public Class<T> candidateClass();
}