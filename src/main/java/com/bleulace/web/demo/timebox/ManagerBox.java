package com.bleulace.web.demo.timebox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.domain.crm.model.Account;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Configurable(preConstruction = true)
class ManagerBox extends Window
{
	@Autowired
	@Qualifier("friendContainer")
	private JPAContainer<Account> candidates;

	private final BeanContainer<String, ManagerBean> managers = makeContainer();

	private final ManagerBoxPresenter presenter;

	ManagerBox(final ManagerBoxPresenter presenter)
	{
		this.presenter = presenter;

		FormLayout form = new FormLayout(makeComboBox(), makeTable());

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(makeButton("Cancel", KeyCode.ESCAPE,
				new Button.ClickListener()
				{

					@Override
					public void buttonClick(ClickEvent event)
					{
						presenter.cancelClicked();
					}
				}));
		buttons.addComponent(makeButton("Submit", KeyCode.ESCAPE,
				new Button.ClickListener()
				{

					@Override
					public void buttonClick(ClickEvent event)
					{
						presenter.submitClicked();
					}
				}));
		buttons.setSpacing(false);

		VerticalLayout content = new VerticalLayout(form, buttons);
		content.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
		setContent(content);
	}

	BeanContainer<String, ManagerBean> getManagers()
	{
		return managers;
	}

	JPAContainer<Account> getCandidates()
	{
		return candidates;
	}

	private ComboBox makeComboBox()
	{
		final ComboBox bean = new ComboBox("Assets", candidates);
		bean.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		bean.setItemCaptionPropertyId("title");
		bean.setImmediate(true);
		bean.setBuffered(false);
		bean.addValueChangeListener(new ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				String id = (String) bean.getValue();
				if (id == null)
				{
					return;
				}
				EntityItem<Account> item = candidates.getItem(id);
				if (item == null)
				{
					return;
				}
				presenter.managerAdded(item.getEntity());
			}
		});
		return bean;
	}

	private Table makeTable()
	{
		final Table bean = new Table("Managers", managers);
		bean.addActionHandler(new ManagerTableRightClickHandler(presenter));
		bean.setPageLength(6);
		bean.setVisibleColumns(new Object[] { "firstName", "lastName", "email",
				"level" });
		bean.setColumnHeader("firstName", "First Name");
		bean.setColumnHeader("lastName", "Last Name");
		bean.setColumnHeader("email", "Email");
		bean.setColumnHeader("level", "Level");
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
					presenter.managerUpdated(id, null);
				}
			}
		});

		return bean;
	}

	private BeanContainer<String, ManagerBean> makeContainer()
	{
		BeanContainer<String, ManagerBean> container = new BeanContainer<String, ManagerBean>(
				ManagerBean.class);
		container.setBeanIdProperty("id");
		return container;
	}

	private Button makeButton(String caption, int keyCode,
			Button.ClickListener listener)
	{
		Button b = new Button(caption, listener);
		b.setClickShortcut(keyCode);
		return b;
	}
}
