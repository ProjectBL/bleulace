package com.bleulace.domain.crm.ui.profile.field;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomField;

@UIComponent
class InviteeField extends CustomField<Set>
{
	@Autowired
	private ParticipantFieldSharedState state;

	@Override
	protected Component initContent()
	{
		return new CssLayout();
	}

	@Override
	public Class<? extends Set> getType()
	{
		return Set.class;
	}

	@Override
	protected Set getInternalValue()
	{
		return state.getParticipantIds();
	}

	@Override
	protected void setInternalValue(Set newValue)
	{
		state.setParticipantIds(newValue);
	}
}
