package com.bleulace.jpa;

import java.util.Collection;

import javax.persistence.Basic;
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
import org.modelmapper.ModelMapper;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.utils.jpa.EntityManagerReference;

/**
 * Copy and paste job...
 * 
 * @author Arleigh Dickerson
 * 
 */
public interface EventSourcedAggregateRootMixin extends
		EventSourcedAggregateRoot<String>
{
	public String getId();

	static aspect Impl
	{
		private transient boolean EventSourcedAggregateRootMixin.deletionFlag = false;

		@Transient
		private volatile EventContainer EventSourcedAggregateRootMixin.eventContainer;

		@Basic(optional = true)
		private Long EventSourcedAggregateRootMixin.lastEventSequenceNumber;

		private transient AnnotationEventHandlerInvoker EventSourcedAggregateRootMixin.eventHandlerInvoker;
		private transient AggregateAnnotationInspector EventSourcedAggregateRootMixin.inspector;

		public boolean EventSourcedAggregateRootMixin.isNew()
		{
			return EntityManagerReference.get().find(getClass(), this.getId()) != null;
		}

		public boolean EventSourcedAggregateRootMixin.isDeleted()
		{
			return this.deletionFlag;
		}

		void EventSourcedAggregateRootMixin.markForDeletion()
		{
			this.deletionFlag = true;
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

		void EventSourcedAggregateRootMixin.map(Object source)
		{
			mapper().map(source, this);
		}

		ModelMapper EventSourcedAggregateRootMixin.mapper()
		{
			return SpringApplicationContext.getBean(ModelMapper.class);
		}

		void EventSourcedAggregateRootMixin.apply(Object command,
				Class<?> eventClazz)
		{
			apply(mapper().map(command, eventClazz));
		}

		public void EventSourcedAggregateRootMixin.apply(Object command,
				Object event)
		{
			mapper().map(command, event);
			apply(event);
		}

		@SuppressWarnings("rawtypes")
		private void EventSourcedAggregateRootMixin.handle(
				DomainEventMessage event)
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
