package com.bleulace.web.demo.timebox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import com.bleulace.web.demo.calendar.CalendarEventAdapter;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

@Configurable(preConstruction = true)
class TimeBox extends CustomComponent
{
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	@Qualifier("resourceComboBox")
	private CustomField comboBox;

	private final TimeBoxPresenter presenter;

	private final ManagerField managerField = new ManagerField();

	TimeBox(final TimeBoxPresenter presenter)
	{
		this.presenter = presenter;

		final BeanFieldGroup<CalendarEventAdapter> fg = presenter
				.getFieldGroup();
		fg.getItemDataSource().addNestedProperty("source.parent");

		fg.bind(comboBox, "source.parent");

		TextField captionField = fg.buildAndBind("What", "caption",
				TextField.class);
		TextField locationField = fg.buildAndBind("Where", "description",
				TextField.class);

		DateField startField = makeDateField("Start", "start", fg);
		DateField endField = makeDateField("End", "end", fg);

		ParticipantField participantField = new ParticipantField();
		presenter.getFieldGroup().bind(participantField, "invitees");

		FormLayout form = new FormLayout(comboBox, captionField, locationField,
				startField, endField, participantField);

		presenter.getFieldGroup().bind(managerField, "assignments");

		setCompositionRoot(form);
	}

	public ManagerField getManagerField()
	{
		return managerField;
	}

	private DateField makeDateField(String caption, String propertyId,
			BeanFieldGroup<CalendarEventAdapter> fieldGroup)
	{
		DateField field = presenter.getFieldGroup().buildAndBind(caption,
				propertyId, DateField.class);
		field.setResolution(Resolution.MINUTE);
		return field;
	}
}