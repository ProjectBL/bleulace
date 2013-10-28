package com.bleulace.web.demo.friends;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.jpa.TransactionalEntityProvider;
import com.bleulace.web.annotation.WebProfile;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;

@WebProfile
@Configuration
class FriendsComponentConfig
{
	@Bean
	@Scope("ui")
	JPAContainer<Account> friendContainer()
	{
		JPAContainer<Account> container = new JPAContainer<Account>(
				Account.class);
		container.setEntityProvider(new TransactionalEntityProvider<Account>(
				Account.class));
		return container;
	}

	@Bean
	@Scope("ui")
	public ComboBox friendSearchField(
			@Qualifier("friendContainer") final JPAContainer<Account> container)
	{
		final ComboBox bean = new ComboBox();
		bean.setContainerDataSource(container);
		bean.setItemCaptionPropertyId("title");
		bean.setInputPrompt("Search friends");
		bean.setImmediate(true);
		bean.setBuffered(false);
		return bean;
	}

	@Bean
	@Scope("ui")
	public GridLayout friendsLayout()
	{
		GridLayout bean = new GridLayout(3, 2);
		return bean;
	}

	@Bean
	@Scope("ui")
	public GridLayout peopleLayout()
	{
		GridLayout bean = new GridLayout(2, 2);
		return bean;
	}
}