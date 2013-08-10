package com.bleulace.domain.resource.model;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.domain.IdentifierFactory;

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

	pointcut initByCommand(AbstractResource resource) : 
		execution(@CommandHandler AbstractResource+.new(*)) 
		&& this(resource);

	before(AbstractResource resource) : initByCommand(resource)
	{
		resource.id = IdentifierFactory.getInstance().generateIdentifier();
	}
}