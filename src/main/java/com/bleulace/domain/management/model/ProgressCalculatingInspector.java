package com.bleulace.domain.management.model;

import org.springframework.roo.addon.tostring.RooToString;

import com.bleulace.domain.resource.model.Resource;
import com.bleulace.domain.resource.model.ResourceInspector;

@RooToString
class ProgressCalculatingInspector implements ResourceInspector
{
	private int completed = 0;
	private int total = 0;

	@Override
	public void inspect(Resource resource)
	{
		if (shouldAddToTotal(resource))
		{
			total++;
			if (isComplete(resource))
			{
				completed++;
			}
		}
	}

	private boolean shouldAddToTotal(Resource resource)
	{
		return resource instanceof Task;
	}

	private boolean isComplete(Resource resource)
	{
		return ((Task) resource).getDateCompleted() != null;
	}

	public Progress getProgress()
	{
		return new ProgressValue(completed, total);
	}
}