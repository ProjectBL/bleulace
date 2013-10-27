package com.bleulace.web.demo.timebox;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.model.PersistentEvent;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Component
@Scope("ui")
public class TimeBox extends Window
{
	@Autowired
	private TimeBoxPresenter presenter;

	@Autowired
	@Qualifier("timeBoxFieldGroup")
	private BeanFieldGroup<PersistentEvent> fieldGroup;

	@Autowired
	private BeanContainer<String, ParticipantBean> participants;

	@Autowired
	private BeanContainer<String, ParticipantBean> candidates;

	private final Button deleteButton = makeDeleteButton();

	public void show(PersistentEvent event)
	{
		presenter.setCurrentEvent(event);
		deleteButton.setVisible(!event.isNew());
		UI.getCurrent().addWindow(this);
		focus();
	}

	private TimeBox()
	{
		setModal(true);
		setCaption("Timebox");
	}

	@PostConstruct
	protected void init()
	{
		TextField captionField = fieldGroup.buildAndBind("What", "title",
				TextField.class);
		TextField locationField = fieldGroup.buildAndBind("Where", "location",
				TextField.class);

		DateField startField = makeDateField("Start", "start", fieldGroup);
		DateField endField = makeDateField("End", "end", fieldGroup);

		final ComboBox comboBox = makeComboBox();
		Table table = makeTable();

		FormLayout form = new FormLayout(captionField, locationField,
				startField, endField, comboBox, table);

		Button applyButton = makeApplyButton();
		Button cancelButton = makeCancelButton();

		HorizontalLayout buttons = new HorizontalLayout(applyButton,
				cancelButton, deleteButton);

		VerticalLayout content = new VerticalLayout(form, buttons);
		setContent(content);

		addCloseListener(new Window.CloseListener()
		{
			@Override
			public void windowClose(CloseEvent e)
			{
				comboBox.setValue(null);
				presenter.timeBoxClosed();
			}
		});
	}

	public void showSuccessMessage(String text)
	{
		Notification.show(text, Type.TRAY_NOTIFICATION);
	}

	public void showWarningMessage(String text)
	{
		Notification.show(text);
	}

	private DateField makeDateField(String caption, String propertyId,
			BeanFieldGroup<PersistentEvent> fieldGroup)
	{
		DateField field = fieldGroup.buildAndBind(caption, propertyId,
				DateField.class);
		field.setResolution(Resolution.MINUTE);
		return field;
	}

	private ComboBox makeComboBox()
	{
		final ComboBox field = new ComboBox();
		field.setBuffered(false);
		field.setImmediate(true);
		field.setItemCaptionPropertyId("name");
		field.setContainerDataSource(candidates);
		field.addValueChangeListener(new ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				BeanItem<ParticipantBean> item = candidates.getItem(field
						.getValue());
				if (item != null)
				{
					presenter.participantAdded(item.getBean());
				}
			}
		});
		return field;
	}

	private Table makeTable()
	{
		Table table = new Table();
		table.setContainerDataSource(participants);
		return table;
	}

	private Button makeApplyButton()
	{
		Button button = new Button("Apply");
		button.setClickShortcut(KeyCode.ENTER);
		button.addClickListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				presenter.applyClicked();
			}
		});
		return button;
	}

	private Button makeCancelButton()
	{
		Button button = new Button("Cancel");
		button.setClickShortcut(KeyCode.ESCAPE);
		button.addClickListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				presenter.cancelClicked();
			}
		});
		return button;
	}

	private Button makeDeleteButton()
	{
		Button button = new Button("Delete");
		button.setClickShortcut(KeyCode.DELETE);
		button.addClickListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				presenter.deleteClicked();
			}
		});
		return button;
	}
}