package com.bleulace.web.demo.calendar;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Component
@Scope("ui")
class VieweeWindow extends Window
{
	@Autowired
	@Qualifier("vieweeTable")
	private Table table;

	@Autowired
	@Qualifier("vieweeComboBox")
	private ComboBox comboBox;

	@PostConstruct
	protected void init()
	{
		FormLayout form = new FormLayout(comboBox, table);
		HorizontalLayout buttons = new HorizontalLayout(
		// submit);
		);
		VerticalLayout content = new VerticalLayout(form, buttons);
		content.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
		setContent(content);
	}
}