package com.bleulace.web.demo.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.web.BleulaceTheme.AvatarGender;
import com.bleulace.web.BleulaceTheme.AvatarSize;
import com.bleulace.web.annotation.WebProfile;
import com.bleulace.web.demo.avatar.AvatarFactory;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
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
@WebProfile
public class CalendarViewConfig
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
	public VerticalLayout centerLayout(ComboBox searchField,
			@Qualifier("tabSheet") TabSheet tabSheet, Calendar calendar,
			@Qualifier("socialButtons") Iterable<Button> socialButtons,
			@Qualifier("logoutButton") final Button logoutButton)
	{
		// Initialize master and set width
		VerticalLayout bean = new VerticalLayout();
		bean.setWidth(new Float(UI.getCurrent().getPage()
				.getBrowserWindowWidth() / 2), Unit.PIXELS);

		HorizontalLayout top = new HorizontalLayout(); // Initialize top bar
		top.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		top.setSpacing(false);
		top.setWidth("100%");

		top.addComponent(tabSheet); // add calendar tabs to top
		top.setExpandRatio(tabSheet, 1);

		top.addComponent(searchField); // add search field to top

		// grouping social buttons together
		HorizontalLayout socialBar = new HorizontalLayout();
		for (Button b : socialButtons)
		{
			socialBar.addComponent(b);
		}
		socialBar.addComponent(logoutButton);
		top.addComponent(socialBar); // add social buttons to top
		bean.addComponent(top); // add top to master

		// add calendar to master
		bean.addComponent(calendar);
		calendar.setSizeFull();

		return bean;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public VerticalLayout rightLayout()
	{
		VerticalLayout bean = new VerticalLayout();
		return bean;
	}
}