package com.bleulace.domain.resource.model;


privileged aspect ResourcingAspect
{
	pointcut requiresCompatibility(CompositeResource composite,
			Resource component) : 
		execution(public * CompositeResource+.*(..,Resource+,..)) 
		&& target(composite) 
		&& args(component);

	pointcut compatibilityCheck() : 
		execution(boolean CompositeResource+.isCompatible(*));

	before(CompositeResource composite, Resource component) : 
		requiresCompatibility(composite,component)
		&& !compatibilityCheck()

	{
		doCompatibilityCheck(composite, component);
	}

	private void doCompatibilityCheck(CompositeResource composite,
			Resource component)
	{
		if (!composite.isCompatible(component))
		{
			throw new IllegalArgumentException();
		}
	}
}