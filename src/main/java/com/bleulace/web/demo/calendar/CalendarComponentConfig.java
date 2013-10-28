package com.bleulace.web.demo.calendar;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;

@Configuration
@WebProfile
class CalendarComponentConfig
{
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private CalendarPresenter presenter;

	@Autowired
	private EventDAO eventDAO;

	@PersistenceContext
	private EntityManager em;

	/**********************************************
	 * LEFT
	 */

	@Bean
	@Scope("ui")
	public DateField dateField()
	{
		final DateField bean = new DateField();
		bean.setResolution(Resolution.DAY);
		bean.setValue(new Date());
		bean.setBuffered(false);
		bean.setImmediate(true);
		bean.addValueChangeListener(new ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				presenter.cursorChanged();
			}
		});
		return bean;
	}

	@Bean
	@Scope("prototype")
	public Button homeButton()
	{
		Button bean = makeIconButton(Icon.home);
		return bean;
	}

	@Bean
	@Scope("prototype")
	public Button lockButton()
	{
		Button bean = makeIconButton(Icon.lock);
		return bean;
	}

	@Bean
	@Scope("prototype")
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
			Label label = new Label("");
			bean.addTab(label, header.toString());
			label.setWidth(100f, Unit.PERCENTAGE);
		}
		bean.addSelectedTabChangeListener(new SelectedTabChangeListener()
		{
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event)
			{
				int i = bean.getTabPosition(bean.getTab(bean.getSelectedTab()));
				ctx.getBean(CalendarView.class).showMainContent(
						AccordionHeader.values()[i].getContent());
			}
		});
		return bean;
	}

	private enum AccordionHeader
	{
		CALENDAR("Calendar", "centerLayout"), PROJECTS("Projects"), FRIENDS(
				"Friends"), GROUPS("Groups");

		private final String name;
		private final String beanName;

		AccordionHeader(String name)
		{
			this(name, null);
		}

		AccordionHeader(String name, String beanName)
		{
			this.name = name;
			this.beanName = beanName;
		}

		@Override
		public String toString()
		{
			return name;
		}

		Component getContent()
		{
			return beanName == null ? new Label("") : SpringApplicationContext
					.getBean(Component.class, beanName);
		}
	}

	/**********************************************
	 * CENTER
	 */

	@Bean
	@Scope(value = "ui")
	public TabSheet tabSheet()
	{
		final TabSheet bean = new TabSheet();
		for (final CalendarSelection selection : CalendarSelection.values())
		{
			final Tab tab = bean.addTab(new AbsoluteLayout(),
					selection.toString());
			bean.addSelectedTabChangeListener(new SelectedTabChangeListener()
			{
				final int tabPosition = bean.getTabPosition(tab);

				@Override
				public void selectedTabChange(SelectedTabChangeEvent event)
				{
					if (bean.getTabPosition(bean.getTab(bean.getSelectedTab())) == tabPosition)
					{
						presenter.tabSelected(selection);
					}
				}
			});
		}
		return bean;
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

	/**********************************************
	 * RIGHT
	 */
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
	public ComboBox searchField(final JPAContainer<PersistentEvent> container,
			final TabSheet tabSheet, final Calendar calendar)
	{
		final ComboBox bean = new ComboBox();
		bean.setInputPrompt("Search");
		bean.setContainerDataSource(container);
		bean.setItemCaptionMode(ItemCaptionMode.ITEM);
		bean.addValueChangeListener(new Property.ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				System.out.println(event);
			}
		});
		bean.setImmediate(true);

		return bean;
	}

	private static Button makeIconButton(Icon icon)
	{
		Button button = new Button(icon.toString());
		button.setHtmlContentAllowed(true);
		return button;
	}
}