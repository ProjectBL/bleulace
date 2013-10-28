package com.bleulace.web.demo.friends;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.model.Account;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

@Component
@Scope("ui")
class FriendsScreen extends CustomComponent
{
	@Autowired
	private Layout friendsLayout;

	@Autowired
	private Layout peopleLayout;

	@PostConstruct
	protected void init()
	{
		HorizontalLayout mid = new HorizontalLayout(friendsLayout, peopleLayout);
		setCompositionRoot(mid);
	}

	void addFriend(Account friend)
	{
		friendsLayout.addComponent(new FriendPolaroid(friend));
	}

	void addPersonYouMightKnow(Account person)
	{
		peopleLayout.addComponent(new FriendPolaroid(person));
	}

	void clearFriends()
	{
		friendsLayout.removeAllComponents();
		peopleLayout.removeAllComponents();
	}
}
