package com.bleulace.web.demo.timebox;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;

public class TimeBox extends Window
{
	private final BeanFieldGroup<EventBean> fieldGroup = new BeanFieldGroup<EventBean>(
			EventBean.class);

	public TimeBox(final EventBean model, final BasicEventProvider provider)
	{
		setModal(true);
		setCaption("Timebox");

		fieldGroup.setItemDataSource(model);

		FormLayout form = new FormLayout();

		form.addComponent(fieldGroup.buildAndBind("What", "caption"));
		form.addComponent(fieldGroup.buildAndBind("Where", "description"));

		DateField start = new DateField();
		start.setCaption("Start");
		start.setResolution(Resolution.MINUTE);
		start.setImmediate(true);
		start.setBuffered(false);
		fieldGroup.bind(start, "start");
		form.addComponent(start);

		DateField end = new DateField();
		end.setCaption("End");
		end.setResolution(Resolution.MINUTE);
		end.setImmediate(true);
		end.setBuffered(false);
		fieldGroup.bind(end, "end");
		form.addComponent(end);

		TwinColSelect select = new TwinColSelect("Who");
		select.setBuffered(false);
		select.setContainerDataSource(makePersonContainer());
		fieldGroup.bind(select, "attendees");
		form.addComponent(select);

		Button cancel = new Button("Cancel");
		cancel.setClickShortcut(KeyCode.ESCAPE);
		cancel.addClickListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				Notification.show("Operation aborted.", Type.TRAY_NOTIFICATION);
				close();
			}
		});

		Button apply = new Button("Apply");
		apply.setClickShortcut(KeyCode.ENTER);
		apply.addClickListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				try
				{
					fieldGroup.commit();
					if (!provider.containsEvent(model))
					{
						provider.addEvent(model);
					}
					Notification.show("Timebox saved.", Type.TRAY_NOTIFICATION);
					close();
				}
				catch (CommitException e)
				{
					Notification.show("Invalid timebox state");
				}
			}
		});

		HorizontalLayout buttons = new HorizontalLayout(cancel, apply);
		buttons.setSpacing(false);

		if (provider.containsEvent(model))
		{
			buttons.addComponent(new Button("Delete",
					new Button.ClickListener()
					{
						@Override
						public void buttonClick(ClickEvent event)
						{
							provider.removeEvent(model);
							close();
						}
					}));
		}

		VerticalLayout layout = new VerticalLayout(form, buttons);
		layout.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);

		setContent(layout);
	}

	private BeanItemContainer<PersonBean> makePersonContainer()
	{
		BeanItemContainer<PersonBean> container = new BeanItemContainer<PersonBean>(
				PersonBean.class);
		container.addBean(new PersonBean("Jane", "Doe"));
		container.addBean(new PersonBean("John", "Doe"));
		container.addBean(new PersonBean("Jane", "Doe"));
		container.addBean(new PersonBean("John", "Doe"));
		container.addBean(new PersonBean("Jane", "Doe"));
		container.addBean(new PersonBean("John", "Doe"));
		return container;
	}
}