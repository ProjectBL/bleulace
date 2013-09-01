package com.bleulace.domain.crm.ui.profile;

import java.util.Date;
import java.util.List;

import com.bleulace.domain.management.model.Event;
import com.bleulace.domain.management.model.QEvent;
import com.bleulace.domain.management.model.QEventInvitee;
import com.bleulace.domain.resource.model.QAbstractChildResource;
import com.bleulace.jpa.config.QueryFactory;
import com.vaadin.data.util.BeanContainer;

class EventSearchContainer extends BeanContainer<String, Event>
{
	private final String accountId;

	EventSearchContainer(String accountId)
	{
		super(Event.class);
		this.accountId = accountId;
		setBeanIdProperty("id");
		refresh();
	}

	private void refresh()
	{
		removeAllItems();
		addAll(getEvents());
	}

	private List<com.bleulace.domain.management.model.Event> getEvents()
	{
		QEvent e = new QEvent("e");
		QAbstractChildResource c = new QAbstractChildResource("c");
		QEventInvitee i = new QEventInvitee("i");
		return QueryFactory
				.from(e)
				.innerJoin(e.invitees, i)
				.innerJoin(e.children, c)
				.where(e.window.end.after(new Date()).and(
						i.guest.id.eq(accountId))).list(e);
	}
}
