package com.bleulace.web.demo.timebox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

import com.bleulace.web.demo.calendar.CalendarEventAdapter;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseListener;

import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;
import de.steinwedel.messagebox.MessageBoxListener;

@Configurable(preConstruction = true)
class TimeBox extends Window implements CloseListener, MessageBoxListener
{
	@Autowired
	private ApplicationContext ctx;

	private final Button deleteButton = makeDeleteButton();

	private final TimeBoxPresenter presenter;

	TimeBox(final TimeBoxPresenter presenter)
	{
		this.presenter = presenter;

		final BeanFieldGroup<CalendarEventAdapter> fg = presenter
				.getFieldGroup();

		TextField captionField = fg.buildAndBind("What", "caption",
				TextField.class);
		TextField locationField = fg.buildAndBind("Where", "description",
				TextField.class);

		DateField startField = makeDateField("Start", "start", fg);
		DateField endField = makeDateField("End", "end", fg);

		ParticipantField participantField = new ParticipantField();
		presenter.getFieldGroup().bind(participantField, "invitees");

		FormLayout form = new FormLayout(captionField, locationField,
				startField, endField, participantField);

		ManagerField managerField = new ManagerField();
		presenter.getFieldGroup().bind(managerField, "assignments");

		Button applyButton = makeApplyButton();
		Button cancelButton = makeCancelButton();

		HorizontalLayout buttons = new HorizontalLayout(managerField,
				cancelButton, applyButton, deleteButton);
		buttons.setSpacing(false);

		VerticalLayout content = new VerticalLayout(form, buttons);
		content.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
		setContent(content);

		deleteButton.setVisible(!presenter.getCurrentEvent().getSource()
				.isNew());
	}

	@Override
	public void windowClose(CloseEvent e)
	{
		presenter.timeBoxClosed();
	}

	@Override
	public void buttonClicked(ButtonId buttonId)
	{
		presenter.warningAccepted(buttonId.equals(ButtonId.OK));
	}

	void showSuccessMessage(String text)
	{
		Notification.show(text, Type.TRAY_NOTIFICATION);
	}

	void showWarningMessage(String text)
	{
		Notification.show(text, Type.WARNING_MESSAGE);
	}

	void showWarningDialog(String text)
	{
		MessageBox
				.showPlain(Icon.WARN, "Warning", text, this, ButtonId.CANCEL,
						ButtonId.OK).setAutoClose(true).setModal(true);
	}

	private DateField makeDateField(String caption, String propertyId,
			BeanFieldGroup<CalendarEventAdapter> fieldGroup)
	{
		DateField field = presenter.getFieldGroup().buildAndBind(caption,
				propertyId, DateField.class);
		field.setResolution(Resolution.MINUTE);
		return field;
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

	private Button makeButton(String caption, int keyCode,
			ClickListener listener)
	{
		Button button = new Button(caption, listener);
		button.setClickShortcut(keyCode);
		return button;
	}
}