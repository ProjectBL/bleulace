package com.bleulace.web.demo.profile;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.Project;
import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.TransactionalEntityProvider;
import com.bleulace.utils.IdCallback;
import com.bleulace.utils.IdsCallback;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TreeTable;

@Configuration
class ProfileConfig
{
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private ResourceDAO resourceDAO;

	@Bean
	@Scope("ui")
	public MenuBar profileMenuBar()
	{
		MenuBar bean = new MenuBar();
		MenuItem file = bean.addItem("File", null);
		MenuItem edit = bean.addItem("Edit", null);
		MenuItem view = bean.addItem("View", null);
		return bean;
	}

	@Bean
	@Scope("ui")
	@DependsOn("profileContainerMap")
	public TreeTable resourceTable()
	{
		TreeTable bean = makeTable(AbstractResource.class);
		return bean;
	}

	@Bean
	@Scope("ui")
	public TabSheet profileTabSheet(
			@Qualifier("profileCalendar") Calendar calendar)
	{
		TabSheet bean = (TabSheet) ctx.getBean("calendarTabsheet", calendar);
		return bean;
	}

	@Bean
	@Scope("ui")
	public Calendar profileCalendar()
	{
		Calendar bean = (Calendar) ctx.getBean("calendar", new IdCallback()
		{
			@Override
			public String evaluate()
			{
				return ctx.getBean(ProfilePresenter.class).getAccount().getId();
			}
		});
		return bean;
	}

	private TreeTable makeTable(Class<?> clazz)
	{
		TreeTable table = new TreeTable();
		table.setContainerDataSource(getContainer(clazz));
		table.setVisibleColumns(new Object[] { "title", "start", "end" });
		return table;
	}

	@Scope("ui")
	@Bean(name = "profileContainerMap")
	public Map<Class<?>, JPAContainer<?>> profileContainerMap()
	{
		Builder<Class<?>, JPAContainer<?>> builder = ImmutableMap.builder();
		for (Class<?> clazz : new Class[] { AbstractResource.class,
				Project.class, PersistentEvent.class })
		{
			builder.put(clazz, makeContainer(clazz));
		}
		return builder.build();
	}

	private JPAContainer<?> makeContainer(Class<?> clazz)
	{
		JPAContainer<?> container = (JPAContainer<?>) ctx.getBean(
				"jpaContainer", clazz, TransactionalEntityProvider.class,
				new IdsCallback()
				{
					@Override
					public Collection<String> evaluate()
					{
						String id = ctx.getBean(ProfilePresenter.class)
								.getAccount().getId();
						return resourceDAO.findIdsForManager(id);
					}
				});
		container.setParentProperty("parent");
		return container;
	}

	private JPAContainer<?> getContainer(Class<?> clazz)
	{
		return ((Map<Class<?>, JPAContainer<?>>) ctx
				.getBean("profileContainerMap")).get(clazz);
	}
}