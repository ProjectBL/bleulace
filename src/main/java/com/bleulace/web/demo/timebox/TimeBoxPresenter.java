package com.bleulace.web.demo.timebox;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

@Configurable(preConstruction = true)
class TimeBoxPresenter implements CommitHandler
{
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private ResourceDAO resourceDAO;

	private TimeBox view;

	private final Calendar calendar;

	private final BeanFieldGroup<PersistentEvent> fieldGroup = makeFieldGroup();

	TimeBoxPresenter(PersistentEvent event, Calendar calendar)
	{
		this.calendar = calendar;
		fieldGroup.setItemDataSource(event);
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
			if (!view.getParticipants().getItemIds().contains(account.getId()))
			{
				event.getInvitees().remove(account);
			}
			eventParticipantIds.add(account.getId());
		}

		for (String id : view.getParticipants().getItemIds())
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

	PersistentEvent getCurrentEvent()
	{
		return fieldGroup.getItemDataSource().getBean();
	}

	BeanFieldGroup<PersistentEvent> getFieldGroup()
	{
		return fieldGroup;
	}

	void setView(TimeBox view)
	{
		this.view = view;
		for (Entry<Account, EventInvitee> entry : getCurrentEvent()
				.getInvitees().entrySet())
		{
			view.getParticipants().addBean(
					new ParticipantBean(entry.getKey(), entry.getValue()
							.getStatus()));
		}
	}

	void participantAdded(ParticipantBean bean)
	{
		if (bean.getStatus() == null)
		{
			bean.setStatus(RsvpStatus.PENDING);
		}
		view.getParticipants().addBean(bean);
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
		view.getParticipants().removeItem(bean.getId());
	}

	void managersClicked()
	{
		Window w = (Window) ctx.getBean("managerBox", getCurrentEvent());
		UI.getCurrent().addWindow(w);
	}

	void cancelClicked()
	{
		view.showSuccessMessage("Operation canceled.");
		view.close();
	}

	void deleteClicked()
	{
		calendar.removeEvent(getCurrentEvent());
		view.showSuccessMessage("Event deleted.");
		view.close();
	}

	void applyClicked()
	{
		try
		{
			fieldGroup.commit();
			PersistentEvent event = fieldGroup.getItemDataSource().getBean();
			calendar.addEvent(getCurrentEvent());
			view.showSuccessMessage("Event "
					+ (event.isNew() ? "created successfully."
							: "updated successfully."));
			view.close();
		}
		catch (CommitException e)
		{
			view.showWarningMessage("Invalid timebox state.");
		}
	}

	void timeBoxClosed()
	{
	}

	private BeanFieldGroup<PersistentEvent> makeFieldGroup()
	{
		BeanFieldGroup<PersistentEvent> bean = new BeanFieldGroup<PersistentEvent>(
				PersistentEvent.class);
		bean.addCommitHandler(this);
		return bean;
	}
}