package com.bleulace.ui.web;

import org.springframework.beans.factory.annotation.Configurable;

import ru.xpoft.vaadin.DiscoveryNavigator;

import com.bleulace.crm.infrastructure.ExecutingAccount;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
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
@Configurable(preConstruction = true)
@Widgetset("com.vaadin.DefaultWidgetSet")
@Theme("bleulacetheme")
public class WebUI extends UI
{
	private static final long serialVersionUID = 2191009197124553972L;

	@Override
	protected void init(VaadinRequest request)
	{
		setNavigator(new DiscoveryNavigator(this, this));
		if (ExecutingAccount.current() != null)
		{
			// somebody is currently logged in
			// TODO : navigate somewhere slow poke!
		}
		else
		{
			// nobody logged in
			getNavigator().navigateTo("front");
		}
	}
}