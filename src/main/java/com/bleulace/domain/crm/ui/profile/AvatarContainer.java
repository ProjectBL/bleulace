package com.bleulace.domain.crm.ui.profile;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

class AvatarContainer extends CustomComponent
{
	private final Image avatar = makeImage();
	private final Label label = makeLabel();

	AvatarContainer()
	{

		VerticalLayout layout = new VerticalLayout();
		layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		layout.addComponent(avatar);
		layout.addComponent(label);
		layout.setSizeFull();
		layout.setMargin(false);

		setCompositionRoot(layout);
	}

	@Override
	public void setCaption(String caption)
	{
		label.setCaption(caption);
	}

	void setSource(Resource source)
	{
		avatar.setSource(source);
	}

	private Label makeLabel()
	{
		Label l = new Label("");
		l.setStyleName(Reindeer.LABEL_H1);
		l.setSizeUndefined();
		return l;
	}

	private Image makeImage()
	{
		Image image = new Image(null, new ThemeResource(
				"img/ProfilePlaceholder.png"));
		image.setSizeFull();
		return image;
	}
}
