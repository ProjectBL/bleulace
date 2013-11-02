package com.bleulace.web.demo.profile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.Gender;
import com.bleulace.domain.crm.model.Account;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;

@Lazy
@Component
@Scope("prototype")
class Avatar implements FactoryBean<Image>
{
	private static final String AVATAR_LOCATION_MALE = "img/ProfilePlaceholderMale.png";

	private static final String AVATAR_LOCATION_FEMALE = "img/ProfilePlaceholderFemale.jpg";

	private Account account;

	Avatar(Account account)
	{
		this.account = account;
	}

	@SuppressWarnings("unused")
	private Avatar()
	{
	}

	@Override
	public Image getObject() throws Exception
	{
		return account == null || account.getAvatar() == null ? makeGeneric()
				: make();
	}

	@Override
	public Class<?> getObjectType()
	{
		return Image.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}

	private Image makeGeneric()
	{
		Gender gender = Gender.MALE;

		Image avatar = new Image("", new ThemeResource(
				getAvatarLocation(gender)));
		return avatar;
	}

	private Image make()
	{
		StreamResource resource = new StreamResource(new StreamSource()
		{
			@Override
			public InputStream getStream()
			{
				return new ByteArrayInputStream(account.getAvatar());
			}
		}, account.getId() + ".png");
		Image image = new Image("", resource);
		return image;
	}

	private String getAvatarLocation(Gender gender)
	{
		switch (gender)
		{
		case MALE:
			return AVATAR_LOCATION_MALE;
		case FEMALE:
			return AVATAR_LOCATION_FEMALE;
		default:
			throw new IllegalArgumentException();
		}
	}
}