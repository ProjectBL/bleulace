package com.bleulace.domain.crm.ui.profile;

import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.crm.presentation.UserDTORsvpDecorator;
import com.bleulace.domain.crm.presentation.UserFinder;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.domain.management.presentation.EventDTO;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@Configurable(preConstruction = true)
@SuppressWarnings({ "unchecked", "rawtypes" })
class GuestListDisplay extends CustomField<Set> implements Handler
{
	@Autowired
	private UserFinder finder;

	private final BeanContainer<String, UserDTORsvpDecorator> inviteeContainer = makeInviteeContainer();

	private final BeanContainer<String, UserDTO> userContainer = makeUserContainer();

	private final Action backspacePressed = new ShortcutAction(
			"Remove Invitee", KeyCode.BACKSPACE, new int[] {});

	private final Table table = makeTable();
	private final ComboBox comboBox = makeComboBox();

	private static final Object[] VISIBLE_COLS = new Object[] { "firstName",
			"lastName", "username", "status" };

	GuestListDisplay(String operatorId, EventDTO event)
	{
		Assert.noNullElements(new Object[] { operatorId, event });
		setImmediate(true);
		setBuffered(false);
		Set<UserDTO> dtos = new HashSet<UserDTO>(findAllCandidates());
		for (Entry<UserDTO, RsvpStatus> entry : event.getInvitees().entrySet())
		{
			dtos.remove(entry.getKey());
			dtos.add(new UserDTORsvpDecorator(entry.getKey(), entry.getValue()));
		}
		userContainer.addAll(dtos);
	}

	@Override
	protected Component initContent()
	{
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(comboBox);
		layout.addComponent(table);
		return layout;
	}

	@Override
	public Action[] getActions(Object target, Object sender)
	{
		return new Action[] { backspacePressed };
	}

	@Override
	public void handleAction(Action action, Object sender, Object target)
	{
		Object id = table.getValue();
		if (id != null)
		{
			BeanItem<UserDTORsvpDecorator> item = inviteeContainer.getItem(id);
			if (item != null)
			{
				removeInvitee(item.getBean());
			}
		}
	}

	@Override
	public Class<? extends Set> getType()
	{
		return Set.class;
	}

	@Override
	public Set getInternalValue()
	{
		System.out.println(inviteeContainer.getItemIds().size());
		return new HashSet(inviteeContainer.getItemIds());
	}

	@Override
	public void setInternalValue(Set inviteeIds)
			throws com.vaadin.data.Property.ReadOnlyException,
			ConversionException
	{
		for (String id : inviteeContainer.getItemIds())
		{
			removeInvitee(inviteeContainer.getItem(id).getBean());
		}

		for (String id : (Set<String>) inviteeIds)
		{
			addInvitee(userContainer.getItem(id).getBean());
		}
	}

	protected List<UserDTO> findAllCandidates()
	{
		return finder.findAll();
	}

	private Table makeTable()
	{
		Table table = new Table();
		table.setContainerDataSource(inviteeContainer);
		table.setVisibleColumns(VISIBLE_COLS);
		table.setBuffered(false);
		table.setImmediate(true);
		table.setSelectable(true);
		table.setNullSelectionAllowed(true);
		return table;
	}

	private ComboBox makeComboBox()
	{
		ComboBox comboBox = new ComboBox();
		comboBox.setContainerDataSource(userContainer);
		comboBox.setItemCaptionPropertyId("name");
		comboBox.setImmediate(true);
		comboBox.setBuffered(false);
		comboBox.addValueChangeListener(new AddInviteeListener());
		return comboBox;
	}

	private void removeInvitee(UserDTO dto)
	{
		userContainer.addBean(dto);
		inviteeContainer.removeItem(dto.getId());
	}

	private void addInvitee(UserDTO dto)
	{
		Assert.notNull(dto.getId());
		if (!(dto instanceof UserDTORsvpDecorator))
		{
			dto = new UserDTORsvpDecorator(dto, RsvpStatus.PENDING);
		}
		inviteeContainer.addBean((UserDTORsvpDecorator) dto);
	}

	private BeanContainer<String, UserDTO> makeUserContainer()
	{
		BeanContainer<String, UserDTO> container = new BeanContainer<String, UserDTO>(
				UserDTO.class);
		container.setBeanIdProperty("id");
		return container;
	}

	private BeanContainer<String, UserDTORsvpDecorator> makeInviteeContainer()
	{
		BeanContainer<String, UserDTORsvpDecorator> container = new BeanContainer<String, UserDTORsvpDecorator>(
				UserDTORsvpDecorator.class);
		container.setBeanIdProperty("id");
		return container;
	}

	private class AddInviteeListener implements ValueChangeListener
	{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event)
		{
			BeanItem<UserDTO> item = userContainer.getItem(comboBox.getValue());
			if (item != null)
			{
				addInvitee(item.getBean());
			}
		}
	}
}
