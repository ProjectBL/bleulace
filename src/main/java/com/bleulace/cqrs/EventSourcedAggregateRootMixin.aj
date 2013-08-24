package com.bleulace.cqrs;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Transient;

import org.axonframework.common.Assert;
import org.axonframework.common.annotation.MessageHandlerInvoker;
import org.axonframework.common.annotation.MethodMessageHandler;
import org.axonframework.domain.AggregateIdentifierNotInitializedException;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.domain.DomainEventStream;
import org.axonframework.domain.EventContainer;
import org.axonframework.domain.EventRegistrationCallback;
import org.axonframework.domain.GenericDomainEventMessage;
import org.axonframework.domain.MetaData;
import org.axonframework.domain.SimpleDomainEventStream;
import org.axonframework.eventhandling.annotation.AnnotationEventHandlerInvoker;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.annotation.EventHandlerInvocationException;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.eventsourcing.EventSourcedEntity;
import org.axonframework.eventsourcing.IncompatibleAggregateException;
import org.axonframework.eventsourcing.annotation.AggregateAnnotationInspector;
import org.springframework.data.domain.Persistable;

import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.utils.dto.Mapper;

/**
 * Copy and paste job...
 * 
 * @author Arleigh Dickerson
 * 
 */
public interface EventSourcedAggregateRootMixin extends
		EventSourcedAggregateRoot<String>, Persistable<String>
{
	public String getId();

	static aspect Impl
	{
		@Transient
		private boolean EventSourcedAggregateRootMixin.deleted = false;

		@Transient
		private volatile EventContainer EventSourcedAggregateRootMixin.eventContainer;

		@Basic(optional = true)
		private Long EventSourcedAggregateRootMixin.lastEventSequenceNumber;

		private transient AnnotationEventHandlerInvoker EventSourcedAggregateRootMixin.eventHandlerInvoker;
		private transient AggregateAnnotationInspector EventSourcedAggregateRootMixin.inspector;

		public boolean EventSourcedAggregateRootMixin.isNew()
		{
			return EntityManagerReference.get().find(getClass(), this.getId()) == null;
		}

		public boolean EventSourcedAggregateRootMixin.isDeleted()
		{
			return deleted;
		}

		void EventSourcedAggregateRootMixin.flagForDeletion()
		{
			deleted = true;
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
			return this.getId();
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

		public boolean EventSourcedAggregateRootMixin.equals(Object obj)
		{
			if (null == obj)
			{
				return false;
			}

			if (this == obj)
			{
				return true;
			}

			if (!getClass().equals(obj.getClass()))
			{
				return false;
			}

			EventSourcedAggregateRootMixin that = (EventSourcedAggregateRootMixin) obj;

			return null == this.getId() ? false : this.getId().equals(
					that.getId());
		}

		@SuppressWarnings("rawtypes")
		private void EventSourcedAggregateRootMixin.handle(
				DomainEventMessage event)
		{
			ensureInspectorInitialized();
			try
			{
				MethodMessageHandler handler = new MessageHandlerInvoker(this,
						EventHandler.class, false).findHandlerMethod(event);
				if (handler == null)
				{
					Mapper.map(event.getPayload(), this);
				}
				else
				{
					handler.invoke(this, event);
				}
			}
			catch (IllegalAccessException e)
			{
				throw new EventHandlerInvocationException(
						"Access to the event handler method was denied.", e);
			}
			catch (InvocationTargetException e)
			{
				if (e.getCause() instanceof RuntimeException)
				{
					throw (RuntimeException) e.getCause();
				}
				throw new EventHandlerInvocationException(
						"An exception occurred while invoking the handler method.",
						e);
			}
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

		public void EventSourcedAggregateRootMixin.apply(Object eventPayload,
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

		@SuppressWarnings("rawtypes")
		public void EventSourcedAggregateRootMixin.handleRecursively(
				DomainEventMessage event)
		{
			handle(event);
			Iterable<? extends EventSourcedEntity> childEntities = getChildEntities();
			if (childEntities != null)
			{
				for (EventSourcedEntity entity : childEntities)
				{
					if (entity != null)
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
