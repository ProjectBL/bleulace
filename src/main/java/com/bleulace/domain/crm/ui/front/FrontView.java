package com.bleulace.domain.crm.ui.front;

import com.bleulace.domain.crm.presentation.UserDTO;

interface FrontView
{
	void setEnabled(boolean enabled);

	void showLoginFailure();

	void showLoginSuccess(UserDTO dto);

	void clearLoginParams();
}