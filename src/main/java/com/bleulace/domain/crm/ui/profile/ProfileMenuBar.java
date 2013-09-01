package com.bleulace.domain.crm.ui.profile;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;

@UIComponent("profileMenuBar")
class ProfileMenuBar extends CustomComponent
{
	@Autowired
	private Component statusUpdateField;

	private PopupView popup;

	private final MenuBar menuBar = makeMenuBar();

	ProfileMenuBar()
	{
	}

	@PostConstruct
	protected void init()
	{
		popup = new PopupView("", statusUpdateField);
		VerticalLayout vLayout = new VerticalLayout(menuBar, popup);
		vLayout.setComponentAlignment(popup, Alignment.BOTTOM_LEFT);
		setCompositionRoot(vLayout);
	}

	private MenuBar makeMenuBar()
	{
		MenuBar menuBar = new MenuBar();
		menuBar.addItem("Status", new UpdateStatusCommand());
		menuBar.addItem("Image", null);
		menuBar.addItem("Playlist", null);
		menuBar.addItem("Event", null);
		menuBar.addItem("Pin", null);
		menuBar.addItem("Blog", null);
		menuBar.setWidth(100.0f, Unit.PERCENTAGE);
		return menuBar;
	}

	private class UpdateStatusCommand implements MenuBar.Command
	{
		@Override
		public void menuSelected(MenuItem selectedItem)
		{
			popup.setPopupVisible(true);
		}
	}
}