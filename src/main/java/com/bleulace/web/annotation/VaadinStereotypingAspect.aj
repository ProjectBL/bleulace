package com.bleulace.web.annotation;

import com.vaadin.ui.UI;

aspect VaadinStereotypingAspect
{
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
		Object retVal = null;
	}
}