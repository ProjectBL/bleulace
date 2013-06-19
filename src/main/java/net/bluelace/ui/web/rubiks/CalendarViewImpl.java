package net.bluelace.ui.web.rubiks;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Panel;

public class CalendarViewImpl extends CustomComponent implements CalendarView
{
	private static final long serialVersionUID = -8161583092068261965L;

	private Panel panel;

	@Override
	public void enter(ViewChangeEvent event)
	{
		panel = new Panel();
		setCompositionRoot(panel);
	}

	@Override
	public void showCenterContent(String title, Component component)

	{
		panel.setCaption(title);
		panel.setContent(component);
	}

	private void initialize(Component center)
	{

		Button decrement = new Button("<", new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{

			}
		});

		Button increment = new Button(">", new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{

			}
		});
	}
}