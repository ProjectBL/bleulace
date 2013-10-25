package com.bleulace.web.demo.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
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
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;

@Configuration
@Profile({ SystemProfiles.DEV, SystemProfiles.PROD })
class UIComponentsConfig
{
	/**********************************************
	 * LEFT
	 */

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public DateField dateField(final Calendar calendar)
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
				Date value = bean.getValue();
				System.out.println(value);
				DateTime start = LocalDate.fromDateFields(bean.getValue())
						.toDateTimeAtStartOfDay();
				calendar.setStartDate(start.toDate());
				calendar.setEndDate(start.plusDays(1).minusMillis(1).toDate());
			}
		});
		return bean;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Button homeButton()
	{
		Button bean = makeIconButton(Icon.home);
		return bean;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Button lockButton()
	{
		Button bean = makeIconButton(Icon.lock);
		return bean;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public TextField statusUpdateField()
	{
		TextField bean = new TextField();
		bean.setInputPrompt("What's happening?");
		return bean;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
	}

	/**********************************************
	 * CENTER
	 */

	@Bean
	@Scope("session")
	public TabSheet tabSheet(final Calendar calendar)
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
						selection.command.execute(calendar);
					}
				}
			});
		}
		return bean;
	}

	interface CalendarCommand
	{
		void execute(Calendar calendar);
	}

	enum CalendarSelection
	{
		//@formatter:off
		DAY("Day",new DayCommand()), 
		WEEK("Week",new WeekCommand()), 
		MONTH("Month",new MonthCommand());
		//@formatter:on

		final String selectionName;
		final CalendarCommand command;

		CalendarSelection(String selectionName, CalendarCommand command)
		{
			this.selectionName = selectionName;
			this.command = command;
		}

		@Override
		public String toString()
		{
			return selectionName;
		}
	}

	static class DayCommand implements CalendarCommand
	{
		@Override
		public void execute(Calendar calendar)
		{
			DateTime start = LocalDate.now().toDateTimeAtStartOfDay();
			calendar.setStartDate(start.toDate());
			calendar.setEndDate(start.plusDays(1).minusMillis(1).toDate());
		}
	}

	static class WeekCommand implements CalendarCommand
	{
		@Override
		public void execute(Calendar calendar)
		{
			DateTime start = LocalDate.now().toDateTimeAtStartOfDay();
			start = start.withDayOfWeek(start.toGregorianCalendar()
					.getFirstDayOfWeek());
			calendar.setStartDate(start.toDate());
			calendar.setEndDate(start.plusWeeks(1).minusMillis(1).toDate());
		}
	}

	static class MonthCommand implements CalendarCommand
	{
		@Override
		public void execute(Calendar calendar)
		{
			DateTime start = LocalDate.now().toDateTimeAtStartOfDay();
			start = start.withDayOfMonth(1);
			calendar.setStartDate(start.toDate());
			calendar.setEndDate(start.plusMonths(1).minusMillis(1).toDate());
		}
	}

	@Bean
	@Scope("session")
	public Calendar calendar(EventClickHandler eventClickHandler,
			RangeSelectHandler rangeSelectHandler, Handler actionHandler)
	{
		Calendar bean = new Calendar();
		bean.setHandler(eventClickHandler);
		bean.setHandler(rangeSelectHandler);
		bean.addActionHandler(actionHandler);
		bean.setImmediate(true);
		return bean;
	}

	/**********************************************
	 * RIGHT
	 */

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ComboBox searchField()
	{
		ComboBox bean = new ComboBox();
		bean.setInputPrompt("Search");
		return bean;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public List<Button> socialButtons()
	{
		List<Button> bean = new ArrayList<Button>();
		for (SocialButton b : SocialButton.values())
		{
			bean.add(b.make());
		}
		return bean;
	}

	private enum SocialButton
	{
		//@formatter:off
		SHARE(Icon.share), 
		RSS(Icon.rss), 
		MAIL(Icon.envelope), 
		FACEBOOK(Icon.facebook), 
		TWITTER(Icon.twitter);
		//@formatter:on

		private final Icon icon;

		private final Button.ClickListener listener;

		SocialButton(Icon icon, Button.ClickListener listener)
		{
			this.icon = icon;
			this.listener = listener;
		}

		SocialButton(Icon icon)
		{
			this(icon, null);
		}

		Button make()
		{
			Button button = makeIconButton(icon);
			if (listener != null)
			{
				button.addClickListener(listener);
			}
			return button;
		}
	}

	private static Button makeIconButton(Icon icon)
	{
		Button button = new Button(icon.toString());
		button.setHtmlContentAllowed(true);
		return button;
	}
}