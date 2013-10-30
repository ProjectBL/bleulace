package com.bleulace.web.demo.timebox;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.web.demo.manager.ManagerBox;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Component
@Scope("ui")
public class TimeBox extends Window
{
	@Autowired
	private ManagerBox managerBox;

	@Autowired
	private TimeBoxPresenter presenter;

	@Autowired
	@Qualifier("timeBoxFieldGroup")
	private BeanFieldGroup<PersistentEvent> fieldGroup;

	@Autowired
	@Qualifier("eventParticipants")
	private BeanContainer<String, ParticipantBean> eventParticipants;

	@Autowired
	@Qualifier("eventCandidates")
	private BeanContainer<String, ParticipantBean> eventCandidates;

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
		addCloseListener(new Window.CloseListener()
		{
			@Override
			public void windowClose(CloseEvent e)
			{
				presenter.timeBoxClosed();
			}
		});
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

		HorizontalLayout buttons = new HorizontalLayout(//
				new Button("Managers", new Button.ClickListener()
				{
					@Override
					public void buttonClick(ClickEvent event)
					{
						managerBox.show(presenter.getCurrentEvent());
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
		Table table = new Table("Participants", eventParticipants);
		table.setPageLength(6);
		table.setVisibleColumns(new Object[] { "firstName", "lastName",
				"email", "status" });
		table.setColumnHeader("firstName", "First Name");
		table.setColumnHeader("lastName", "Last Name");
		table.setColumnHeader("email", "Email");
		table.setColumnHeader("status", "RSVP");
		table.addActionHandler(new Handler()
		{
			private final Action[] actions = new Action[] { new Action("Remove") };

			@Override
			public void handleAction(Action action, Object sender, Object target)
			{
				Assert.notNull(target);
				String id = (String) target;
				presenter.participantRemoved(eventParticipants.getItem(id)
						.getBean());
			}

			@Override
			public Action[] getActions(Object target, Object sender)
			{
				return (target == null || target.equals(SecurityUtils
						.getSubject().getPrincipal())) ? null : actions;
			}
		});

		table.setCellStyleGenerator(new CellStyleGenerator()
		{
			@Override
			public String getStyle(Table source, Object itemId,
					Object propertyId)
			{
				return eventParticipants.getItem(itemId).getBean().getStatus()
						.toString().toLowerCase();
			}
		});
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