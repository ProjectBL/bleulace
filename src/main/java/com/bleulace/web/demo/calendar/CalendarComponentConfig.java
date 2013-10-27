package com.bleulace.web.demo.calendar;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.bleulace.utils.SystemProfiles;
import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComboBox;
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
@Profile({ SystemProfiles.DEV, SystemProfiles.PROD })
class CalendarComponentConfig
{
	@Autowired
	private CalendarPresenter presenter;

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
	@Scope("prototype")
	public Accordion accordion()
	{
		Accordion bean = new Accordion();
		for (AccordionHeader header : AccordionHeader.values())
		{
			Label l = new Label("");
			bean.addTab(l, header.toString());
			l.setWidth(100f, Unit.PERCENTAGE);
		}
		return bean;
	}

	private enum AccordionHeader
	{
		CREATE, PROFILE, EVENTS, PROJECTS, FRIENDS, GROUPS, BUNDLES, LACE, TIMEBOX;

		@Override
		public String toString()
		{
			return StringUtils.capitalize(this.name().toLowerCase());
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
	@Scope("prototype")
	public ComboBox searchField()
	{
		ComboBox bean = new ComboBox();
		bean.setInputPrompt("Search");
		return bean;
	}

	private static Button makeIconButton(Icon icon)
	{
		Button button = new Button(icon.toString());
		button.setHtmlContentAllowed(true);
		return button;
	}
}