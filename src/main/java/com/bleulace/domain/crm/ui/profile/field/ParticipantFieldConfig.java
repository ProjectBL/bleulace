package com.bleulace.domain.crm.ui.profile.field;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vaadin.peter.contextmenu.ContextMenu;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItem;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItemClickEvent;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItemClickListener;

import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;

@UIComponent
@Configuration
class ParticipantFieldConfig
{
	private static final Object[] VISIBLE_COLS = new Object[] { "firstName",
			"lastName", "username", "status", "level" };

	@Bean(name = "participantTable")
	public Table participantTable(
			@Qualifier("participantContainer") final BeanContainer<String, UserDTODecorator> participantContainer)
	{
		final Table table = new Table();
		table.setContainerDataSource(participantContainer);
		table.setVisibleColumns(VISIBLE_COLS);
		table.setBuffered(false);
		table.setImmediate(true);
		table.setSelectable(true);
		table.setNullSelectionAllowed(true);
		return table;
	}

	@Bean(name = "candidateBox")
	public ComboBox candidateComboBox(
			@Qualifier("candidateContainer") BeanContainer<String, UserDTODecorator> candidateContainer,
			final ParticipantFieldOperations ops)
	{
		final ComboBox comboBox = new ComboBox();
		comboBox.setContainerDataSource(candidateContainer);
		comboBox.setItemCaptionPropertyId("name");
		comboBox.setImmediate(true);
		comboBox.setBuffered(false);
		comboBox.addValueChangeListener(new ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				String id = (String) comboBox.getValue();
				if (id != null)
				{
					ops.guestInvited(id);
				}
			}
		});
		return comboBox;
	}

	@Bean(name = "managerContextMenu")
	public ContextMenu tableContextMenu(final Table table,
			final ParticipantFieldOperations ops)
	{
		ContextMenu menu = new ContextMenu();
		menu.setAsTableContextMenu(table);
		menu.setOpenAutomatically(true);

		ContextMenuItem remove = menu.addItem("Remove Invitation");
		remove.addItemClickListener(new ContextMenuItemClickListener()
		{
			@Override
			public void contextMenuItemClicked(ContextMenuItemClickEvent event)
			{
				if (table.getValue() != null)
				{
					ops.guestRemoved((String) table.getValue());
				}
			}
		});

		List<String> options = new ArrayList<String>();
		options.add("None");
		for (ManagementLevel level : ManagementLevel.values())
		{
			options.add(level.toString());
		}

		final ContextMenuItem levelChange = menu.addItem("Change Access Level");
		for (final String option : options)
		{
			ContextMenuItem level = levelChange.addItem(option);
			level.addItemClickListener(new ContextMenuItemClickListener()
			{
				@Override
				public void contextMenuItemClicked(
						ContextMenuItemClickEvent event)
				{
					String id = (String) table.getValue();
					if (id != null)
					{
						ManagementLevel l = null;
						try
						{
							l = ManagementLevel.valueOf(option);
						}
						catch (Exception e)
						{
						}
						ops.managerAdded(id, l);
					}
				}
			});
		}

		return menu;
	}

	@Bean(name = "tableDeleteKeyHandler")
	public Handler tableDeleteHandler(
			@Qualifier("participantTable") final Table field,
			final ParticipantFieldOperations ops)
	{
		return new Handler()
		{
			private final ShortcutAction backspacePressed = new ShortcutAction(
					"Remove Invitee", KeyCode.BACKSPACE, new int[] {});

			@Override
			public Action[] getActions(Object target, Object sender)
			{
				return new Action[] { backspacePressed };
			}

			@Override
			public void handleAction(com.vaadin.event.Action action,
					Object sender, Object target)
			{
				String id = (String) field.getValue();
				if (id != null)
				{
					ops.guestRemoved(id);
				}
			}
		};
	}

	@Bean(name = "participantContainer")
	public BeanContainer<String, UserDTODecorator> participantContainer()
	{
		BeanContainer<String, UserDTODecorator> container = makeUserDTODecoratorContainer();
		return container;
	}

	@Bean(name = "candidateContainer")
	public BeanContainer<String, UserDTODecorator> candidateContainer()
	{
		BeanContainer<String, UserDTODecorator> container = makeUserDTODecoratorContainer();
		return container;
	}

	private BeanContainer<String, UserDTODecorator> makeUserDTODecoratorContainer()
	{
		BeanContainer<String, UserDTODecorator> container = new BeanContainer<String, UserDTODecorator>(
				UserDTODecorator.class);
		container.setBeanIdProperty("id");
		return container;
	}
}
