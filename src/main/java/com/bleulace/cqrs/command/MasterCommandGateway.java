package com.bleulace.cqrs.command;

import com.bleulace.crm.application.command.CrmCommandGateway;
import com.bleulace.mgt.application.command.MgtCommandGateway;

public interface MasterCommandGateway extends CrmCommandGateway,
		MgtCommandGateway
{
}