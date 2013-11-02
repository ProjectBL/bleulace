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
import com.bleulace.jpa.TransactionalEntityProvider;
import com.bleulace.utils.IdCallback;
import com.bleulace.utils.IdsCallback;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.util.converter.Converter;
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
	public TreeTable resourceTable(
			Converter<String, DateTime> jodaDateTimeConverter)
	{
		JPAContainer<?> container = (JPAContainer<?>) ctx.getBean(
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

		TreeTable table = new TreeTable();
		table.setContainerDataSource(container);
		table.setVisibleColumns(new Object[] { "title", "createdDate",
				"lastModifiedDate", "complete" });
		table.setConverter("createdDate", jodaDateTimeConverter);
		table.setConverter("lastModifiedDate", jodaDateTimeConverter);
		table.setReadOnly(true);
		// table.setStyleName(Reindeer.TABLE_STRONG);
		return table;
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