package com.bleulace.ui.web.profile.status;

import com.bleulace.utils.spec.Specification;

enum StatusUpdateFieldState implements StatusUpdateFieldOps,
		Specification<StatusUpdateField>
{
	//@formatter:off
	DEFAULT(new DefaultOperations(), StatusUpdateField.DEFAULT_FIELD_VALUE), 
	BLANK(new OccupiedOperations(), StatusUpdateField.BLANK_FIELD_VALUE), 
	OCCUPIED(new OccupiedOperations(), null);
	//@formatter:on

	private final String value;
	private final StatusUpdateFieldOps ops;

	private StatusUpdateFieldState(StatusUpdateFieldOps ops, String value)
	{
		this.ops = ops;
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	@Override
	public boolean isSatisfiedBy(StatusUpdateField candidate)
	{
		if (value == null)
		{
			return DEFAULT.not().and(BLANK.not()).isSatisfiedBy(candidate);
		}
		return value.equals(candidate.getValue());
	}

	@Override
	public void onFocus(StatusUpdateField field)
	{
		ops.onFocus(field);
	}

	@Override
	public void onBlur(StatusUpdateField field)
	{
		ops.onBlur(field);
	}

	static StatusUpdateFieldState calculateState(StatusUpdateField field)
	{
		for (StatusUpdateFieldState state : StatusUpdateFieldState.values())
		{
			if (state.isSatisfiedBy(field))
			{
				return state;
			}
		}
		throw new IllegalStateException(
				"StatusUpdateField state could not be determined. This should never happen.");
	}

	static class DefaultOperations extends TransitioningOperations
	{
		DefaultOperations()
		{
			super(BLANK, DEFAULT);
		}
	}

	static class OccupiedOperations extends TransitioningOperations
	{
		OccupiedOperations()
		{
			super(OCCUPIED, DEFAULT);
		}

		@Override
		public void onBlur(StatusUpdateField field)
		{
			if (!field.getFieldState().equals(OCCUPIED))
			{
				super.onBlur(field);
			}
		}
	}
}