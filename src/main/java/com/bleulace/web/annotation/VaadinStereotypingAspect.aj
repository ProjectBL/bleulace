package com.bleulace.web.annotation;

import com.vaadin.navigator.View;
import com.vaadin.ui.UI;

aspect VaadinStereotypingAspect
{
	declare parents : @VaadinView * implements View;

	pointcut pushEnabled() : execution(@EnablePush * *(..));

	Object around() : pushEnabled()
	{
		PushCallback callback = new PushCallback()
		{
			public void run()
			{
				retVal = proceed();
			}
		};
		UI.getCurrent().access(callback);
		return callback.retVal;
	}

	private abstract class PushCallback implements Runnable
	{
		Object retVal;
	}
}