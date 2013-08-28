package com.bleulace.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.ViewChangeListener;

@Component
@Scope("session")
class PresenterSubscribingListener implements ViewChangeListener
{
	private static final Logger LOGGER = Logger
			.getLogger(PresenterSubscribingListener.class);

	private Map<String, Set<AnnotationEventListenerAdapter>> map = new HashMap<String, Set<AnnotationEventListenerAdapter>>();

	@Autowired
	@Qualifier("uiBus")
	private EventBus uiBus;

	void registerPresenter(Object presenter)
	{
		for (String viewName : presenter.getClass()
				.getAnnotation(Presenter.class).viewNames())
		{
			if (map.get(viewName) == null)
			{
				map.put(viewName, new HashSet<AnnotationEventListenerAdapter>());
			}
			map.get(viewName).add(
					new AnnotationEventListenerAdapter(presenter, uiBus));
		}
	}

	@Override
	public boolean beforeViewChange(ViewChangeEvent event)
	{
		unsubscribeAll();
		subscribeInterestedListeners(event.getViewName());
		return true;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event)
	{
	}

	private void unsubscribeAll()
	{
		for (Set<AnnotationEventListenerAdapter> adapters : map.values())
		{
			for (AnnotationEventListenerAdapter adapter : adapters)
			{
				adapter.unsubscribe();
			}
		}
	}

	private void subscribeInterestedListeners(String viewName)
	{
		Iterable<AnnotationEventListenerAdapter> adapters = map.get(viewName);
		if (adapters == null)
		{
			LOGGER.warn("View with name '"
					+ viewName
					+ "' has not been registered to PresenterSubscribingListener");
			return;
		}
		for (AnnotationEventListenerAdapter adapter : adapters)
		{
			adapter.subscribe();
		}
	}
}