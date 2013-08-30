package com.bleulace.domain.crm.ui.profile;

import javax.annotation.PostConstruct;

import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

@UIComponent
class StatusUpdateField extends CustomComponent implements ClickListener
{
	private static final String STATUS_UPDATE_PROMPT = "What's happening?";

	private final TextField tf = makeTextField();
	private final Button button = makeButton();

	@Autowired
	@Qualifier("uiBus")
	private transient EventBus uiBus;

	public StatusUpdateField()
	{
	}

	@PostConstruct
	protected void init()
	{
		HorizontalLayout layout = new HorizontalLayout(tf, button);
		layout.setSpacing(false);
		setCompositionRoot(layout);
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		String value = tf.getValue();
		if (value != null && !value.equals(""))
		{
			uiBus.publish(GenericEventMessage
					.asEventMessage(new StatusUpdatedEvent(tf.getValue())));
		}
	}

	private TextField makeTextField()
	{
		TextField tf = new TextField();
		tf.setImmediate(true);
		tf.setInputPrompt(STATUS_UPDATE_PROMPT);
		tf.setMaxLength(50);
		return tf;
	}

	private Button makeButton()
	{
		Button button = new Button("Post", this);
		return button;
	}
}
