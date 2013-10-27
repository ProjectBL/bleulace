package com.bleulace.web.demo.timebox;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.web.annotation.Presenter;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Calendar;

@Presenter
class TimeBoxPresenter implements CommitHandler
{
	@Qualifier("timeBoxFieldGroup")
	@Autowired
	private BeanFieldGroup<PersistentEvent> fieldGroup;

	@Autowired
	private BeanContainer<String, ParticipantBean> participants;

	@Autowired
	private BeanContainer<String, ParticipantBean> candidates;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private TimeBox timeBox;

	@Autowired
	private Calendar calendar;

	@Autowired
	private transient ApplicationContext ctx;

	void setCurrentEvent(PersistentEvent event)
	{
		Assert.notNull(event);
		fieldGroup.setItemDataSource(event);

		participants.removeAllItems();
		candidates.removeAllItems();

		Set<Account> friends = accountDAO.findOne(
				(String) SecurityUtils.getSubject().getPrincipal())
				.getFriends();

		for (Account friend : friends)
		{
			candidates.addBean(new ParticipantBean(friend));
		}

		for (Entry<Account, EventInvitee> entry : event.getInvitees()
				.entrySet())
		{
			participants.addBean(new ParticipantBean(entry.getKey(), entry
					.getValue().getStatus()));
		}
	}

	void participantAdded(ParticipantBean bean)
	{
		if (bean.getStatus() == null)
		{
			bean.setStatus(RsvpStatus.PENDING);
		}
		participants.addBean(bean);
	}

	void participantRemoved(ParticipantBean bean)
	{
		participants.removeItem(bean.getId());
	}

	void applyClicked()
	{
		try
		{
			fieldGroup.commit();
			PersistentEvent event = fieldGroup.getItemDataSource().getBean();
			String message = "Event ";
			if (event.isNew())
			{
				calendar.addEvent(eventDAO.save(event));
				message += "created successfully.";
			}
			else
			{
				eventDAO.save(event);
				message += "updated successfully.";
			}
			timeBox.showSuccessMessage(message);
			timeBox.close();
		}
		catch (CommitException e)
		{
			timeBox.showWarningMessage("Invalid timebox state.");
		}
	}

	void cancelClicked()
	{
		timeBox.showSuccessMessage("Operation canceled.");
		timeBox.close();
	}

	void deleteClicked()
	{
		calendar.removeEvent(fieldGroup.getItemDataSource().getBean());
		timeBox.showSuccessMessage("Event deleted.");
		timeBox.close();
	}

	void timeBoxClosed()
	{
	}

	@Override
	public void preCommit(CommitEvent commitEvent) throws CommitException
	{
	}

	@Override
	public void postCommit(CommitEvent commitEvent) throws CommitException
	{

		PersistentEvent event = fieldGroup.getItemDataSource().getBean();

		Set<String> eventParticipantIds = new HashSet<String>();

		for (Account account : event.getInvitees().keySet())
		{
			if (!participants.getItemIds().contains(account.getId()))
			{
				event.getInvitees().remove(account);
			}
			eventParticipantIds.add(account.getId());
		}

		for (String id : participants.getItemIds())
		{
			if (!eventParticipantIds.contains(id))
			{
				Account guest = accountDAO.findOne(id);
				Assert.notNull(guest);

				Account host = accountDAO.findOne((String) SecurityUtils
						.getSubject().getPrincipal());
				Assert.notNull(host);

				EventInvitee invitee = new EventInvitee(guest, host);
				event.getInvitees().put(guest, invitee);
			}
		}
	}
}