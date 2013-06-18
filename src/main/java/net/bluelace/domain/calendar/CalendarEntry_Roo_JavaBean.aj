// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.bluelace.domain.calendar;

import java.util.List;
import net.bluelace.domain.account.Account;
import net.bluelace.domain.calendar.CalendarEntry;
import org.joda.time.LocalDateTime;

privileged aspect CalendarEntry_Roo_JavaBean {
    
    public Account CalendarEntry.getOwner() {
        return this.owner;
    }
    
    public void CalendarEntry.setOwner(Account owner) {
        this.owner = owner;
    }
    
    public List<Account> CalendarEntry.getInvitees() {
        return this.invitees;
    }
    
    public void CalendarEntry.setInvitees(List<Account> invitees) {
        this.invitees = invitees;
    }
    
    public String CalendarEntry.getTitle() {
        return this.title;
    }
    
    public void CalendarEntry.setTitle(String title) {
        this.title = title;
    }
    
    public LocalDateTime CalendarEntry.getDate() {
        return this.date;
    }
    
    public void CalendarEntry.setDate(LocalDateTime date) {
        this.date = date;
    }
    
}