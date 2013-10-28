package com.bleulace.web.annotation;

import com.vaadin.navigator.View;
import com.vaadin.ui.UI;

aspect VaadinStereotypingAspect
{
	declare parents : @VaadinView * implements View;

	pointcut pushEnabled() : execution(@EnablePush void *(..));

	void around() : pushEnabled()
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