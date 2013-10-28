package com.bleulace.web.demo.friends;

import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.web.annotation.Presenter;
import com.vaadin.ui.Notification;

@Presenter
public class FriendsPresenter
{
	@Autowired
	private AccountDAO dao;

	@Autowired
	private FriendsScreen screen;

	void accountClicked(Account account)
	{
		Notification.show(account + " clicked!");
	}

	public void setOwnerId(String accountId)
	{
		screen.clearFriends();

		for (Account friend : dao.findRandomFriends(accountId, 6))
		{
			screen.addFriend(friend);
		}

		for (Account person : dao.findPeopleYouMightKnow(accountId, 4))
		{
			screen.addPersonYouMightKnow(person);
		}
	}
}