package com.bleulace.web.demo.social;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.bleulace.utils.SystemProfiles;
import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.ui.Button;

@Configuration
@Profile({ SystemProfiles.DEV, SystemProfiles.PROD })
class SocialButtonsConfig
{
	@Bean(name = "socialButtons")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Iterable<Button> socialButtons()
	{
		List<Button> buttons = new ArrayList<Button>();
		for (SocialButtons value : SocialButtons.values())
		{
			buttons.add(value.make());
		}
		return buttons;
	}

	enum SocialButtons
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

		SocialButtons(Icon icon, Button.ClickListener listener)
		{
			this.icon = icon;
			this.listener = listener;
		}

		SocialButtons(Icon icon)
		{
			this(icon, null);
		}

		private Button make()
		{
			Button button = new Button(icon.toString());
			button.setHtmlContentAllowed(true);
			if (listener != null)
			{
				button.addClickListener(listener);
			}
			return button;
		}
	}
}