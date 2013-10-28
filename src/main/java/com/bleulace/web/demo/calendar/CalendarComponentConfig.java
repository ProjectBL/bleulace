package com.bleulace.web.demo.calendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.jpa.TransactionalEntityProvider;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.annotation.WebProfile;
import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;

@Configuration
@WebProfile
public class CalendarComponentConfig
{
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private CalendarPresenter presenter;

	@Autowired
	private EventDAO eventDAO;

	@PersistenceContext
	private EntityManager em;

	@Bean
	@Scope("ui")
	public Button homeButton()
	{
		Button bean = makeIconButton(Icon.home);
		return bean;
	}

	@Bean
	@Scope("ui")
	public Button lockButton()
	{
		Button bean = makeIconButton(Icon.lock);
		return bean;
	}

	@Bean
	@Scope("ui")
	public TextField statusUpdateField()
	{
		final TextField bean = new TextField();
		bean.setInputPrompt("What's happening?");
		bean.addValueChangeListener(new ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				presenter.statusUpdated(bean.getValue());
			}
		});
		return bean;
	}

	@Bean
	@Scope("ui")
	public Accordion accordion()
	{
		final Accordion bean = new Accordion();
		for (final AccordionHeader header : AccordionHeader.values())
		{
			Component tabContent = header.getTabContent();
			bean.addTab(tabContent, header.toString());
			tabContent.setSizeFull();
		}
		bean.addSelectedTabChangeListener(new SelectedTabChangeListener()
		{
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event)
			{
				int i = bean.getTabPosition(bean.getTab(bean.getSelectedTab()));
				ctx.getBean(CalendarView.class).showMainContent(
						AccordionHeader.values()[i].getScreenContent());
			}
		});
		return bean;
	}

	private enum AccordionHeader
	{
		//@formatter:off
		CALENDAR("Calendar", "searchFieldLayout", "centerLayout"), 
		PROJECTS("Projects", null, null), 
		FRIENDS("Friends", "friendSearchLayout", "friendsScreen"), 
		GROUPS("Groups", null, null);
		// @formatter:on

		private final String name;
		private final String tabName;
		private final String screenName;

		AccordionHeader(String name, String tabName, String screenName)
		{
			this.name = name;
			this.screenName = screenName;
			this.tabName = tabName;
		}

		@Override
		public String toString()
		{
			return name;
		}

		Component getTabContent()
		{
			return getComponent(tabName);
		}

		Component getScreenContent()
		{
			return getComponent(screenName);
		}

		private Component getComponent(String name)
		{
			if (name == null)
			{
				name = "blankLabel";
			}
			return SpringApplicationContext.getBean(Component.class, name);
		}
	}

	@Bean
	@Scope("prototype")
	public Label blankLabel()
	{
		return new Label("");
	}

	@Bean
	@Scope("ui")
	public Calendar calendar(EventClickHandler eventClickHandler,
			RangeSelectHandler rangeSelectHandler,
			DateClickHandler dateClickHandler,
			EventMoveHandler eventMoveHandler,
			EventResizeHandler eventResizeHandler, Handler actionHandler)
	{
		Calendar bean = new Calendar();
		bean.setHandler(eventClickHandler);
		bean.setHandler(rangeSelectHandler);
		bean.setHandler(dateClickHandler);
		bean.setHandler(eventMoveHandler);
		bean.setHandler(eventResizeHandler);
		bean.addActionHandler(actionHandler);
		bean.setImmediate(true);
		return bean;
	}

	@Bean
	@Scope("ui")
	public DateClickHandler dateClickHandler()
	{
		final DateClickHandler basicDateClickHandler = new BasicDateClickHandler();

		DateClickHandler bean = new DateClickHandler()
		{
			@Override
			public void dateClick(DateClickEvent event)
			{
				basicDateClickHandler.dateClick(event);
				ctx.getBean("weekButton", Button.class).setVisible(true);
				ctx.getBean("monthButton", Button.class).setVisible(true);
			}
		};
		return bean;
	}

	@Bean
	@Scope("ui")
	public Button monthButton(final Calendar calendar)
	{
		final Button bean = new Button("Month view");
		bean.addClickListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				CalendarSelection.MONTH.execute(calendar);
				bean.setVisible(false);
			}
		});
		return bean;
	}

	@Bean
	@Scope("ui")
	public Button weekButton(final Calendar calendar)
	{
		final Button bean = new Button("Week view");
		bean.addClickListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				CalendarSelection.WEEK.execute(calendar);
				bean.setVisible(false);
				ctx.getBean("monthButton", Button.class).setVisible(true);
			}
		});
		return bean;
	}

	@Bean
	@Scope("ui")
	public JPAContainer<PersistentEvent> searchFieldContainer()
	{
		JPAContainer<PersistentEvent> container = new JPAContainer<PersistentEvent>(
				PersistentEvent.class);
		container
				.setEntityProvider(new TransactionalEntityProvider<PersistentEvent>(
						PersistentEvent.class));
		container.setQueryModifierDelegate(new DefaultQueryModifierDelegate()
		{
			@Override
			public void queryWillBeBuilt(CriteriaBuilder criteriaBuilder,
					CriteriaQuery<?> query)
			{
				query.distinct(true);
			}
		});
		return container;
	}

	@Bean
	@Scope("ui")
	public ComboBox searchField(
			@Qualifier("searchFieldContainer") final JPAContainer<PersistentEvent> container)
	{
		final ComboBox bean = new ComboBox();
		bean.setContainerDataSource(container);
		bean.setBuffered(false);
		bean.setImmediate(true);
		bean.setInputPrompt("Search");
		bean.setItemCaptionMode(ItemCaptionMode.ITEM);
		return bean;
	}

	@Bean
	@Scope("ui")
	public VerticalLayout searchFieldLayout(
			@Qualifier("searchField") ComboBox searchField)
	{
		VerticalLayout bean = new VerticalLayout(searchField);
		bean.setMargin(false);
		return bean;
	}

	private static Button makeIconButton(Icon icon)
	{
		Button button = new Button(icon.toString());
		button.setHtmlContentAllowed(true);
		return button;
	}
}