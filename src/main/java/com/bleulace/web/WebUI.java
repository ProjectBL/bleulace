package com.bleulace.web;

import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

//@formatter:off
/***************************************************
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
 * @author Arleigh Dickerson
 * 
 * --USER INTERFACE BOOTSTRAP--------------------->>>
 ****************************************************
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
		Navigator nav = new Navigator(this, this);
		for (Entry<String, View> entry : ctx.getBeansOfType(View.class)
				.entrySet())
		{
			nav.addView(entry.getKey(), entry.getValue());
		}
		setNavigator(nav);
	}
}