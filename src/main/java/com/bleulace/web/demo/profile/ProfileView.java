package com.bleulace.web.demo.profile;

import javax.annotation.PostConstruct;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.web.SystemUser;
import com.bleulace.web.annotation.VaadinView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@VaadinView
class ProfileView extends CustomComponent implements View
{
	@Autowired
	private SystemUser user;

	@Autowired
	private ProfilePresenter presenter;

	@Autowired
	@Qualifier("profileMenuBar")
	private MenuBar menuBar;

	@Autowired
	private TreeTable resourceTable;

	@Autowired
	@Qualifier("profileCalendar")
	private Calendar calendar;

	@Autowired
	@Qualifier("profileTabSheet")
	private TabSheet tabSheet;

	@Autowired
	private ApplicationContext ctx;

	// panel rules everything around me
	private final HorizontalSplitPanel panel = new HorizontalSplitPanel();

	public ProfileView()
	{
	}

	@PostConstruct
	protected void init()
	{
		VerticalLayout layout = new VerticalLayout(menuBar, panel);
		setCompositionRoot(layout);
		layout.setSizeFull();
		menuBar.setWidth(100f, Unit.PERCENTAGE);
		panel.setSizeFull();
		layout.setExpandRatio(panel, 1.0f);
	}

	@Override
	@RequiresUser
	public void enter(ViewChangeEvent event)
	{
		setSizeFull();

		final String id = event.getParameters();

		user.setTarget(id);
		presenter.init(id);

		final String firstName = presenter.getAccount().getContactInformation()
				.getFirstName();

		panel.setFirstComponent(makeLeft());
		panel.setSecondComponent(makeCenter());

		panel.setSplitPosition(15f, Unit.PERCENTAGE);
	}

	private Layout makeCenter()
	{
		Calendar cal = (Calendar) ctx.getBean("calendar", presenter
				.getAccount().getId());
		TabSheet tabs = (TabSheet) ctx.getBean("calendarTabsheet", cal);
		VerticalLayout center = new VerticalLayout(tabs, cal);
		center.setSpacing(false);
		center.setHeight(100f, Unit.PERCENTAGE);
		cal.setSizeFull();
		center.setExpandRatio(cal, 1.0f);
		return center;
	}

	private Layout makeLeft()
	{
		Account account = presenter.getAccount();

		Image avatar = (Image) ctx.getBean("avatar", account);
		avatar.setWidth(100f, Unit.PERCENTAGE);
		avatar.setHeight(100f, Unit.PERCENTAGE);

		Label name = new Label(account.getContactInformation().getName());
		name.addStyleName(Reindeer.LABEL_H1);

		VerticalLayout layout = new VerticalLayout(avatar, name, resourceTable);
		return layout;
	}
}