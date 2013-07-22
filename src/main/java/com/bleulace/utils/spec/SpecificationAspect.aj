package com.bleulace.utils.spec;

aspect SpecificationAspect
{
	public Specification<T> Specification<T>.and(Specification<T> other)
	{
		return new AndSpecification<T>(this, other);
	}

	public Specification<T> Specification<T>.or(Specification<T> other)
	{
		return new OrSpecification<T>(this, other);
	}

	public Specification<T> Specification<T>.not()
	{
		return new NotSpecification<T>(this);
	}
}
