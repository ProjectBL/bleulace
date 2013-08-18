package com.bleulace.utils.spec;

/**
 * 
 * @author Slawek
 * 
 * @param <T>
 */
class NotSpecification<T> extends CompositeSpecification<T>
{
	private Specification<T> wrapped;

	public NotSpecification(Specification<T> wrapped)
	{
		this.wrapped = wrapped;
	}

	@Override
	public boolean isSatisfiedBy(T candidate)
	{
		return !wrapped.isSatisfiedBy(candidate);
	}
}
