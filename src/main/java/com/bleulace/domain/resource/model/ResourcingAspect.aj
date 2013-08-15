package com.bleulace.domain.resource.model;

import javax.persistence.EntityManager;

aspect ResourcingAspect
{
	pointcut merge() : call(public void EntityManager.persist(*)) 
	&& within(org.axonframework..*);

	before() : merge() {
	}
}