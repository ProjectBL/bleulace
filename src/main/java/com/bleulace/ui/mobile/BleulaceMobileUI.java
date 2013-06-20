package com.bleulace.ui.mobile;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("touch")
@Title("bluelace devel")
public class BleulaceMobileUI extends UI
{
	private static final long serialVersionUID = 2378161374437587033L;

	private Map<Class<? extends NavigationView>, NavigationView> views = new HashMap<Class<? extends NavigationView>, NavigationView>();

	@Override
	protected void init(VaadinRequest request)
	{
		setContent(new LoginView());
	}

	public NavigationView getView(Class<? extends NavigationView> viewClazz)
	{
		NavigationView value = views.get(viewClazz);
		if (value == null)
		{
			try
			{
				value = viewClazz.newInstance();
				views.put(viewClazz, value);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return value;

	}
}