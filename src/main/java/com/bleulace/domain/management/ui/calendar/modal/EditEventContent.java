package com.bleulace.domain.management.ui.calendar.modal;

import com.bleulace.domain.management.ui.calendar.modal.CalendarModal.ModelCallback;
import com.bleulace.domain.management.ui.calendar.model.EventModel;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;

class EditEventContent extends CustomComponent implements ModelCallback
{
	private final BeanFieldGroup<EventModel> group;

	public EditEventContent(EventModel model)
	{
		group = new BeanFieldGroup<EventModel>(EventModel.class);
		group.setItemDataSource(model);
		group.setBuffered(false);

		FormLayout layout = new FormLayout();
		layout.addComponent(group.buildAndBind("caption"));
		layout.addComponent(group.buildAndBind("description"));

		DateField startField = new DateField(group.getItemDataSource()
				.getItemProperty("start"));
		startField.setResolution(Resolution.MINUTE);
		layout.addComponent(startField);

		DateField endField = new DateField(group.getItemDataSource()
				.getItemProperty("end"));
		endField.setResolution(Resolution.MINUTE);
		layout.addComponent(endField);

		setCompositionRoot(layout);
	}

	FieldGroup getFieldGroup()
	{
		return group;
	}

	@Override
	public EventModel getModel()
	{
		return group.getItemDataSource().getBean();
	}
}