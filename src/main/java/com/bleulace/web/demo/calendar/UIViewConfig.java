package com.bleulace.web.demo.calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.bleulace.utils.SystemProfiles;
import com.bleulace.web.BleulaceTheme.AvatarGender;
import com.bleulace.web.BleulaceTheme.AvatarSize;
import com.bleulace.web.demo.avatar.AvatarFactory;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Configuration
@Profile({ SystemProfiles.DEV, SystemProfiles.PROD })
public class UIViewConfig
{
	@Autowired
	private AvatarFactory avatarFactory;

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public VerticalLayout leftLayout(DateField dateField, Calendar calendar,
			Button homeButton, Button lockButton, TextField statusUpdateField,
			Accordion accordion)
	{
		VerticalLayout bean = new VerticalLayout();

		HorizontalLayout top = new HorizontalLayout(homeButton, lockButton,
				dateField);
		bean.addComponent(top);

		bean.addComponent(statusUpdateField);

		Image avatar = avatarFactory.make(AvatarGender.MALE, AvatarSize.FULL);
		bean.addComponent(avatar);
		bean.addComponent(accordion);
		accordion.setWidth(avatar.getWidth(), avatar.getWidthUnits());

		return bean;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public VerticalLayout centerLayout(TabSheet tabSheet, Calendar calendar)
	{
		VerticalLayout bean = new VerticalLayout(tabSheet, calendar);
		calendar.setWidth(new Float(UI.getCurrent().getPage()
				.getBrowserWindowWidth() / 2), Unit.PIXELS);
		return bean;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public VerticalLayout rightLayout(List<Button> socialButtons,
			ComboBox searchField)
	{
		VerticalLayout bean = new VerticalLayout();

		HorizontalLayout top = new HorizontalLayout(searchField);
		for (Button button : socialButtons)
		{
			top.addComponent(button);
		}
		bean.addComponent(top);

		return bean;
	}
}