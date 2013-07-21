package com.bleulace.mgt.presentation;

public abstract class ResourceDTO
{
	private String id;
	private String title;
	private boolean complete;

	public ResourceDTO()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public boolean isComplete()
	{
		return complete;
	}

	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}

}