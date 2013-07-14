/**
 * @author Arleigh Dickerson
 * 
 * Marks axon infrastructures for Q-class generation
 */
@com.mysema.query.annotations.QueryEntities({
		org.axonframework.eventsourcing.AbstractEventSourcedEntity.class,
		org.axonframework.eventsourcing.AbstractEventSourcedAggregateRoot.class }) package org.axonframework.eventsourcing;