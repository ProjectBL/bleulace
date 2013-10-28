package com.bleulace.web.demo.friends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.web.BleulaceTheme.AvatarGender;
import com.bleulace.web.BleulaceTheme.AvatarSize;
import com.bleulace.web.demo.avatar.AvatarFactory;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@Configurable(preConstruction = true)
class FriendPolaroid extends CustomComponent
{
	@Autowired
	private AvatarFactory avatarFactory;

	@Autowired
	private FriendsPresenter presenter;

	FriendPolaroid(final Account account)
	{
		Assert.notNull(account);

		Label label = new Label(account.getContactInformation().getName());
		Image avatar = avatarFactory.make(AvatarGender.MALE, AvatarSize.MEDIUM);
		avatar.addClickListener(new ClickListener()
		{
			@Override
			public void click(ClickEvent event)
			{
				presenter.accountClicked(account);
			}
		});

		setCompositionRoot(new Panel(new VerticalLayout(avatar, label)));
	}
}