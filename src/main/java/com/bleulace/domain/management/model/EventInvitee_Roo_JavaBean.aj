// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.domain.management.model;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.RsvpStatus;

privileged aspect EventInvitee_Roo_JavaBean {
    
    public Account EventInvitee.getAccount() {
        return this.account;
    }
    
    public RsvpStatus EventInvitee.getStatus() {
        return this.status;
    }
    
}
