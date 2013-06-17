package net.bluelace.ui;

import net.bluelace.ui.registration.RegistrationView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("vornitologist")
@Widgetset("net.bluelace.ui.BluelaceWidgetSet")
@Title("bluelace devel")
public class BluelaceUI extends UI
{
	private static final long serialVersionUID = 2378161374437587033L;

	@Override
	protected void init(VaadinRequest request)
	{
		setContent(new RegistrationView());
	}
}
