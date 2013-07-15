package com.bleulace.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.axonframework.common.Assert;
import org.axonframework.domain.AggregateIdentifierNotInitializedException;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.domain.DomainEventStream;
import org.axonframework.domain.EventContainer;
import org.axonframework.domain.EventRegistrationCallback;
import org.axonframework.domain.GenericDomainEventMessage;
import org.axonframework.domain.MetaData;
import org.axonframework.domain.SimpleDomainEventStream;
import org.axonframework.eventhandling.annotation.AnnotationEventHandlerInvoker;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.eventsourcing.EventSourcedEntity;
import org.axonframework.eventsourcing.IncompatibleAggregateException;
import org.axonframework.eventsourcing.annotation.AggregateAnnotationInspector;

public interface EventSourcedAggregateRootMixin extends
		EventSourcedAggregateRoot<String>, Serializable
{
	static aspect Impl
	{

		@Transient
		private volatile EventContainer EventSourcedAggregateRootMixin.eventContainer;

		@Transient
		private boolean EventSourcedAggregateRootMixin.deleted = false;

		@Basic(optional = true)
		private Long EventSourcedAggregateRootMixin.lastEventSequenceNumber;

		private transient AnnotationEventHandlerInvoker EventSourcedAggregateRootMixin.eventHandlerInvoker;
		private transient AggregateAnnotationInspector EventSourcedAggregateRootMixin.inspector;

		@Id
		@Column(nullable = false, updatable = false)
		private String EventSourcedAggregateRootMixin.id;

		public String EventSourcedAggregateRootMixin.getId()
		{
			return id;
		}

		public boolean EventSourcedAggregateRootMixin.isDeleted()
		{
			return deleted;
		}

		public void EventSourcedAggregateRootMixin.addEventRegistrationCallback(
				EventRegistrationCallback eventRegistrationCallback)
		{
			getEventContainer().addEventRegistrationCallback(
					eventRegistrationCallback);
		}

		public DomainEventStream EventSourcedAggregateRootMixin.getUncommittedEvents()
		{
			if (eventContainer == null)
			{
				return SimpleDomainEventStream.emptyStream();
			}
			return eventContainer.getEventStream();
		}

		public void EventSourcedAggregateRootMixin.commitEvents()
		{
			if (eventContainer != null)
			{
				lastEventSequenceNumber = eventContainer
						.getLastSequenceNumber();
				eventContainer.commit();
			}
		}

		public int EventSourcedAggregateRootMixin.getUncommittedEventCount()
		{
			return eventContainer != null ? eventContainer.size() : 0;
		}

		public String EventSourcedAggregateRootMixin.getIdentifier()
		{
			return id;
		}

		public void EventSourcedAggregateRootMixin.initializeState(
				DomainEventStream domainEventStream)
		{
			Assert.state(this.getUncommittedEventCount() == 0,
					"Aggregate is already initialized");
			long lastSequenceNumber = -1;
			while (domainEventStream.hasNext())
			{
				DomainEventMessage<?> event = domainEventStream.next();
				lastSequenceNumber = event.getSequenceNumber();
				handleRecursively(event);
			}
			this.initializeEventStream(lastSequenceNumber);
		}

		public Long EventSourcedAggregateRootMixin.getVersion()
		{
			return this.getLastCommittedEventSequenceNumber();
		}

		private void EventSourcedAggregateRootMixin.setId(String id)
		{
			Assert.notNull(id, "An aggregate root's id can not be set to null");
			if (this.id != null)
			{
				throw new IllegalStateException(
						"The identifier for this aggregate root has already been set.");
			}
			this.id = id;
		}

		private void EventSourcedAggregateRootMixin.handle(
				DomainEventMessage<?> event)
		{
			ensureInspectorInitialized();
			ensureInvokerInitialized();
			eventHandlerInvoker.invokeEventHandlerMethod(event);
		}

		private Collection<EventSourcedEntity> EventSourcedAggregateRootMixin.getChildEntities()
		{
			ensureInspectorInitialized();
			return inspector.getChildEntities(this);
		}

		private void EventSourcedAggregateRootMixin.ensureInvokerInitialized()
		{
			if (eventHandlerInvoker == null)
			{
				eventHandlerInvoker = inspector.createEventHandlerInvoker(this);
			}
		}

		private void EventSourcedAggregateRootMixin.ensureInspectorInitialized()
		{
			if (inspector == null)
			{
				inspector = AggregateAnnotationInspector
						.getInspector(getClass());
			}
		}

		private void EventSourcedAggregateRootMixin.apply(Object eventPayload)
		{
			apply(eventPayload, MetaData.emptyInstance());
		}

		private void EventSourcedAggregateRootMixin.apply(Object eventPayload,
				MetaData metaData)
		{
			if (this.getIdentifier() == null)
			{
				if (this.getUncommittedEventCount() > 0
						|| this.getVersion() != null)
				{
					throw new IncompatibleAggregateException(
							"The Aggregate Identifier has not been initialized. "
									+ "It must be initialized at the latest when the "
									+ "first event is applied.");
				}
				handleRecursively(new GenericDomainEventMessage<Object>(null,
						0, eventPayload, metaData));
				this.registerEvent(metaData, eventPayload);
			}
			else
			{
				DomainEventMessage<?> event = registerEvent(metaData,
						eventPayload);
				handleRecursively(event);
			}
		}

		private void EventSourcedAggregateRootMixin.handleRecursively(
				DomainEventMessage<?> event)
		{
			handle(event);
			Iterable<? extends EventSourcedEntity> childEntities = getChildEntities();
			if (childEntities != null)
			{
				for (EventSourcedEntity entity : childEntities)
				{
					if (entity != null
							&& entity instanceof EventSourcedEntityMixin)
					{
						((EventSourcedEntityMixin) entity)
								.registerAggregateRoot(this);
						entity.handleRecursively(event);
					}
				}
			}
		}

		private <T> DomainEventMessage<T> EventSourcedAggregateRootMixin.registerEvent(
				T payload)
		{
			return registerEvent(MetaData.emptyInstance(), payload);
		}

		private <T> DomainEventMessage<T> EventSourcedAggregateRootMixin.registerEvent(
				MetaData metaData, T payload)
		{
			return getEventContainer().addEvent(metaData, payload);
		}

		private void EventSourcedAggregateRootMixin.markDeleted()
		{
			this.deleted = true;
		}

		private void EventSourcedAggregateRootMixin.initializeEventStream(
				long lastSequenceNumber)
		{
			getEventContainer().initializeSequenceNumber(lastSequenceNumber);
			lastEventSequenceNumber = lastSequenceNumber >= 0 ? lastSequenceNumber
					: null;
		}

		private Long EventSourcedAggregateRootMixin.getLastCommittedEventSequenceNumber()
		{
			if (eventContainer == null)
			{
				return lastEventSequenceNumber;
			}
			return eventContainer.getLastCommittedSequenceNumber();
		}

		private EventContainer EventSourcedAggregateRootMixin.getEventContainer()
		{
			if (eventContainer == null)
			{
				Object identifier = this.getIdentifier();
				if (identifier == null)
				{
					throw new AggregateIdentifierNotInitializedException(
							"AggregateIdentifier is unknown in ["
									+ getClass().getName()
									+ "]. "
									+ "Make sure the Aggregate Identifier is initialized before registering events.");
				}
				eventContainer = new EventContainer(identifier);
				eventContainer
						.initializeSequenceNumber(this.lastEventSequenceNumber);
			}
			return eventContainer;
		}
	}
}
