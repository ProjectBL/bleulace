// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.domain.crm.command;

import com.bleulace.domain.crm.command.GroupMembershipCommand;
import com.bleulace.domain.crm.model.GroupMembershipAction;
import java.util.Set;

privileged aspect GroupMembershipCommand_Roo_JavaBean {
    
    public String GroupMembershipCommand.getId() {
        return this.id;
    }
    
    public Set<String> GroupMembershipCommand.getAccountIds() {
        return this.accountIds;
    }
    
    public void GroupMembershipCommand.setAccountIds(Set<String> accountIds) {
        this.accountIds = accountIds;
    }
    
    public GroupMembershipAction GroupMembershipCommand.getAction() {
        return this.action;
    }
    
}
