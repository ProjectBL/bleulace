package com.bleulace.web.demo.timebox;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.resource.model.AbstractResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Scope("ui")
@Component("managerBoxWindow")
public class ManagerBox extends Window
{
	@Autowired
	private ManagerBoxPresenter presenter;

	@Autowired
	@Qualifier("managerTable")
	private Table table;

	@Autowired
	@Qualifier("managerComboBox")
	private ComboBox comboBox;

	@Autowired
	@Qualifier("managerCancelButton")
	private Button cancel;

	@Autowired
	@Qualifier("managerSubmitButton")
	private Button submit;

	ManagerBox()
	{
		setModal(true);
		setCaption("Managers");
	}

	void setResource(AbstractResource resource)
	{
		presenter.setCurrentResource(resource);
	}

	@PostConstruct
	protected void init()
	{
		FormLayout form = new FormLayout(comboBox, table);

		HorizontalLayout buttons = new HorizontalLayout(cancel, submit);
		buttons.setSpacing(false);

		VerticalLayout content = new VerticalLayout(form, buttons);
		content.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
		setContent(content);
	}
}
