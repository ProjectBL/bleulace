package com.bleulace.web.demo.timebox;

import java.util.HashSet;
import java.util.List;
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
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

class TimeBoxPresenter implements CommitHandler
{
	@Qualifier("timeBoxFieldGroup")
	@Autowired
	private BeanFieldGroup<PersistentEvent> fieldGroup;

	@Autowired
	@Qualifier("eventParticipants")
	private BeanContainer<String, ParticipantBean> participants;

	@Autowired
	@Qualifier("eventCandidates")
	private BeanContainer<String, ParticipantBean> candidates;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private ResourceDAO resourceDAO;

	@Autowired
	private EventDAO eventDAO;

	private TimeBox timeBox;

	private final Calendar calendar;

	@Autowired
	private ManagerBox managerBox;

	@Autowired
	private transient ApplicationContext ctx;

	TimeBoxPresenter(Calendar calendar)
	{
		this.calendar = calendar;
	}

	void setTimeBox(TimeBox timeBox)
	{
		this.timeBox = timeBox;
	}

	PersistentEvent getCurrentEvent()
	{
		return fieldGroup.getItemDataSource().getBean();
	}

	void setCurrentEvent(PersistentEvent event)
	{
		Assert.notNull(event);
		fieldGroup.setItemDataSource(event);

		participants.removeAllItems();
		candidates.removeAllItems();

		List<Account> friends = accountDAO.findOne(
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
		if (resourceDAO.findManagerIds(getCurrentEvent().getId(),
				ManagementLevel.OWN).contains(bean.getId()))
		{
			Notification.show("Can not remove resource owner.",
					Type.WARNING_MESSAGE);
			return;
		}
		participants.removeItem(bean.getId());
	}

	void applyClicked()
	{
		try
		{
			fieldGroup.commit();
			PersistentEvent event = fieldGroup.getItemDataSource().getBean();
			calendar.addEvent(getCurrentEvent());
			timeBox.showSuccessMessage("Event "
					+ (event.isNew() ? "created successfully."
							: "updated successfully."));
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
		calendar.removeEvent(getCurrentEvent());
		timeBox.showSuccessMessage("Event deleted.");
		timeBox.close();
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

	void timeBoxClosed()
	{
		managerBox.close();
	}
}