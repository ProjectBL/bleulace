package com.bleulace.domain.management.ui.calendar.view;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.ui.calendar.view.CalendarView.EventDTOCommandCallback;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;

class CalendarModal extends Window implements ClickListener,
		CommandGatewayAware
{
	private final BeanFieldGroup<EventDTO> group = new BeanFieldGroup<EventDTO>(
			EventDTO.class);

	private final Button apply = new Button("Apply", this);

	private final EventDTOCommandCallback<?> callback;

	private final CalendarView view;

	CalendarModal(CalendarView view, EventDTO dto,
			EventDTOCommandCallback<?> callback)
	{
		this.callback = callback;
		this.view = view;
		setModal(true);
		setDraggable(false);

		group.setItemDataSource(dto);
		group.setBuffered(false);

		FormLayout layout = new FormLayout();

		layout.addComponent(group.buildAndBind("caption"));
		layout.addComponent(group.buildAndBind("description"));

		DateField start = new DateField();
		start.setResolution(Resolution.MINUTE);
		group.bind(start, "start");
		layout.addComponent(start);

		DateField end = new DateField();
		end.setResolution(Resolution.MINUTE);
		group.bind(end, "end");
		layout.addComponent(end);

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(apply);
		layout.addComponent(buttons);

		setContent(layout);
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		if (group.isValid())
		{
			sendAndWait(callback
					.getCommand(group.getItemDataSource().getBean()));
			view.refreshCalendar();
			close();
		}
	}
}
