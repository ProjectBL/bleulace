package com.bleulace.domain.crm.ui.profile;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

class InfoComponent extends CustomComponent
{
	private final Label school = new Label("");
	private final Label work = new Label("");
	private final Label location = new Label("");

	InfoComponent()
	{
		VerticalLayout layout = new VerticalLayout(school, work, location);
		layout.setWidth("100%");
		layout.setSpacing(false);
		setCompositionRoot(layout);

		setLocation("");
		setWork("");
		setSchool("");
	}

	public void setLocation(String location)
	{
		this.location.setCaption("Location: " + location);
	}

	public void setWork(String work)
	{
		this.work.setCaption("Work: " + location);
	}

	public void setSchool(String school)
	{
		this.school.setCaption("School: " + location);
	}
}