package com.bleulace.web.demo.timebox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.PersistentEvent;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanContainer;
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
	private ApplicationContext ctx;

	@Autowired
	@Qualifier("friendContainer")
	private JPAContainer<Account> candidates;

	private final BeanContainer<String, ParticipantBean> participants = makeContainer();

	private final Button deleteButton = makeDeleteButton();

	private final TimeBoxPresenter presenter;

	TimeBox(final TimeBoxPresenter presenter)
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

		final BeanFieldGroup<PersistentEvent> fg = presenter.getFieldGroup();

		TextField captionField = fg.buildAndBind("What", "title",
				TextField.class);
		TextField locationField = fg.buildAndBind("Where", "location",
				TextField.class);

		DateField startField = makeDateField("Start", "start", fg);
		DateField endField = makeDateField("End", "end", fg);

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
						presenter.managersClicked();
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

		deleteButton.setVisible(!presenter.getCurrentEvent().isNew());
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
		DateField field = presenter.getFieldGroup().buildAndBind(caption,
				propertyId, DateField.class);
		field.setResolution(Resolution.MINUTE);
		return field;
	}

	private ComboBox makeComboBox()
	{
		final ComboBox bean = new ComboBox("Invite", candidates);
		bean.setBuffered(false);
		bean.setImmediate(true);
		bean.setItemCaptionPropertyId("title");
		bean.addValueChangeListener(new ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				String id = (String) event.getProperty().getValue();
				if (id != null)
				{
					presenter.participantAdded(new ParticipantBean(candidates
							.getItem(id).getEntity()));
				}
			}
		});
		return bean;
	}

	private Table makeTable()
	{
		final Table table = new Table("Participants", participants);
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
					presenter.participantRemoved(participants.getItem(id)
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
				return participants.getItem(itemId).getBean().getStatus()
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

	BeanContainer<String, ParticipantBean> getParticipants()
	{
		return participants;
	}

	JPAContainer<Account> getCandidates()
	{
		return candidates;
	}

	private BeanContainer<String, ParticipantBean> makeContainer()
	{
		BeanContainer<String, ParticipantBean> container = new BeanContainer<String, ParticipantBean>(
				ParticipantBean.class);
		container.setBeanIdProperty("id");
		return container;
	}

	private Button makeButton(String caption, int keyCode,
			ClickListener listener)
	{
		Button button = new Button(caption, listener);
		button.setClickShortcut(keyCode);
		return button;
	}
}