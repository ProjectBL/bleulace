package com.bleulace.web.demo.timebox;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.demo.calendar.CalendarEventAdapter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Calendar;

import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;

@Lazy
@Component("timeBox")
@Scope("prototype")
class TimeBoxFactoryBean implements FactoryBean<MessageBox>
{
	private final CalendarEventAdapter event;
	private final Calendar calendar;

	TimeBoxFactoryBean(Calendar calendar, CalendarEventAdapter event)
	{
		this.calendar = calendar;
		this.event = event;
	}

	TimeBoxFactoryBean()
	{
		this(SpringApplicationContext.getBean(Calendar.class),
				(CalendarEventAdapter) SpringApplicationContext.getBean(
						"calendarAdapter", new PersistentEvent()));
	}

	@Override
	public MessageBox getObject() throws Exception
	{
		final TimeBoxPresenter presenter = new TimeBoxPresenter(event, calendar);
		TimeBox timeBox = new TimeBox(presenter);
		MessageBox bean = MessageBox.showCustomized(Icon.NONE, "TimeBox",
				timeBox, ButtonId.CUSTOM_1, ButtonId.CUSTOM_2, ButtonId.CANCEL,
				ButtonId.SAVE).setAutoClose(false);
		bean.getWindow().setDraggable(false);
		presenter.setView(bean);
		Button managers = bean.getButton(ButtonId.CUSTOM_1);
		managers.setCaption("Managers");
		managers.addClickListener(timeBox.getManagerField());
		Button delete = bean.getButton(ButtonId.CUSTOM_2);
		delete.setCaption("Delete");
		delete.setVisible(!event.getSource().isNew());
		delete.addClickListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				presenter.deleteClicked();
			}
		});

		Button cancel = bean.getButton(ButtonId.CANCEL);
		cancel.addClickListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				presenter.cancelClicked();
			}
		});

		Button save = bean.getButton(ButtonId.SAVE);
		save.addClickListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				presenter.applyClicked();
			}
		});
		return bean;
	}

	@Override
	public Class<?> getObjectType()
	{
		return MessageBox.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}
}
