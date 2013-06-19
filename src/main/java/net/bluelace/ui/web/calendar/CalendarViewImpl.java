package net.bluelace.ui.web.calendar;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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

	private Component center;
	private Panel panel;

	public CalendarViewImpl()
	{
		addViewListener(new CalendarPresenter(this));
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		center = new CustomComponent();
		makeLayout();
	}

	@Override
	public void showCenter(String title, Component content)
	{
		panel.setCaption(title);
		panel.setContent(content);
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
		HorizontalLayout hLayout = new HorizontalLayout();
		VerticalLayout vLayout = new VerticalLayout(new ButtonRow(), hLayout);
		setCompositionRoot(vLayout);

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

		Button forward = new Button(">");
		forward.setData(RequestDirection.FORWARD);

		Button backwards = new Button("<");
		backwards.setData(RequestDirection.BACKWARDS);

		hLayout.addComponents(backwards, center, forward);
	}

	class ButtonRow extends CustomComponent
	{
		private static final long serialVersionUID = 8410972783305859925L;

		public ButtonRow()
		{
			HorizontalLayout layout = new HorizontalLayout();
			setCompositionRoot(layout);
			for (TabDescriptor value : TabDescriptor.values())
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
									listener.onTabActivated((TabDescriptor) event
											.getButton().getData());
								}
							}
						});
				button.setData(value);
			}
		}
	}
}
