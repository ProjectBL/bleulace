package com.bleulace.persistence.infrastructure;

import org.axonframework.eventsourcing.AbstractEventSourcedAggregateRoot;
import org.axonframework.eventsourcing.AbstractEventSourcedEntity;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;

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
	declare @type : AbstractAnnotatedAggregateRoot: @QueryExclude;
	declare @type : AbstractEventSourcedAggregateRoot: @QueryExclude;
	declare @type : AbstractAnnotatedEntity: @QueryExclude;
	declare @type : AbstractEventSourcedEntity: @QueryExclude;
}