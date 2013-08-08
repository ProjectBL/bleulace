package com.bleulace.ui.web.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.crm.presentation.AccountDTO;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.Reindeer;

@Configurable(preConstruction = true)
class ProfileHeadingComponent extends CustomComponent implements ClickListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7333744848084785712L;

	@Autowired
	private Image profileImage;

	ProfileHeadingComponent(AccountDTO dto)
	{
		profileImage.addClickListener(this);
		Label label = new Label();
		label.setCaption(dto.getCaption());
		label.setStyleName(Reindeer.LABEL_H1);
		setCompositionRoot(new HorizontalLayout(profileImage, label));
	}

	@Override
	public void click(ClickEvent event)
	{
		Notification.show("IMAGE CLICKED");
	}
}