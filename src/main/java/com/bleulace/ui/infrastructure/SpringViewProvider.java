package com.bleulace.ui.infrastructure;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.navigator.Navigator.ClassBasedViewProvider;
import com.vaadin.navigator.View;

public class SpringViewProvider extends ClassBasedViewProvider
{
	private static final long serialVersionUID = -3847157174338421311L;

	public SpringViewProvider(String viewName, Class<? extends View> viewClass)
	{
		super(viewName, viewClass);
	}

	@Override
	public View getView(String viewName)
	{
		if (getViewName().equals(viewName))
		{
			try
			{
				return SpringApplicationContext.getBean(View.class,
						getViewName());
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		return null;
	}
}