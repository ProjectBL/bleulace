// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.mgt.domain.event;

import com.bleulace.mgt.domain.ManagementLevel;
import com.bleulace.mgt.domain.event.ManagerAddedEvent;

privileged aspect ManagerAddedEvent_Roo_JavaBean {
    
    public String ManagerAddedEvent.getAccountId() {
        return this.accountId;
    }
    
    public ManagementLevel ManagerAddedEvent.getLevel() {
        return this.level;
    }
    
}