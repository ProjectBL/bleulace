package com.bleulace.web.demo.menu;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.AccountGroup;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.Project;
import com.bleulace.domain.management.model.Task;
import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.TransactionalEntityProvider;
import com.bleulace.utils.IdsCallback;
import com.bleulace.web.SystemUser;
import com.bleulace.web.demo.resource.ResourceSelectedEvent;
import com.bleulace.web.demo.shared.OpenResourceDialogue;
import com.bleulace.web.demo.shared.OpenResourceDialogue.OpenResourceListener;
import com.google.common.collect.ImmutableMap;
import com.google.common.eventbus.EventBus;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

@Configuration
class MenuBarConfig
{
	private final String[] actions = new String[] { "New", "Open" };

	//@formatter:off
	private final Map<String, Class<? extends AbstractResource>> targets = 
			ImmutableMap.of(
					"Project",Project.class, 
					"Event", PersistentEvent.class, 
					"Task", Task.class,
					"Group", AccountGroup.class);
	//@formatter:on

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private SystemUser user;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private ResourceDAO resourceDAO;

	@Bean
	@Scope("ui")
	public MenuBar menuBar()
	{
		MenuBar root = new MenuBar();
		MenuItem file = root.addItem("File", null);
		MenuBar.MenuItem nw = file.addItem("New", null);
		for (Entry<String, Class<? extends AbstractResource>> entry : targets
				.entrySet())
		{
			nw.addItem(entry.getKey(),
					makeNewResourceCommand(entry.getKey(), entry.getValue()));
		}

		MenuBar.MenuItem open = file.addItem("Open", null);
		for (Entry<String, Class<? extends AbstractResource>> entry : targets
				.entrySet())
		{
			open.addItem(entry.getKey(),
					makeOpenResourceCommand(entry.getKey(), entry.getValue()));
		}

		MenuItem view = root.addItem("View", null);
		view.addItem("Profile", null);
		view.addItem("Projects", null);
		view.addItem("Friends", null);
		view.addItem("Groups", null);

		MenuItem help = root.addItem("Help", null);

		return root;
	}

	private MenuBar.Command makeNewResourceCommand(final String name,
			final Class<? extends AbstractResource> clazz)
	{
		return ctx.getBean("create" + name + "Command", Command.class);
	}

	private MenuBar.Command makeOpenResourceCommand(final String name,
			final Class<? extends AbstractResource> clazz)
	{
		return new MenuBar.Command()
		{
			@Override
			public void menuSelected(MenuItem selectedItem)
			{
				OpenResourceDialogue dialogue = ctx
						.getBean(OpenResourceDialogue.class);
				dialogue.setTitle("Open " + name);
				dialogue.setContainer(makeContainer(clazz));
				dialogue.addOpenResourceListener(new OpenResourceListener()
				{

					@Override
					public void resourceOpened(EntityItem<?> resource)
					{
						ctx.getBean("uiBus", EventBus.class).post(
								new ResourceSelectedEvent(resource));
					}
				});
				dialogue.show();
			}
		};
	}

	private JPAContainer<?> makeContainer(Class<?> clazz)
	{
		IdsCallback callback = new IdsCallback()
		{
			@Override
			public Collection<String> evaluate()
			{
				final String id = user.getId();
				List<String> ids = accountDAO.findFriendIds(id);
				ids.addAll(resourceDAO.findIdsForManager(id));
				return ids;
			}
		};
		JPAContainer<?> container = (JPAContainer<?>) ctx.getBean(
				"jpaContainer", clazz, TransactionalEntityProvider.class,
				callback);
		return container;
	}
}