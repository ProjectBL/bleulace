package com.bleulace.web.demo.profile;

import javax.annotation.PostConstruct;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.web.SystemUser;
import com.bleulace.web.annotation.VaadinView;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@VaadinView
class ProfileView extends CustomComponent implements View,
		TabSheet.CloseHandler
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
	private ApplicationContext ctx;

	private TabSheet tabSheet;

	// me
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

		panel.setFirstComponent(makeLeft());
		panel.setSecondComponent(makeCenter());

		panel.setSplitPosition(20f, Unit.PERCENTAGE);
	}

	private Layout makeCenter()
	{
		Calendar calendar = (Calendar) ctx.getBean("calendar", presenter);
		tabSheet = (TabSheet) ctx.getBean("calendarTabs", calendar);
		tabSheet.setCloseHandler(this);

		VerticalLayout center = new VerticalLayout(tabSheet);
		tabSheet.setSizeFull();
		center.setSpacing(false);
		center.setSizeFull();
		for (Component c : tabSheet)
		{
			center.setExpandRatio(tabSheet, 1.0f);
		}
		return center;
	}

	private Layout makeLeft()
	{
		Account account = presenter.getAccount();

		Image avatar = (Image) ctx.getBean("avatar", account);
		avatar.setWidth(100f, Unit.PERCENTAGE);

		Label name = new Label(account.getContactInformation().getName());
		name.addStyleName(Reindeer.LABEL_H1);

		VerticalLayout layout = new VerticalLayout(avatar, name, resourceTable);
		resourceTable.setWidth(100f, Unit.PERCENTAGE);
		return layout;
	}

	void openTab(EntityItem<?> item)
	{
		Component screen = (Component) ctx.getBean("resourceScreen", item);
		Tab tab = tabSheet.getTab(screen);
		if (tab == null)
		{
			tab = tabSheet.addTab(screen, screen.toString(), null,
					tabSheet.getComponentCount());
			tab.setClosable(true);
		}
	}

	void selectTab(EntityItem<?> item)
	{
		for (Component component : tabSheet)
		{
			if (item.equals(((AbstractComponent) component).getData()))
			{
				tabSheet.setSelectedTab(component);
				return;
			}
		}
	}

	@Override
	public void onTabClose(final TabSheet tabsheet, final Component tabContent)
	{
		presenter.tabClosing(
				(EntityItem<?>) ((AbstractComponent) tabContent).getData(),
				new Runnable()
				{
					@Override
					public void run()
					{
						tabsheet.removeTab(tabsheet.getTab(tabContent));
					}
				});
	}
}