package com.bleulace.ddd.spec;

/**
 * 
 * @author Slawek
 * 
 * @param <T>
 */
public interface Specification<T>
{
	Specification<T> and(Specification<T> other);

	Specification<T> or(Specification<T> other);

	Specification<T> not();

	boolean isSatisfiedBy(T candidate);
}
