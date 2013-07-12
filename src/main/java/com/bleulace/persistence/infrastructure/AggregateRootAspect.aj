package com.bleulace.persistence.infrastructure;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;

import com.mysema.query.annotations.QueryExclude;

/**
 * Keeps maven-apt from freaking out when we generate query classes. Maven-apt
 * doesn't like axon's AggregateRoot classes, so we are just marking them for
 * exclusion.
 * 
 * @author Arleigh Dickerson
 * 
 */
public aspect AggregateRootAspect
{
	// exclude AbstractAnnotatedAggregateRoot as a candidate for query class
	// generation.
	declare @type : AbstractAnnotatedAggregateRoot : @QueryExclude;
}