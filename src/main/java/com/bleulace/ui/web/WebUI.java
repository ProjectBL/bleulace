package com.bleulace.ui.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.ui.infrastructure.NavigatorFactory;
import com.vaadin.annotations.Push;
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
@Push
@Configurable
@Widgetset("com.vaadin.DefaultWidgetSet")
@Theme("bleulacetheme")
public class WebUI extends UI
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2191009197124553972L;

	@Override
	protected void init(VaadinRequest request)
	{
		setNavigator(NavigatorFactory.make(this));
		SecurityUtils.getSubject()
				.login(new UsernamePasswordToken("hamidbundu@facebook.com",
						"password"));
		getNavigator().navigateTo(
				"profileView/" + SecurityUtils.getSubject().getPrincipal());
	}
}