// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.domain.management.event;

import com.bleulace.domain.management.event.ManagerAssignedEvent;
import com.bleulace.domain.management.model.ManagementRole;

privileged aspect ManagerAssignedEvent_Roo_JavaBean {
    
    public String ManagerAssignedEvent.getAssignerId() {
        return this.assignerId;
    }
    
    public void ManagerAssignedEvent.setAssignerId(String assignerId) {
        this.assignerId = assignerId;
    }
    
    public String ManagerAssignedEvent.getAssigneeId() {
        return this.assigneeId;
    }
    
    public void ManagerAssignedEvent.setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }
    
    public ManagementRole ManagerAssignedEvent.getRole() {
        return this.role;
    }
    
    public void ManagerAssignedEvent.setRole(ManagementRole role) {
        this.role = role;
    }
    
}