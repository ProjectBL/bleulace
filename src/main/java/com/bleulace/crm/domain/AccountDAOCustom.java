package com.bleulace.crm.domain;

import org.axonframework.domain.AggregateRoot;

/**
 * A DAO interface for {@link Account}s. I hesitate to name the class
 * "...Repository" because axon uses Repositories in the DDD sense of the word.
 * 
 * ----------------------------------------------------------------------
 * 
 * TODO : move this statement somewhere where it would make more sense
 * 
 * So, a class named "...DAO" is used to find persistent instances of
 * {@link AggregateRoot}s or components of the aggregate.
 * 
 * And a class named DAO is used to find classes persistent class which are NOT
 * instances of {@link AggregateRoot} or components of the aggregate.
 * ----------------------------------------------------------------------
 * 
 * @author Arleigh Dickerson
 * 
 */
interface AccountDAOCustom
{
	public Account findByEmail(String email);
}