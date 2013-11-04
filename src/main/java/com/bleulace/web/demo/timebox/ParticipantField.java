package com.bleulace.web.demo.timebox;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.RsvpStatus;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.VerticalLayout;

@Configurable(preConstruction = true)
class ParticipantField extends CustomField<List>
{
	@Autowired
	@Qualifier("friendContainer")
	private JPAContainer<Account> candidates;

	private final BeanContainer<String, EventInvitee> participants = makeParticipantContainer();

	ParticipantField()
	{
	}

	@Override
	protected void setInternalValue(List newValue)
	{
		participants.addAll(newValue);
		super.setInternalValue(newValue);
	}

	@Override
	protected Component initContent()
	{
		VerticalLayout layout = new VerticalLayout(makeComboBox(), makeTable());
		return layout;
	}

	@Override
	public Class<? extends List> getType()
	{
		return List.class;
	}

	private ComboBox makeComboBox()
	{
		final ComboBox bean = new ComboBox("Invite", candidates);
		bean.setBuffered(false);
		bean.setImmediate(true);
		bean.setItemCaptionPropertyId("title");
		bean.addValueChangeListener(new ValueChangeListener()
		{
			@Override
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event)
			{
				String id = (String) event.getProperty().getValue();
				if (id != null && !participants.getItemIds().contains(id))
				{
					EventInvitee bean = new EventInvitee(candidates.getItem(id)
							.getEntity(), RsvpStatus.PENDING);
					participants.addBean(bean);
					getInternalValue().add(bean);
				}
			}
		});
		return bean;
	}

	private Table makeTable()
	{
		final Table table = new Table("Participants", participants);
		table.setPageLength(6);
		table.setVisibleColumns(new Object[] {
				"account.contactInformation.firstName",
				"account.contactInformation.lastName",
				"account.contactInformation.email", "status" });
		table.setColumnHeader("firstName", "First Name");
		table.setColumnHeader("lastName", "Last Name");
		table.setColumnHeader("email", "Email");
		table.setColumnHeader("status", "RSVP");
		table.setImmediate(true);
		table.setSelectable(true);
		table.addShortcutListener(new ShortcutListener("Delete",
				KeyCode.DELETE, null)
		{
			@Override
			public void handleAction(Object sender, Object target)
			{
				String id = (String) table.getValue();
				if (id != null)
				{
					participants.removeItem(id);
				}
			}
		});
		table.setCellStyleGenerator(new CellStyleGenerator()
		{
			@Override
			public String getStyle(Table source, Object itemId,
					Object propertyId)
			{
				RsvpStatus status = participants.getItem(itemId).getBean()
						.getStatus();
				return status == null ? null : status.getStyleName();
			}
		});
		return table;
	}

	private BeanContainer<String, EventInvitee> makeParticipantContainer()
	{
		BeanContainer<String, EventInvitee> container = new BeanContainer<String, EventInvitee>(
				EventInvitee.class);
		container
				.addNestedContainerProperty("account.contactInformation.firstName");
		container
				.addNestedContainerProperty("account.contactInformation.lastName");
		container
				.addNestedContainerProperty("account.contactInformation.email");
		container.setBeanIdResolver(new BeanIdResolver<String, EventInvitee>()
		{
			@Override
			public String getIdForBean(EventInvitee bean)
			{
				return bean.getAccount().getId();
			}
		});
		return container;
	}
}