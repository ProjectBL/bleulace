// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.mgt.domain.event;

import java.util.Date;

privileged aspect EventCreatedEvent_Roo_JavaBean {
    
    public Date EventCreatedEvent.getStart() {
        return this.start;
    }
    
    public void EventCreatedEvent.setStart(Date start) {
        this.start = start;
    }
    
    public Date EventCreatedEvent.getEnd() {
        return this.end;
    }
    
    public void EventCreatedEvent.setEnd(Date end) {
        this.end = end;
    }
    
}