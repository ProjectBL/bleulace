package com.bleulace.web.demo.friends;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.model.Account;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("ui")
class FriendsScreen extends CustomComponent
{
	@Autowired
	private ComboBox friendSearchField;

	@Autowired
	private Layout friendsLayout;

	@Autowired
	private Layout peopleLayout;

	@Autowired
	private Iterable<Button> socialButtons;

	@PostConstruct
	protected void init()
	{
		HorizontalLayout buttons = new HorizontalLayout();
		for (Button b : socialButtons)
		{
			buttons.addComponent(b);
		}
		buttons.setSpacing(false);

		HorizontalLayout top = new HorizontalLayout(friendSearchField, buttons);
		top.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);

		HorizontalLayout mid = new HorizontalLayout(friendsLayout, peopleLayout);

		VerticalLayout root = new VerticalLayout(top, mid);
		setCompositionRoot(root);
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
