package com.bleulace.persistence;

import com.bleulace.ddd.spec.Specification;

public interface EventFilterSpecification<T> extends Specification<T>
{
	public Class<T> candidateClass();
}