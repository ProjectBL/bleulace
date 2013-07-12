package com.bleulace.persistence.infrastructure;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;

import com.mysema.query.annotations.QueryExclude;

public aspect AggregateRootAspect
{
	declare @type : AbstractAnnotatedAggregateRoot : @QueryExclude;
}