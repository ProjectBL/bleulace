package com.bleulace.ui.web.profile.status;

import org.springframework.util.Assert;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

public class StatusUpdateField extends CustomComponent implements
		FocusListener, BlurListener, ClickListener, CommandGatewayAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4220719642719293267L;

	static final String DEFAULT_FIELD_VALUE = "What's Happening?";
	static final String BLANK_FIELD_VALUE = "";

	@SuppressWarnings("unused")
	private final String accountId;

	private final TextField field;

	public StatusUpdateField(String accountId)
	{
		Assert.notNull(accountId);
		this.accountId = accountId;

		field = new TextField();
		field.setBuffered(false);
		field.addFocusListener(this);
		field.addBlurListener(this);

		Button button = new Button("submit", this);
		button.setClickShortcut(KeyCode.ENTER);

		setFieldState(StatusUpdateFieldState.DEFAULT);
		setCompositionRoot(new HorizontalLayout(field, button));
	}

	String getValue()
	{
		return field.getValue();
	}

	@Override
	public void blur(BlurEvent event)
	{
		getFieldState().onBlur(this);
	}

	@Override
	public void focus(FocusEvent event)
	{
		getFieldState().onFocus(this);
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		if (getFieldState().equals(StatusUpdateFieldState.OCCUPIED))
		{
			doStatusUpdate();
			setFieldState(StatusUpdateFieldState.DEFAULT);
		}
	}

	void setFieldState(StatusUpdateFieldState state)
	{
		if (!state.equals(StatusUpdateFieldState.OCCUPIED))
		{
			field.setValue(state.getValue());
		}
	}

	StatusUpdateFieldState getFieldState()
	{
		return StatusUpdateFieldState.calculateState(this);
	}

	private void doStatusUpdate()
	{
		Notification.show("NOT YET IMPLEMENTED");
	}
}