// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.mgt.application.command;

import com.bleulace.mgt.application.command.InviteGuestsCommand;
import java.util.Set;

privileged aspect InviteGuestsCommand_Roo_JavaBean {
    
    public String InviteGuestsCommand.getId() {
        return this.id;
    }
    
    public Set<String> InviteGuestsCommand.getGuestIds() {
        return this.guestIds;
    }
    
}
