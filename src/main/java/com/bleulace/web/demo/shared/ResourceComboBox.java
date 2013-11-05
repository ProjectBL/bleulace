package com.bleulace.web.demo.shared;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.resource.model.AbstractResource;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomField;

@Lazy
@Component
@Scope("prototype")
class ResourceComboBox extends CustomField<AbstractResource> implements
		FactoryBean<CustomField<?>>,
		com.vaadin.data.Property.ValueChangeListener
{
	@Autowired
	@Qualifier("resourceContainer")
	private JPAContainer<AbstractResource> container;

	private ComboBox box;

	@PostConstruct
	protected void init()
	{
		box = makeComboBox();
	}

	@Override
	public CustomField<?> getObject() throws Exception
	{
		return this;
	}

	@Override
	public Class<?> getObjectType()
	{
		return CustomField.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}

	@Override
	protected com.vaadin.ui.Component initContent()
	{
		return box;
	}

	@Override
	protected void setInternalValue(AbstractResource newValue)
	{
		if (newValue != null)
		{
			box.setValue(newValue.getId());
		}
		super.setInternalValue(newValue);
	}

	@Override
	public Class<? extends AbstractResource> getType()
	{
		return AbstractResource.class;
	}

	private ComboBox makeComboBox()
	{
		ComboBox bean = new ComboBox();
		bean.setContainerDataSource(container);
		bean.setItemCaptionPropertyId("title");
		bean.setCaption("parent");
		bean.setImmediate(true);
		bean.addValueChangeListener(new Property.ValueChangeListener()
		{
			@Override
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event)
			{
				Object id = event.getProperty().getValue();
				setInternalValue(id == null ? null : container.getItem(id)
						.getEntity());
			}
		});
		return bean;
	}
}
