// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.domain.crm.model;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.Comment;
import java.util.Date;

privileged aspect Comment_Roo_JavaBean {
    
    public String Comment.getContent() {
        return this.content;
    }
    
    public Account Comment.getAuthor() {
        return this.author;
    }
    
    public Date Comment.getDatePosted() {
        return this.datePosted;
    }
    
}