package com.bleulace.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import com.bleulace.web.stereotype.Presenter;
import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.navigator.ViewChangeListener;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
@UIComponent("presenterSubscribingListener")
class PresenterSubscribingListener implements ViewChangeListener,
		BeanPostProcessor
{
	private static final Logger LOGGER = Logger
			.getLogger(PresenterSubscribingListener.class);

	private transient Map<String, Set<AnnotationEventListenerAdapter>> map = new HashMap<String, Set<AnnotationEventListenerAdapter>>();

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	@Qualifier("uiBus")
	private transient EventBus uiBus;

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

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException
	{
		if (AnnotationUtils.findAnnotation(bean.getClass(), Presenter.class) != null)
		{
			ctx.getBean(PresenterSubscribingListener.class).registerPresenter(
					bean);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException
	{
		return bean;
	}

	private void registerPresenter(Object presenter)
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
}