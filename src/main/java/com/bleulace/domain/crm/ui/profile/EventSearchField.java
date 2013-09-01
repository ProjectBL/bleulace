package com.bleulace.domain.crm.ui.profile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.axonframework.domain.GenericDomainEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.domain.crm.ui.profile.ResourceListComponent.ResourceSelectedEvent;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;

@UIComponent
class EventSearchField extends CustomComponent implements ValueChangeListener
{
	@Autowired
	@Qualifier("uiBus")
	private EventBus uiBus;

	private final JPAContainer<?> container = makeContainer();

	private final ComboBox comboBox = makeComboBox();

	EventSearchField()
	{
		setCompositionRoot(comboBox);
	}

	@Override
	public void valueChange(ValueChangeEvent event)
	{
		uiBus.publish(GenericDomainEventMessage
				.asEventMessage(new ResourceSelectedEvent((String) event
						.getProperty().getValue(), "event")));
	}

	void setAccountId(String accountId)
	{
		if (accountId != null)
		{
			container.removeAllContainerFilters();
			container.addContainerFilter("invitees.guest.id", accountId, false,
					false);
		}
	}

	private JPAContainer<?> makeContainer()
	{
		JPAContainer<?> container = JPAContainerFactory.makeNonCachedReadOnly(
				com.bleulace.domain.management.model.Event.class,
				EntityManagerReference.get());
		container.setQueryModifierDelegate(new DefaultQueryModifierDelegate()
		{
			@Override
			public void queryHasBeenBuilt(CriteriaBuilder criteriaBuilder,
					CriteriaQuery<?> query)
			{
				query.distinct(true);
			}
		});
		return container;
	}

	private ComboBox makeComboBox()
	{
		ComboBox comboBox = new ComboBox(null, container);
		comboBox.setBuffered(false);
		comboBox.setImmediate(true);
		comboBox.addValueChangeListener(this);
		return comboBox;
	}
}