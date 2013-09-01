package com.bleulace.domain.crm.ui.profile;

import java.util.Map;

import com.bleulace.domain.crm.presentation.UserDTO;

interface ProfileView
{
	public void setInfo(UserDTO dto);

	public void setProjects(Map<String, String> idTitleMap);

	public void setEvents(Map<String, String> idTitleMap);

	public void refreshCalendar();
}