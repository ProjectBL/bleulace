package com.bleulace.ui.web.calendar;

import com.bleulace.domain.calendar.CalendarEntry;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;

class CalendarEntryEditor extends AbstractCalendarEntryDetail
{
	private static final long serialVersionUID = -4206862826575716813L;

	private BeanFieldGroup<CalendarEntry> fieldGroup;

	private DateField start;
	private DateField end;
	private CheckBox allDay;

	public CalendarEntryEditor()
	{
	}

	@Override
	public void showEntry(CalendarEntry entry)
	{
		fieldGroup = new BeanFieldGroup<CalendarEntry>(CalendarEntry.class);
		fieldGroup.setItemDataSource(entry);
		fieldGroup.addCommitHandler(new CalendarEntryCommitHandler());
		AccountField field = new AccountField("Participants");
		fieldGroup.bind(field, "accounts");

		start = fieldGroup.buildAndBind("Start", "start", DateField.class);
		start.setResolution(Resolution.MINUTE);

		end = fieldGroup.buildAndBind("End", "end", DateField.class);
		end.setResolution(Resolution.MINUTE);

		allDay = fieldGroup.buildAndBind("All Day", "allDay", CheckBox.class);
		allDay.addValueChangeListener(new AllDayListener());

		//@formatter:off
		FormLayout layout = new FormLayout(
				fieldGroup.buildAndBind("caption"),
				fieldGroup.buildAndBind("description"),
				fieldGroup.buildAndBind("Location","location",TextArea.class),
				allDay,
				start,
				end,
				field,
				new HorizontalLayout(
						new Button("Submit",new SubmitListener()),
						new Button("Cancel",new CancelListener())));
		layout.setSpacing(true);
		//@formatter:on
		setCompositionRoot(layout);
		updateDateFieldVisibility();
	}

	private void setDateFieldsEnabled(boolean enabled)
	{
		start.setEnabled(enabled);
		end.setEnabled(enabled);
	}

	private void updateDateFieldVisibility()
	{
		setDateFieldsEnabled(!allDay.getValue());
	}

	class CalendarEntryCommitHandler implements CommitHandler
	{
		private static final long serialVersionUID = 2206783145288896322L;

		@Override
		public void preCommit(CommitEvent commitEvent) throws CommitException
		{
		}

		@Override
		public void postCommit(CommitEvent commitEvent) throws CommitException
		{
			for (CalendarEntryDetailListener listener : listeners())
			{
				listener.onSaveButtonClicked(fieldGroup.getItemDataSource()
						.getBean());
			}
		}

	}

	class SubmitListener implements ClickListener
	{
		private static final long serialVersionUID = 4907610689486337372L;

		@Override
		public void buttonClick(ClickEvent event)
		{
			try
			{
				fieldGroup.commit();
			}
			catch (CommitException e)
			{
				e.printStackTrace();
			}
		}
	}

	class CancelListener implements ClickListener
	{
		private static final long serialVersionUID = -1174421599961694080L;

		@Override
		public void buttonClick(ClickEvent event)
		{
			for (CalendarEntryDetailListener listener : listeners())
			{
				listener.onCancelButtonClicked();
			}
		}
	}

	class AllDayListener implements ValueChangeListener
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void valueChange(ValueChangeEvent event)
		{
			updateDateFieldVisibility();
		}
	}
}