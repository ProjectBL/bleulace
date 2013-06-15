package net.bluelace.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class BluelaceUI extends UI
{
	private static final long serialVersionUID = 2378161374437587033L;

	@Override
	protected void init(VaadinRequest request)
	{
		setContent(new Label("hello there"));
	}

}
