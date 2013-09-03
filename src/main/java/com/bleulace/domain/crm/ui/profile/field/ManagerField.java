package com.bleulace.domain.crm.ui.profile.field;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomField;

@UIComponent
class ManagerField extends CustomField<Map>
{
	@Autowired
	private ParticipantFieldSharedState state;

	@Override
	protected Component initContent()
	{
		return new CssLayout();
	}

	@Override
	public Class<? extends Map> getType()
	{
		return Map.class;
	}

	@Override
	protected Map getInternalValue()
	{
		return state.getManagerIds();
	}

	@Override
	protected void setInternalValue(Map newValue)
	{
		state.setManagerIds(newValue);
	}
}