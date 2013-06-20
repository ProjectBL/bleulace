package com.bleulace.utils;

public class CommandSendAttemptedEvent<T>
{
	private final T payload;

	private final Boolean success;

	public CommandSendAttemptedEvent(T payload, Boolean success)
	{
		this.payload = payload;
		this.success = success;
	}

	public T getPayload()
	{
		return payload;
	}

	public Boolean isSuccess()
	{
		return success;
	}
}