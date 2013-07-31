package com.bleulace.ui.infrastructure;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.cqrs.event.EventBusPublisher;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.navigator.View;
import com.vaadin.ui.UI;

aspect VaadinViewAspect
{
	declare parents :@VaadinView * implements View;
	declare parents :@VaadinView * implements EventBusPublisher;
	declare parents :@VaadinView * implements CommandGatewayAware;
	declare parents :@VaadinView * implements IVaadinView;

	private interface IVaadinView
	{
	}

	private final transient AnnotationEventListenerAdapter IVaadinView.adapter = new AnnotationEventListenerAdapter(
			this, SpringApplicationContext.getBean(EventBus.class));

	@PostConstruct
	private void IVaadinView.init()
	{
		adapter.subscribe();
	}
	
	@PreDestroy
	private void IVaadinView.destroy()
	{
		adapter.unsubscribe();
	}
	
	void around() : execution(@ServerPush void *(..))
	{
		UI.getCurrent().access(new Runnable()
		{
			@Override
			public void run()
			{
				proceed();
			}
		});
	}
}