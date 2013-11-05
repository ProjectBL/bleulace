package com.bleulace.web.demo.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Window;

import de.steinwedel.messagebox.MessageBox;

@Configuration
class MenuBarCommands
{
	@Autowired
	private ApplicationContext ctx;

	@Bean
	public MenuBar.Command createProjectCommand()
	{
		return new MenuBar.Command()
		{
			@Override
			public void menuSelected(MenuItem selectedItem)
			{
				ctx.getBean("projectMessageBox", MessageBox.class);
			}
		};
	}

	@Bean
	public MenuBar.Command createEventCommand()
	{
		return new MenuBar.Command()
		{
			@Override
			public void menuSelected(MenuItem selectedItem)
			{
				ctx.getBean("timeBox", Window.class);
			}
		};
	}

	@Bean
	public MenuBar.Command createTaskCommand()
	{
		return new MenuBar.Command()
		{
			@Override
			public void menuSelected(MenuItem selectedItem)
			{
				// TODO Auto-generated method stub

			}
		};
	}

	@Bean
	public MenuBar.Command createGroupCommand()
	{
		return new MenuBar.Command()
		{
			@Override
			public void menuSelected(MenuItem selectedItem)
			{
				// TODO Auto-generated method stub

			}
		};
	}
}
