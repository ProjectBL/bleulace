package com.bleulace.domain.management.model;

import org.springframework.roo.addon.tostring.RooToString;

@RooToString
class ProgressCalculatingVisitor implements ManageableResourceVisitor
{
	private int completed = 0;
	private int total = 0;

	@Override
	public void visit(ManageableResource resource)
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

	private boolean shouldAddToTotal(ManageableResource resource)
	{
		return resource.isLeaf();
	}

	private boolean isComplete(ManageableResource resource)
	{
		if (resource instanceof Task)
		{
			return ((Task) resource).isComplete();
		}
		return false;
	}

	public Progress getProgress()
	{
		return new ProgressValue(completed, total);
	}
}