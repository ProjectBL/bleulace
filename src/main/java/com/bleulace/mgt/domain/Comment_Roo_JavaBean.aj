// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.mgt.domain;

import java.util.Date;

import com.bleulace.crm.domain.Account;

privileged aspect Comment_Roo_JavaBean {
    
    public Account Comment.getAuthor() {
        return this.author;
    }
    
    public String Comment.getContent() {
        return this.content;
    }
    
    public Date Comment.getDatePosted() {
        return this.datePosted;
    }
    
}