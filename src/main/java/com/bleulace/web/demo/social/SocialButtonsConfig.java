package com.bleulace.web.demo.social;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.bleulace.utils.SystemProfiles;
import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;

@Configuration
@Profile({ SystemProfiles.DEV, SystemProfiles.PROD })
class SocialButtonsConfig
{
	@Bean(name = "socialButtons")
	@Scope("prototype")
	public Iterable<Button> socialButtons()
	{
		List<Button> buttons = new ArrayList<Button>();
		for (SocialButtons value : SocialButtons.values())
		{
			buttons.add(value.make());
		}
		return buttons;
	}

	public enum SocialButtons implements Button.ClickListener
	{
		//@formatter:off
		SHARE(Icon.share), 
		RSS(Icon.rss), 
		MAIL(Icon.envelope), 
		FACEBOOK(Icon.facebook), 
		TWITTER(Icon.twitter);
		//@formatter:on

		private final Icon icon;

		SocialButtons(Icon icon)
		{
			this.icon = icon;
		}

		private Button make()
		{
			Button button = new Button(icon.toString(), this);
			button.setHtmlContentAllowed(true);
			return button;
		}

		@Override
		public void buttonClick(ClickEvent event)
		{
			Notification.show(this.toString() + " clicked");
		}
	}
}