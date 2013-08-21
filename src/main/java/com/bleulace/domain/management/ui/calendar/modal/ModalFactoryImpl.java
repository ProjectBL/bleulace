package com.bleulace.domain.management.ui.calendar.modal;

import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.bleulace.domain.management.ui.calendar.modal.CalendarModal.CalendarModalListener;
import com.bleulace.domain.management.ui.calendar.model.CommandCallbackFactory;
import com.bleulace.domain.management.ui.calendar.model.EventModel;
import com.bleulace.utils.dto.Factory;
import com.vaadin.ui.Window;

@Factory(makes = { Window.class })
public class ModalFactoryImpl implements ModalFactory
{
	@Autowired
	private CommandCallbackFactory factory;

	@Override
	public Window make(EventModel model)
	{
		EditEventContent content = new EditEventContent(model);
		CalendarModalImpl modal = new CalendarModalImpl(content);
		modal.setModelCallback(content);

		CalendarViewContext ctx = model.getContext();
		boolean isNew = model.isNew();
		CalendarModalListener listener = isNew ? makeCreateListener(ctx)
				: makeEditListener(ctx);
		modal.addModalListener(listener);
		modal.addModalListener(new ClosingListener(modal));
		modal.setCaption(isNew ? "Create Event" : "Edit Event");
		modal.getDelete().setVisible(!isNew);

		return modal;
	}

	private CalendarModalListener makeEditListener(CalendarViewContext ctx)
	{
		CallbackDelegatingModalListener listener = new CallbackDelegatingModalListener();
		listener.setApply(factory.makeEditCallback(ctx));
		listener.setDelete(factory.makeDeleteCallback(ctx));
		return listener;
	}

	private CalendarModalListener makeCreateListener(CalendarViewContext ctx)
	{
		CallbackDelegatingModalListener listener = new CallbackDelegatingModalListener();
		listener.setApply(factory.makeCreateCallback(ctx));
		return listener;
	}
}
