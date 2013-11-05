package com.bleulace.web.demo.profile;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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
import com.bleulace.utils.IdsCallback;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TreeTable;

@Configuration
class ProfileConfig
{
	@Autowired
	private ApplicationContext ctx;

	@Bean
	@Scope("ui")
	public ComboBox resourceSearchField(
			@Qualifier("resourceContainer") JPAContainer<?> container)
	{
		ComboBox bean = new ComboBox();
		bean.setContainerDataSource(container);
		bean.setItemCaptionPropertyId("title");
		bean.setImmediate(true);
		return bean;
	}

	@Bean
	@Scope("ui")
	public TreeTable resourceTable(
			Converter<String, DateTime> jodaDateTimeConverter,
			@Qualifier("resourceContainer") JPAContainer<?> container)
	{

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

	@Bean
	@Scope("ui")
	public JPAContainer<?> resourceContainer()
	{
		final JPAContainer<?> container = (JPAContainer<?>) ctx.getBean(
				"jpaContainer", Project.class,
				TransactionalEntityProvider.class, new IdsCallback()
				{
					@Override
					public Set<String> evaluate()
					{
						String id = ctx.getBean(ProfilePresenter.class)
								.getAccount().getId();
						return new HashSet<String>(SpringApplicationContext
								.getBean(ResourceDAO.class).findIdsForManager(
										id));
					}
				});
		container.setParentProperty("parent");
		return container;
	}
}