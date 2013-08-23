package com.bleulace.domain.crm.ui.front;

import javax.annotation.PostConstruct;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
@Component
@Scope("session")
class FrontView extends CustomComponent implements View
{
	@Autowired
	@Qualifier("uiBus")
	private EventBus uiBus;

	@Autowired
	private LoginForm loginForm;

	FrontView()
	{
	}

	@PostConstruct
	protected void init()
	{
		AnnotationEventListenerAdapter.subscribe(this, uiBus);
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		setCompositionRoot(loginForm);
	}

	@EventHandler
	public void foo(String str)
	{
		Notification.show(str);
	}
}
