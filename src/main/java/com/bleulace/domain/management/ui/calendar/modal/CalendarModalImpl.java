package com.bleulace.domain.management.ui.calendar.modal;

import java.util.LinkedList;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.domain.management.ui.calendar.model.EventModel;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@RooJavaBean(settersByDefault = false)
class CalendarModalImpl extends Window implements CalendarModal
{
	private final List<CalendarModalListener> listeners = new LinkedList<CalendarModalListener>();

	private ModelCallback callback;

	private final Button apply = new Button("Apply", new ApplyListener());
	private final Button delete = new Button("Delete", new DeleteListener());
	private final Button cancel = new Button("Cancel", new CancelListener());

	CalendarModalImpl(Component content)
	{
		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout buttons = new HorizontalLayout(apply, delete, cancel);

		layout.addComponent(content);
		layout.setComponentAlignment(content, Alignment.MIDDLE_LEFT);

		layout.addComponent(buttons);
		layout.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);

		setContent(layout);
	}

	@Override
	public void setModelCallback(ModelCallback callback)
	{
		this.callback = callback;
	}

	@Override
	public void addModalListener(CalendarModalListener listener)
	{
		listeners.add(listener);
	}

	private class ApplyListener implements ClickListener
	{
		@Override
		public void buttonClick(ClickEvent event)
		{
			try
			{
				Assert.notNull(callback);
				EventModel model = callback.getModel();
				for (CalendarModalListener l : listeners)
				{
					l.apply(model);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private class CancelListener implements ClickListener
	{
		@Override
		public void buttonClick(ClickEvent event)
		{
			Assert.notNull(callback);
			EventModel model = callback.getModel();
			for (CalendarModalListener l : listeners)
			{
				l.cancel(model);
			}
		}
	}

	private class DeleteListener implements ClickListener
	{
		@Override
		public void buttonClick(ClickEvent event)
		{
			Assert.notNull(callback);
			EventModel model = callback.getModel();
			for (CalendarModalListener l : listeners)
			{
				l.delete(model);
			}
		}
	}
}