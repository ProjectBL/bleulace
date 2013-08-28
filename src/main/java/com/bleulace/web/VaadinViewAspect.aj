package com.bleulace.web;


public aspect VaadinViewAspect
{
	private interface IVaadinView
	{
	}

	declare parents : @VaadinView * implements IVaadinView;

	public String IVaadinView.getViewName()
	{
		return getClass().getAnnotation(VaadinView.class).value();
	}
}