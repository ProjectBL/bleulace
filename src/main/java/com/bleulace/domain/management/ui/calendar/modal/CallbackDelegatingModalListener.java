package com.bleulace.domain.management.ui.calendar.modal;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.management.ui.calendar.model.EventModel;
import com.bleulace.domain.management.ui.calendar.model.EventModel.EventModelCallback;

@RooJavaBean
@Configurable(preConstruction = true)
public class CallbackDelegatingModalListener implements
		CalendarModal.CalendarModalListener
{
	private EventModelCallback apply;
	private EventModelCallback cancel;
	private EventModelCallback delete;

	CallbackDelegatingModalListener()
	{
	}

	CallbackDelegatingModalListener(EventModelCallback apply,
			EventModelCallback cancel, EventModelCallback delete)
	{
		this.apply = apply;
		this.cancel = cancel;
		this.delete = delete;
	}

	@Override
	public void apply(EventModel model)
	{
		doCallback(apply, model);
	}

	@Override
	public void delete(EventModel model)
	{
		doCallback(delete, model);
	}

	@Override
	public void cancel(EventModel model)
	{
		doCallback(cancel, model);
	}

	private void doCallback(EventModelCallback callback, EventModel model)
	{
		if (callback != null)
		{
			callback.execute(model);
		}
	}
}