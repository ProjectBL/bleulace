package com.bleulace.ui.infrastructure;

import com.bleulace.cqrs.event.EventBusPublisher;
import com.vaadin.navigator.View;

aspect VaadinViewAspect
{

	declare parents :@VaadinView * implements View;
	declare parents :@VaadinView * implements EventBusPublisher;
}