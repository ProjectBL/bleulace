package com.bleulace.web.main;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
class ResourceContent extends CustomComponent
{
	ResourceContent()
	{
		setCompositionRoot(new Label("Placeholder"));
		setSizeFull();
	}
}