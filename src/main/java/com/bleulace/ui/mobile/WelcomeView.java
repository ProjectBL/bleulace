package com.bleulace.ui.mobile;

import com.vaadin.addon.touchkit.gwt.client.theme.StyleNames;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class WelcomeView extends NavigationView
{
	private static final long serialVersionUID = -3086371884138162119L;

	private Button register = makeButton("register", StyleNames.BUTTON_GREEN);
	private Button login = makeButton("login");

	private Button cancel = makeButton("cancel", StyleNames.BUTTON_RED);

	public WelcomeView()
	{
		setCaption("Welcome");
		setSizeUndefined();
		VerticalComponentGroup group = new VerticalComponentGroup();

		Label title = new Label("<h1>BlueLace</h1>");
		title.setContentMode(ContentMode.HTML);

		Label description = new Label("Welcome to BlueLace Iphone App "
				+ "Please sign in if you are an existing "
				+ "user or simply register to use "
				+ "BlueLace with your friends");
		description.setContentMode(ContentMode.HTML);

		VerticalLayout layout = new VerticalLayout(title, description);
		group.addComponent(layout);
		group.addComponent(makeButtons());
		setContent(group);
	}

	private Component makeButtons()
	{
		VerticalLayout l = new VerticalLayout(register, login, cancel);
		l.setSpacing(true);
		return l;
	}

	private Button makeButton(String caption)
	{
		Button b = new Button(caption);
		b.setSizeFull();
		return b;
	}

	private Button makeButton(String caption, String styleName)
	{
		Button b = makeButton(caption);
		b.addStyleName(styleName);
		return b;
	}
}