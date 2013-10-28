package com.bleulace.web.demo.calendar;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.web.annotation.VaadinView;
import com.bleulace.web.demo.friends.FriendsPresenter;
import com.bleulace.web.demo.timebox.TimeBox;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;

@VaadinView
class CalendarView extends CustomComponent implements View
{
	public static final String VIEW_NAME = "calendarView";

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private CalendarPresenter presenter;

	@Autowired
	private FriendsPresenter friendsPresenter;

	@Autowired
	private TimeBox timeBox;

	private final HorizontalSplitPanel panel;

	@Autowired
	CalendarView(VerticalLayout leftLayout, VerticalLayout centerLayout)
	{
		panel = new HorizontalSplitPanel(leftLayout, centerLayout);
		panel.setSplitPosition(200, Unit.PIXELS);
		panel.setLocked(true);
		setCompositionRoot(panel);
	}

	@Override
	@RequiresUser
	public void enter(ViewChangeEvent event)
	{
		Assert.notNull(accountDAO.findOne(event.getParameters()));
		presenter.setOwner(event.getParameters());
		friendsPresenter.setOwnerId(event.getParameters());

	}

	void showMainContent(Component content)
	{
		panel.setSecondComponent(content);
	}
}