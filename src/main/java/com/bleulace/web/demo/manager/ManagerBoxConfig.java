package com.bleulace.web.demo.manager;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;

@Configuration
class ManagerBoxConfig
{
	@Bean
	@Scope("ui")
	public Table managerTable(
			@Qualifier("managementParticipants") BeanContainer<String, ManagerBean> container,
			ManagerTableHandler handler)
	{
		Table bean = new Table("", container);
		bean.addActionHandler(handler);
		bean.setPageLength(6);
		bean.setVisibleColumns(new Object[] { "firstName", "lastName", "email",
				"level" });
		bean.setColumnHeader("firstName", "First Name");
		bean.setColumnHeader("lastName", "Last Name");
		bean.setColumnHeader("email", "Email");
		bean.setColumnHeader("level", "Level");
		bean.setSelectable(true);
		return bean;
	}

	@Bean
	@Scope("ui")
	public ComboBox managerComboBox(
			@Qualifier("managementCandidates") BeanContainer<String, ManagerBean> managementCandidates,
			final ManagerBoxPresenter presenter)
	{
		final ComboBox bean = new ComboBox("Assets", managementCandidates);
		bean.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		bean.setItemCaptionPropertyId("name");
		bean.setImmediate(true);
		bean.setBuffered(false);
		bean.addValueChangeListener(new ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				presenter.managerAdded((String) bean.getValue());
			}
		});
		return bean;
	}

	@Bean
	@Scope("ui")
	public Button managerSubmitButton(final ManagerBoxPresenter presenter)
	{
		Button bean = new Button("Submit", new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				presenter.submitClicked();
			}
		});
		bean.setClickShortcut(KeyCode.ENTER);
		return bean;
	}

	@Bean
	@Scope("ui")
	public Button managerCancelButton(final ManagerBoxPresenter presenter)
	{
		Button bean = new Button("Cancel", new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				presenter.cancelClicked();
			}
		});
		bean.setClickShortcut(KeyCode.ESCAPE);
		return bean;
	}

	@Bean
	@Scope("ui")
	public BeanContainer<String, ManagerBean> managementCandidates()
	{
		return makeContainer();
	}

	@Bean
	@Scope("ui")
	public BeanContainer<String, ManagerBean> managementParticipants()
	{
		return makeContainer();
	}

	private BeanContainer<String, ManagerBean> makeContainer()
	{
		BeanContainer<String, ManagerBean> container = new BeanContainer<String, ManagerBean>(
				ManagerBean.class);
		container.setBeanIdResolver(new BeanIdResolver<String, ManagerBean>()
		{
			@Override
			public String getIdForBean(ManagerBean bean)
			{
				return bean.getId();
			}
		});
		return container;
	}
}