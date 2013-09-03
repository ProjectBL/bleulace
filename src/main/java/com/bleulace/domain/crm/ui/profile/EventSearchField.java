package com.bleulace.domain.crm.ui.profile;

import java.util.List;

import javax.annotation.PostConstruct;

import org.axonframework.domain.GenericDomainEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;

@UIComponent
class EventSearchField extends CustomComponent implements ValueChangeListener
{
	@Autowired
	@Qualifier("uiBus")
	private EventBus uiBus;

	@Autowired
	@Qualifier("profileCalendar")
	private Calendar calendar;

	private final BeanContainer<String, EventDTO> container = makeContainer();

	private final ComboBox comboBox = makeComboBox();

	private EventSearchField()
	{
	}

	@PostConstruct
	protected void init()
	{
		setCompositionRoot(comboBox);
	}

	@Override
	public void valueChange(ValueChangeEvent event)
	{
		String id = (String) event.getProperty().getValue();
		if (id != null)
		{
			uiBus.publish(GenericDomainEventMessage
					.asEventMessage(new EventClick(calendar, container.getItem(
							id).getBean())));
		}
	}

	@Override
	public void markAsDirty()
	{
		if (container != null)
		{
			container.removeAllItems();
			LocalDateTime now = LocalDateTime.now();
			container.addAll((List) calendar.getEvents(now.toDate(), now
					.plusYears(10).toDate()));
		}
		super.markAsDirty();
	}

	private BeanContainer<String, EventDTO> makeContainer()
	{
		BeanContainer<String, EventDTO> container = new BeanContainer<String, EventDTO>(
				EventDTO.class);
		container.setBeanIdProperty("id");
		return container;
	}

	private ComboBox makeComboBox()
	{
		ComboBox comboBox = new ComboBox(null, container);
		comboBox.setContainerDataSource(container);
		comboBox.setBuffered(false);
		comboBox.setImmediate(true);
		comboBox.addValueChangeListener(this);
		comboBox.setItemCaptionPropertyId("caption");
		return comboBox;
	}
}