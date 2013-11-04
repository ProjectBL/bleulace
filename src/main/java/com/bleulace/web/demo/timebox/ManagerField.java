package com.bleulace.web.demo.timebox;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.domain.management.model.ManagementLevel;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Configurable(preConstruction = true)
class ManagerField extends CustomField<List> implements Button.ClickListener
{
	@Autowired
	@Qualifier("friendContainer")
	private JPAContainer<Account> candidates;

	private BeanContainer<String, ManagementAssignment> managers = makeContainer();

	ManagerField()
	{
		setBuffered(true);
	}

	@Override
	protected Component initContent()
	{
		return new Button("Managers", this);
	}

	@Override
	public Class<? extends List> getType()
	{
		return List.class;
	}

	@Override
	protected void setInternalValue(List newValue)
	{
		managers.addAll(newValue);
		super.setInternalValue(newValue);
	}

	private ComboBox makeComboBox()
	{
		final ComboBox bean = new ComboBox("Assets", candidates);
		bean.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		bean.setItemCaptionPropertyId("title");
		bean.setImmediate(true);
		bean.addValueChangeListener(new ValueChangeListener()
		{
			@Override
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event)
			{
				String id = (String) event.getProperty().getValue();
				if (id != null && !managers.containsId(id))
				{
					ManagementAssignment bean = new ManagementAssignment(
							candidates.getItem(id).getEntity(),
							ManagementLevel.LOOP);
					managers.addBean(bean);
					getInternalValue().add(bean);
				}
			}
		});
		return bean;
	}

	private Table makeTable()
	{
		final Table bean = new Table("Managers", managers);
		bean.setPageLength(6);
		bean.setVisibleColumns(new Object[] {
				"account.contactInformation.firstName",
				"account.contactInformation.lastName",
				"account.contactInformation.email", "level" });
		bean.setImmediate(true);
		bean.setSelectable(true);

		bean.addShortcutListener(new ShortcutListener("Delete", KeyCode.DELETE,
				null)
		{
			@Override
			public void handleAction(Object sender, Object target)
			{
				String id = (String) bean.getValue();
				if (id != null)
				{
				}
			}
		});

		return bean;
	}

	private BeanContainer<String, ManagementAssignment> makeContainer()
	{
		BeanContainer<String, ManagementAssignment> container = new BeanContainer<String, ManagementAssignment>(
				ManagementAssignment.class);
		container
				.addNestedContainerProperty("account.contactInformation.firstName");
		container
				.addNestedContainerProperty("account.contactInformation.lastName");
		container
				.addNestedContainerProperty("account.contactInformation.email");
		container
				.setBeanIdResolver(new BeanIdResolver<String, ManagementAssignment>()
				{
					@Override
					public String getIdForBean(ManagementAssignment bean)
					{
						return bean.getAccount().getId();
					}
				});
		return container;
	}

	private Button makeButton(String caption, int keyCode,
			Button.ClickListener listener)
	{
		Button b = new Button(caption, listener);
		b.setClickShortcut(keyCode);
		return b;
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		showContent();
	}

	private void showContent()
	{
		final Window window = new Window();
		FormLayout form = new FormLayout(makeComboBox(), makeTable());
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(makeButton("Cancel", KeyCode.ESCAPE,
				new Button.ClickListener()
				{

					@Override
					public void buttonClick(ClickEvent event)
					{
						discard();
						window.close();
					}
				}));
		buttons.addComponent(makeButton("Submit", KeyCode.ESCAPE,
				new Button.ClickListener()
				{

					@Override
					public void buttonClick(ClickEvent event)
					{
						commit();
						window.close();
					}
				}));
		buttons.setSpacing(false);
		VerticalLayout content = new VerticalLayout(form, buttons);
		content.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
		window.setContent(content);
		UI.getCurrent().addWindow(window);
	}
}