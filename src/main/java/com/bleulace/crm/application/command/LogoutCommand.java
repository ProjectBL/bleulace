package com.bleulace.crm.application.command;

/**
 * A command to log a user out of the system.
 * 
 * There are params because the (previously authenticated) subject executing the
 * command will be determined by the system at runtime.
 * 
 * If the subject is not authenticated, do nothing.
 * 
 * @author Arleigh Dickerson
 * 
 */
public class LogoutCommand
{
}