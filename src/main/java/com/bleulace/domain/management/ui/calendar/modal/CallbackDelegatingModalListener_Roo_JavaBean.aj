// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.domain.management.ui.calendar.modal;

import com.bleulace.domain.management.ui.calendar.modal.CallbackDelegatingModalListener;
import com.bleulace.domain.management.ui.calendar.model.EventModel.EventModelCallback;

privileged aspect CallbackDelegatingModalListener_Roo_JavaBean {
    
    public EventModelCallback CallbackDelegatingModalListener.getApply() {
        return this.apply;
    }
    
    public void CallbackDelegatingModalListener.setApply(EventModelCallback apply) {
        this.apply = apply;
    }
    
    public EventModelCallback CallbackDelegatingModalListener.getCancel() {
        return this.cancel;
    }
    
    public void CallbackDelegatingModalListener.setCancel(EventModelCallback cancel) {
        this.cancel = cancel;
    }
    
    public EventModelCallback CallbackDelegatingModalListener.getDelete() {
        return this.delete;
    }
    
    public void CallbackDelegatingModalListener.setDelete(EventModelCallback delete) {
        this.delete = delete;
    }
    
}