package com.bleulace.web.demo.profile;

import java.util.Collection;
import java.util.Locale;

import org.joda.time.DateTime;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.management.model.Project;
import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.TransactionalEntityProvider;
import com.bleulace.utils.IdCallback;
import com.bleulace.utils.IdsCallback;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Component;
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
	public TreeTable resourceTable(
			Converter<String, DateTime> jodaDateTimeConverter)
	{
		final JPAContainer<?> container = (JPAContainer<?>) ctx.getBean(
				"jpaContainer", Project.class,
				TransactionalEntityProvider.class, new IdsCallback()
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

		final TreeTable bean = new TreeTable();
		bean.setContainerDataSource(container);
		bean.setVisibleColumns(new Object[] { "title", "createdDate",
				"lastModifiedDate", "complete" });
		bean.setConverter("createdDate", jodaDateTimeConverter);
		bean.setConverter("lastModifiedDate", jodaDateTimeConverter);
		bean.setReadOnly(false);
		bean.addItemClickListener(new ItemClickListener()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void itemClick(ItemClickEvent event)
			{
				if (event.isDoubleClick())
				{
					EntityItem<AbstractResource> item = (EntityItem<AbstractResource>) event
							.getItem();
					ctx.getBean(ProfilePresenter.class).resourceSelected(item);

				}
			}
		});
		bean.setSelectable(true);
		return bean;
	}

	@Bean
	@Scope("ui")
	public TabSheet profileTabSheet(
			@Qualifier("profileCalendar") Calendar calendar)
	{
		TabSheet bean = (TabSheet) ctx.getBean("calendarTabSheet", calendar);
		bean.setCloseHandler(new TabSheet.CloseHandler()
		{
			@Override
			public void onTabClose(final TabSheet tabSheet,
					final Component tabContent)
			{
				ctx.getBean(ProfilePresenter.class).tabClosing(
						(EntityItem<?>) ((AbstractComponent) tabContent)
								.getData(), new Runnable()
						{
							@Override
							public void run()
							{
								tabSheet.removeComponent(tabContent);
							}
						});
			}
		});
		bean.setImmediate(true);
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

	@Bean
	public Converter<String, DateTime> jodaDateTimeConverter()
	{
		return new Converter<String, DateTime>()
		{

			@Override
			public DateTime convertToModel(String value,
					Class<? extends DateTime> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String convertToPresentation(DateTime value,
					Class<? extends String> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException
			{
				return new PrettyTime().format(value.toDate());
			}

			@Override
			public Class<DateTime> getModelType()
			{
				return DateTime.class;
			}

			@Override
			public Class<String> getPresentationType()
			{
				return String.class;
			}
		};
	}
}