package com.bleulace.web.demo.shared;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;

public interface OpenResourceDialogue
{
	void addOpenResourceListener(OpenResourceListener listener);

	void setTitle(String title);

	void setContainer(JPAContainer<?> container);

	void show();

	public interface OpenResourceListener
	{
		void resourceOpened(EntityItem<?> resource);
	}
}