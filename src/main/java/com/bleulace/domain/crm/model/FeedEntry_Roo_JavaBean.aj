// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.domain.crm.model;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.FeedEntry;
import com.bleulace.domain.crm.model.FeedEntryScenario;
import java.util.List;
import org.joda.time.DateTime;

privileged aspect FeedEntry_Roo_JavaBean {
    
    public String FeedEntry.getId() {
        return this.id;
    }
    
    public Class<?> FeedEntry.getClazz() {
        return this.clazz;
    }
    
    public byte[] FeedEntry.getData() {
        return this.data;
    }
    
    public DateTime FeedEntry.getCreatedDate() {
        return this.createdDate;
    }
    
    public Account FeedEntry.getCreatedBy() {
        return this.createdBy;
    }
    
    public List<Account> FeedEntry.getAccounts() {
        return this.accounts;
    }
    
    public FeedEntryScenario FeedEntry.getScenario() {
        return this.scenario;
    }
    
}