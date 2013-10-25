package com.bleulace.web.annotation;

import com.vaadin.navigator.View;

aspect VaadinStereotypingAspect
{
	declare parents : @VaadinView * implements View;
}