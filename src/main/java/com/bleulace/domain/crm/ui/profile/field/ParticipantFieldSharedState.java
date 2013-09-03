package com.bleulace.domain.crm.ui.profile.field;

import java.util.Map;
import java.util.Set;

import com.bleulace.domain.management.model.ManagementLevel;
import com.vaadin.data.util.BeanContainer;

interface ParticipantFieldSharedState
{
	public Set<String> getParticipantIds();

	public void setParticipantIds(Set<String> ids);

	public Map<String, ManagementLevel> getManagerIds();

	public void setManagerIds(Map<String, ManagementLevel> map);

	public BeanContainer<String, UserDTODecorator> getParticipantContainer();

	public BeanContainer<String, UserDTODecorator> getCandidateContainer();
}