package com.bleulace.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
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
@PreserveOnRefresh
@Theme("bleulacetheme")
@Widgetset("com.bleulace.web.client.BleulaceWidgetSet")
@Configurable
public class WebUI extends UI
{
	@Autowired
	private ApplicationContext ctx;

	@Override
	protected void init(VaadinRequest request)
	{
		/*
		 * setNavigator(new Navigator(this, this));
		 * 
		 * for (Entry<String, Object> entry : ctx.getBeansWithAnnotation(
		 * Screen.class).entrySet()) { getNavigator().addView(entry.getKey(),
		 * (View) entry.getValue()); }
		 * 
		 * for (NavStateConversion bean : ctx.getBeansOfType(
		 * NavStateConversion.class).values()) { for (String entryState :
		 * bean.getEntryStates()) { getNavigator().addView(entryState, bean); }
		 * }
		 */
		setContent(new Label("foo"));
	}
}