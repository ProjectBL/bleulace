package com.bleulace.ui.web.profile.status;


class TransitioningOperations implements StatusUpdateFieldOps
{
	private final StatusUpdateFieldState stateOnFocus;
	private final StatusUpdateFieldState stateOnBlur;

	TransitioningOperations(StatusUpdateFieldState stateOnFocus,
			StatusUpdateFieldState stateOnBlur)
	{
		this.stateOnFocus = stateOnFocus;
		this.stateOnBlur = stateOnBlur;
	}

	@Override
	public void onFocus(StatusUpdateField field)
	{
		field.setFieldState(stateOnFocus);
	}

	@Override
	public void onBlur(StatusUpdateField field)
	{
		field.setFieldState(stateOnBlur);
	}
}
