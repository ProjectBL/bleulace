package com.bleulace.domain.crm.ui.profile;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.vaadin.navigator.Navigator.EmptyView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;

@Configuration
class ProfileViewConfig
{
	private static final String STATUS_UPDATE_PROMPT = "What's happening?";

	@Scope("prototype")
	@Bean(name = "actionTabSheet")
	public TabSheet actionTabSheet(
			@Qualifier("statusUpdateField") TextField statusUpdateField,
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
	public TextField statusUpdateField()
	{
		TextField tf = new TextField();
		tf.setImmediate(true);
		tf.setInputPrompt(STATUS_UPDATE_PROMPT);
		tf.setMaxLength(50);
		return tf;
	}
}