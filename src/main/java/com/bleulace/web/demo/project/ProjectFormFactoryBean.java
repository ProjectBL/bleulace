package com.bleulace.web.demo.project;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.management.model.Project;
import com.bleulace.web.demo.timebox.ManagerField;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

@Lazy
@Scope("prototype")
@org.springframework.stereotype.Component("projectForm")
class ProjectFormFactoryBean implements FactoryBean<Component>
{
	private final FieldGroup fg;

	@Autowired
	@Qualifier("resourceComboBox")
	private CustomField<?> parentBox;

	ProjectFormFactoryBean(FieldGroup fg)
	{
		this.fg = fg;
	}

	ProjectFormFactoryBean()
	{
		this(new FieldGroup(new BeanItem<Project>(new Project())));
	}

	@Override
	public Component getObject() throws Exception
	{
		fg.bind(parentBox, "parent");
		TextField title = fg.buildAndBind("Title", "title", TextField.class);
		ManagerField managerField = new ManagerField();
		fg.bind(managerField, "managers");

		FormLayout layout = new FormLayout(parentBox, title, managerField);
		return layout;
	}

	@Override
	public Class<?> getObjectType()
	{
		return Component.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}
}