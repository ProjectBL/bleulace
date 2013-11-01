package com.bleulace.web.demo.timebox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.management.model.PersistentEvent;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Configurable(preConstruction = true)
class TimeBox extends Window
{
	@Autowired
	private ManagerBox managerBox;

	private final BeanFieldGroup<PersistentEvent> fieldGroup = makeFieldGroup();

	private final BeanContainer<String, ParticipantBean> eventParticipants = makeContainer();

	private final BeanContainer<String, ParticipantBean> eventCandidates = makeContainer();

	private final Button deleteButton = makeDeleteButton();

	private final TimeBoxPresenter presenter;

	public void setEvent(PersistentEvent event)
	{
		presenter.setCurrentEvent(event);
		deleteButton.setVisible(!event.isNew());
	}

	TimeBox(final PersistentEvent event, final TimeBoxPresenter presenter)
	{
		this.presenter = presenter;
		addCloseListener(new Window.CloseListener()
		{
			@Override
			public void windowClose(CloseEvent e)
			{
				presenter.timeBoxClosed();
			}
		});

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

		HorizontalLayout buttons = new HorizontalLayout(//
				new Button("Managers", new Button.ClickListener()
				{
					@Override
					public void buttonClick(ClickEvent event)
					{
						managerBox.setResource(presenter.getCurrentEvent());
					}
				}),//
				cancelButton, applyButton, deleteButton);
		buttons.setSpacing(false);

		VerticalLayout content = new VerticalLayout(form, buttons);
		content.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
		setContent(content);

		addCloseListener(new Window.CloseListener()
		{
			@Override
			public void windowClose(CloseEvent e)
			{
				comboBox.setValue(null);
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
		final ComboBox bean = new ComboBox("Invite", eventCandidates);
		bean.setBuffered(false);
		bean.setImmediate(true);
		bean.setItemCaptionPropertyId("name");
		bean.addValueChangeListener(new ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				BeanItem<ParticipantBean> item = eventCandidates.getItem(bean
						.getValue());
				if (item != null)
				{
					presenter.participantAdded(item.getBean());
				}
			}
		});
		return bean;
	}

	private Table makeTable()
	{
		final Table table = new Table("Participants", eventParticipants);
		table.setPageLength(6);
		table.setVisibleColumns(new Object[] { "firstName", "lastName",
				"email", "status" });
		table.setColumnHeader("firstName", "First Name");
		table.setColumnHeader("lastName", "Last Name");
		table.setColumnHeader("email", "Email");
		table.setColumnHeader("status", "RSVP");
		table.setImmediate(true);
		table.setSelectable(true);
		table.addShortcutListener(new ShortcutListener("Delete",
				KeyCode.DELETE, null)
		{
			@Override
			public void handleAction(Object sender, Object target)
			{
				String id = (String) table.getValue();
				if (id != null)
				{
					presenter.participantRemoved(eventParticipants.getItem(id)
							.getBean());
				}
			}
		});
		table.setCellStyleGenerator(new CellStyleGenerator()
		{
			@Override
			public String getStyle(Table source, Object itemId,
					Object propertyId)
			{
				return eventParticipants.getItem(itemId).getBean().getStatus()
						.getStyleName();
			}
		});
		return table;
	}

	private Button makeApplyButton()
	{
		return makeButton("Apply", KeyCode.ENTER, new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				presenter.applyClicked();
			}
		});
	}

	private Button makeCancelButton()
	{
		return makeButton("Cancel", KeyCode.ESCAPE, new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				presenter.cancelClicked();
			}
		});
	}

	private Button makeDeleteButton()
	{
		return makeButton("Delete", KeyCode.DELETE, new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				presenter.deleteClicked();
			}
		});
	}

	BeanContainer<String, ParticipantBean> getEventParticipants()
	{
		return eventParticipants;
	}

	BeanContainer<String, ParticipantBean> getEventCandidates()
	{
		return eventCandidates;
	}

	private BeanContainer<String, ParticipantBean> makeContainer()
	{
		BeanContainer<String, ParticipantBean> container = new BeanContainer<String, ParticipantBean>(
				ParticipantBean.class);
		container.setBeanIdProperty("id");
		return container;
	}

	private BeanFieldGroup<PersistentEvent> makeFieldGroup()
	{
		BeanFieldGroup<PersistentEvent> bean = new BeanFieldGroup<PersistentEvent>(
				PersistentEvent.class);
		bean.addCommitHandler(presenter);
		return bean;
	}

	private Button makeButton(String caption, int keyCode,
			ClickListener listener)
	{
		Button button = new Button(caption, listener);
		button.setClickShortcut(keyCode);
		return button;
	}
}