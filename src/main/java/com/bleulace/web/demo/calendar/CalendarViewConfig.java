package com.bleulace.web.demo.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.web.BleulaceTheme.AvatarGender;
import com.bleulace.web.BleulaceTheme.AvatarSize;
import com.bleulace.web.annotation.WebProfile;
import com.bleulace.web.demo.avatar.AvatarFactory;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Configuration
@WebProfile
public class CalendarViewConfig
{
	@Autowired
	private AvatarFactory avatarFactory;

	@Bean
	@Scope("ui")
	public VerticalLayout leftLayout(Calendar calendar, Button homeButton,
			Button lockButton, TextField statusUpdateField, Accordion accordion)
	{
		VerticalLayout bean = new VerticalLayout();

		HorizontalLayout top = new HorizontalLayout(homeButton, lockButton);
		bean.addComponent(top);

		bean.addComponent(statusUpdateField);

		Image avatar = avatarFactory.make(AvatarGender.MALE, AvatarSize.FULL);
		bean.addComponent(avatar);
		bean.addComponent(accordion);
		accordion.setWidth(avatar.getWidth(), avatar.getWidthUnits());

		return bean;
	}

	@Bean
	@Scope("ui")
	public VerticalLayout centerLayout(Button weekButton, Button monthButton,
			Calendar calendar)
	{
		HorizontalLayout top = new HorizontalLayout();
		top.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		top.addComponents(weekButton, monthButton);
		top.setSpacing(true);

		VerticalLayout bean = new VerticalLayout(top, calendar);
		top.setWidth("100%");
		calendar.setSizeFull();
		bean.setSizeFull();

		return bean;
	}
}