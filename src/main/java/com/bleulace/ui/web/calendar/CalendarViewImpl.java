package com.bleulace.ui.web.calendar;

import java.util.ArrayList;
import java.util.List;

import com.bleulace.ui.web.calendar.CalendarType.RequestDirection;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

public class CalendarViewImpl extends CustomComponent implements CalendarView
{
	private static final long serialVersionUID = -8161583092068261965L;

	private List<CalendarViewListener> listeners = new ArrayList<CalendarViewListener>();

	private Panel panel = new Panel();

	public CalendarViewImpl()
	{
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		makeLayout();
		addViewListener(new CalendarPresenter(this));
	}

	@Override
	public void showTitle(String title)
	{
		panel.setCaption(title);
	}

	@Override
	public void showMainContent(Component mainContent)
	{
		panel.setContent(mainContent);
	}

	@Override
	public void showEvent(CalendarEvent event)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void addViewListener(CalendarViewListener listener)
	{
		listeners.add(listener);
	}

	private void makeLayout()
	{
		Button.ClickListener pageRequestListener = new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				for (CalendarViewListener listener : listeners)
				{
					listener.onDirectionRequest((RequestDirection) event
							.getButton().getData());
				}
			}
		};

		Button forward = new Button(">", pageRequestListener);
		forward.setData(RequestDirection.FORWARD);

		Button backwards = new Button("<", pageRequestListener);
		backwards.setData(RequestDirection.BACKWARD);

		HorizontalLayout hLayout = new HorizontalLayout();
		hLayout.addComponents(backwards, panel, forward);
		hLayout.setComponentAlignment(backwards, Alignment.MIDDLE_RIGHT);
		hLayout.setComponentAlignment(forward, Alignment.MIDDLE_LEFT);

		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		vLayout.addComponents(new ButtonRow(), hLayout);
		vLayout.setExpandRatio(hLayout, 1);
		setCompositionRoot(vLayout);
	}

	class ButtonRow extends CustomComponent
	{
		private static final long serialVersionUID = 8410972783305859925L;

		public ButtonRow()
		{
			HorizontalLayout layout = new HorizontalLayout();
			layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
			for (CalendarType value : CalendarType.values())
			{
				Button button = new Button(value.toString(),
						new Button.ClickListener()
						{
							private static final long serialVersionUID = 1L;

							@Override
							public void buttonClick(ClickEvent event)
							{
								for (CalendarViewListener listener : listeners)
								{
									listener.onTabActivated((CalendarType) event
											.getButton().getData());
								}
							}
						});
				button.setData(value);
				layout.addComponent(button);
			}
			setCompositionRoot(layout);
		}
	}
}
