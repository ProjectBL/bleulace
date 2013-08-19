package com.bleulace.web;

import javax.annotation.PreDestroy;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.springframework.util.Assert;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.navigator.Navigator.StaticViewProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

public abstract class BusRegisteringViewProvider extends StaticViewProvider
		implements ViewChangeListener
{
	private View view = null;

	private transient AnnotationEventListenerAdapter adapter = null;

	public BusRegisteringViewProvider(String viewName)
	{
		super(notNull(viewName), null);
	}

	@Override
	public View getView(String viewName)
	{
		if (getViewName().equals(viewName))
		{
			return getView();
		}
		return null;
	}

	@Override
	public boolean beforeViewChange(ViewChangeEvent event)
	{
		return true;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event)
	{
		if (view != null)
		{
			if (view.equals(event.getOldView()))
			{
				getAdapter().unsubscribe();
			}

			if (view.equals(event.getNewView()))
			{
				getAdapter().subscribe();
			}
		}
	}

	protected abstract View createView();

	@PreDestroy
	@SuppressWarnings("all")
	protected void preDestroy()
	{
		if (adapter != null)
		{
			adapter.unsubscribe();
		}
	}

	private View getView()
	{
		if (view == null)
		{
			view = createView();
		}
		return view;
	}

	private AnnotationEventListenerAdapter getAdapter()
	{
		if (adapter == null)
		{
			adapter = new AnnotationEventListenerAdapter(getView(),
					SpringApplicationContext.getBean(EventBus.class));
		}
		return adapter;
	}

	private static String notNull(String value)
	{
		Assert.notNull(value);
		return value;
	}
}