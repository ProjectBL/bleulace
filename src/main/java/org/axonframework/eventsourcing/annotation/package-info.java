/**
 * @author Arleigh Dickerson
 * 
 * Marks axon infrastructures for Q-class generation
 */
@com.mysema.query.annotations.QueryEntities({
		org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot.class,
		org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity.class,
		org.axonframework.domain.AbstractAggregateRoot.class }) package org.axonframework.eventsourcing.annotation;