package com.bleulace.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

//@formatter:off

/***********************************************
 *  _______                        
 * '   /      ___    ___  , _ , _  
 *     |    .'   `  /   ` |' `|' `.
 *     |    |----' |    | |   |   |
 *     /    `.___, `.__/| /   '   / 
 * ____    .                 .                       
 * /   \   |     ___  ,   .  |     ___    ___    ___ 
 * |,_-<   |   .'   ` |   |  |    /   ` .'   ` .'   `
 * |    `  |   |----' |   |  |   |    | |      |----'
 * `----' /\__ `.___, `._/| /\__ `.__/|  `._.' `.___,
 * 
 * "Novelty in Productivity!"
 * --------------------------------------------------
 * @founder Hamid Bundu
 * @founder Idris Noor
 * @founder Samar Elatta
 * @founder Arleigh Dickerson
 * --------------------------------------------------
 * 
 * 
 * @author Arleigh Dickerson
 * This is the bootstrap for the web user interface, to be served by tomcat.
 * 
 *********************************************** 
 */

//@formatter:on
@Push
@Widgetset("com.vaadin.DefaultWidgetSet")
@Theme("bleulacetheme")
public class WebUI extends UI implements DetachListener
{
	@Autowired
	private transient ApplicationContext ctx;

	@Override
	protected void init(VaadinRequest request)
	{
		addDetachListener(this);
		// String navState = (String) SecurityUtils.getSubject().getSession()
		// .getAttribute("navState");
		// getNavigator().navigateTo(navState);
	}

	@Override
	public void detach(DetachEvent event)
	{
		for (String viewName : ctx.getBeansWithAnnotation(VaadinView.class)
				.keySet())
		{
			getNavigator().removeView(viewName);
		}

		for (String name : ctx.getBeansOfType(NavStateConversion.class)
				.keySet())
		{
			getNavigator().removeView(name);
		}
	}
}