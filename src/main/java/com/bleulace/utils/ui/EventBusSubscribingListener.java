package com.bleulace.utils.ui;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.navigator.ViewChangeListener;

@Configurable
class EventBusSubscribingListener implements ViewChangeListener
{
	@Autowired
	private EventBus eventBus;

	private AnnotationEventListenerAdapter adapter;

	@Override
	public boolean beforeViewChange(ViewChangeEvent event)
	{
		if (adapter != null)
		{
			adapter.unsubscribe();
		}
		return true;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event)
	{
		adapter = new AnnotationEventListenerAdapter(event.getNewView(),
				eventBus);
		adapter.subscribe();
	}
}