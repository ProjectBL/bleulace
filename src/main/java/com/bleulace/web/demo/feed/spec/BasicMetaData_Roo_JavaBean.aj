// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.web.demo.feed.spec;

import com.bleulace.web.BleulaceTheme.AvatarGender;
import com.bleulace.web.demo.feed.spec.BasicMetaData;
import java.util.Date;

privileged aspect BasicMetaData_Roo_JavaBean {
    
    public String BasicMetaData.getSubjectName() {
        return this.subjectName;
    }
    
    public void BasicMetaData.setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    public AvatarGender BasicMetaData.getGender() {
        return this.gender;
    }
    
    public void BasicMetaData.setGender(AvatarGender gender) {
        this.gender = gender;
    }
    
    public Date BasicMetaData.getTimestamp() {
        return this.timestamp;
    }
    
    public void BasicMetaData.setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
}