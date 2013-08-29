package com.bleulace.domain.crm.ui.profile;

import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.vaadin.navigator.Navigator.EmptyView;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;

@Configuration
class ProfileViewConfig
{
	private static final String STATUS_UPDATE_PROMPT = "What's happening?";

	@Scope("prototype")
	@Bean(name = "actionTabSheet")
	public TabSheet actionTabSheet(
			@Qualifier("statusUpdateField") Component statusUpdateField,
			ImageDropField imageField)
	{
		TabSheet ts = new TabSheet();
		ts.addTab(statusUpdateField, "Status");
		ts.addTab(imageField, "Image");
		ts.addTab(new EmptyView(), "Playlist");
		ts.addTab(new EmptyView(), "Location");
		ts.addTab(new EmptyView(), "Event");
		ts.addTab(new EmptyView(), "Pin");
		ts.addTab(new EmptyView(), "Blog");
		return ts;
	}

	@Scope("prototype")
	@Bean(name = "statusUpdateField")
	public Component statusUpdateField(@Qualifier("uiBus") final EventBus uiBus)
	{
		final TextField tf = new TextField();
		tf.setImmediate(true);
		tf.setInputPrompt(STATUS_UPDATE_PROMPT);
		tf.setMaxLength(50);

		Button submit = new Button("Post", new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				String value = tf.getValue();
				if (value != null && value.equals(""))
				{
					uiBus.publish(GenericEventMessage
							.asEventMessage(new StatusUpdatedEvent(tf
									.getValue())));
				}
			}
		});
		HorizontalLayout layout = new HorizontalLayout(tf, submit);
		layout.setSpacing(false);
		return layout;
	}

	/**
	 * this is not sexist at all
	 * 
	 * @todo default avatar for female users
	 */
	@Bean
	@Scope("prototype")
	public Image defaultAvatar()
	{
		Image image = new Image();
		image.setSource(new ThemeResource("img/ProfilePlaceholder.png"));
		return image;
	}
}