package com.bleulace.domain.crm.ui.profile.field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;

@UIComponent
@Configuration
class ParticipantFieldConfig
{
	private final ApplicationContext CTX = SpringApplicationContext.get();

	private static final Object[] VISIBLE_COLS = new Object[] { "firstName",
			"lastName", "username", "status", "level" };

	@Autowired
	private ParticipantFieldOperations ops;

	@Bean(name = "participantTable")
	public Table participantTable(
			@Qualifier("participantContainer") BeanContainer<String, UserDTODecorator> participantContainer)
	{
		final Table table = new Table();
		table.setContainerDataSource(participantContainer);
		table.setVisibleColumns(VISIBLE_COLS);
		table.setBuffered(false);
		table.setImmediate(true);
		table.setSelectable(true);
		table.setNullSelectionAllowed(true);

		table.addItemClickListener(new ItemClickEvent.ItemClickListener()
		{
			@Override
			public void itemClick(ItemClickEvent event)
			{
				if (event.getButton() == MouseButton.RIGHT)
				{
					// show popup
				}
			}
		});
		return table;
	}

	@Bean(name = "candidateBox")
	public ComboBox candidateComboBox(
			@Qualifier("candidateContainer") BeanContainer<String, UserDTODecorator> candidateContainer)
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

	@Bean(name = "tableDeleteHandler")
	public Handler tableDeleteHandler(
			@Qualifier("participantTable") final Table field)
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
