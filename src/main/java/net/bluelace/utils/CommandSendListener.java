package net.bluelace.utils;

public interface CommandSendListener<P>
{
	public void onCommandSendAttempted(CommandSendAttemptedEvent<P> event);
}