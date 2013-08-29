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
@Widgetset("com.vaadin.DefaultWidgetSet")
@Theme("bleulacetheme")
@Configurable(preConstruction = true)
public class WebUI extends UI
{
	@Autowired
	private ApplicationContext ctx;

	@Override
	protected void init(VaadinRequest request)
	{
		setNavigator(makeNavigator());
		getNavigator().navigateTo("frontView");
	}

	private Navigator makeNavigator()
	{
		Navigator navigator = new Navigator(this, this);

		for (Entry<String, Object> entry : ctx.getBeansWithAnnotation(
				VaadinView.class).entrySet())
		{
			navigator.addView(entry.getKey(), (View) entry.getValue());
		}

		for (NavStateConversion bean : ctx.getBeansOfType(
				NavStateConversion.class).values())
		{
			for (String entryState : bean.getEntryStates())
			{
				navigator.addView(entryState, bean);
			}
		}

		// hack to eagerly load presenters
		ctx.getBeansWithAnnotation(Presenter.class).values();

		navigator.addViewChangeListener(ctx
				.getBean(PresenterSubscribingListener.class));

		return navigator;
	}
}