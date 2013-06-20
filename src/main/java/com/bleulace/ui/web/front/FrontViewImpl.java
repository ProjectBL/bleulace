package com.bleulace.ui.web.front;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class FrontViewImpl extends CustomComponent implements FrontView
{
	private static final long serialVersionUID = 1199488980233798486L;

	private List<FrontViewListener> listeners = new ArrayList<FrontViewListener>();

	@Override
	public void enter(ViewChangeEvent event)
	{
		initializeModels();
		initializePresenters();
		buildView();
	}

	private void initializeModels()
	{
	}

	private void initializePresenters()
	{
		addListener(new FrontPresenter(this));
	}

	private void buildView()
	{
		setCompositionRoot(new LoginForm());
	}

	@Override
	public void addListener(FrontViewListener listener)
	{
		listeners.add(listener);
	}

	class LoginForm extends CustomComponent
	{
		private Component[] components;

		private static final long serialVersionUID = 1L;

		LoginForm()
		{
			initUI();
		}

		private void initUI()
		{
			final TextField username = new TextField("Username", "");
			final PasswordField password = new PasswordField("Password", "");
			Button submit = new Button("Submit", new ClickListener()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event)
				{
					for (FrontViewListener listener : listeners)
					{
						listener.onLogin(new UsernamePasswordToken(username
								.getValue(), password.getValue()));
					}
				}
			});
			submit.setClickShortcut(KeyCode.ENTER);
			components = new Component[] { username, password, submit };

			FormLayout layout = new FormLayout(username, password, submit);
			setCompositionRoot(new Panel("Log in", layout));
		}

		@Override
		public void setEnabled(boolean enabled)
		{
			super.setEnabled(enabled);
			for (Component c : components)
			{
				c.setEnabled(enabled);
			}
		}
	}
}