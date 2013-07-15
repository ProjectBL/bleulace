package com.bleulace.persistence;

import java.util.Collection;

import org.axonframework.domain.DomainEventMessage;
import org.axonframework.eventhandling.annotation.AnnotationEventHandlerInvoker;
import org.axonframework.eventsourcing.EventSourcedEntity;
import org.axonframework.eventsourcing.annotation.AggregateAnnotationInspector;

public interface EventSourcedEntityMixin extends EventSourcedEntity
{
	static aspect Impl
	{

		private transient AggregateAnnotationInspector EventSourcedEntityMixin.inspector;

		private transient AnnotationEventHandlerInvoker EventSourcedEntityMixin.eventHandlerInvoker = new AnnotationEventHandlerInvoker(
				this);

		private volatile EventSourcedAggregateRootMixin EventSourcedEntityMixin.aggregateRoot;

		public void EventSourcedEntityMixin.registerAggregateRoot(
				EventSourcedAggregateRootMixin aggregateRootToRegister)
		{
			if (this.aggregateRoot != null
					&& this.aggregateRoot != aggregateRootToRegister)
			{
				throw new IllegalStateException(
						"Cannot register new aggregate. "
								+ "This entity is already part of another aggregate");
			}
			this.aggregateRoot = aggregateRootToRegister;
		}

		public void EventSourcedEntityMixin.handleRecursively(
				@SuppressWarnings("rawtypes") DomainEventMessage event)
		{
			handle(event);
			Collection<? extends EventSourcedEntity> childEntities = getChildEntities();
			if (childEntities != null)
			{
				for (EventSourcedEntity entity : childEntities)
				{
					if (entity != null)
					{
						//TODO : uncomment
						//entity.registerAggregateRoot(aggregateRoot);
						entity.handleRecursively(event);
					}
				}
			}
		}

		private void EventSourcedEntityMixin.handle(DomainEventMessage<?> event)
		{
			ensureInspectorInitialized();
			ensureInvokerInitialized();
			eventHandlerInvoker.invokeEventHandlerMethod(event);
		}

		private Collection<EventSourcedEntity> EventSourcedEntityMixin.getChildEntities()
		{
			ensureInspectorInitialized();
			return inspector.getChildEntities(this);
		}

		private void EventSourcedEntityMixin.ensureInvokerInitialized()
		{
			if (eventHandlerInvoker == null)
			{
				eventHandlerInvoker = new AnnotationEventHandlerInvoker(this);
			}
		}

		private void EventSourcedEntityMixin.ensureInspectorInitialized()
		{
			if (inspector == null)
			{
				inspector = AggregateAnnotationInspector
						.getInspector(getClass());
			}
		}
	}
}