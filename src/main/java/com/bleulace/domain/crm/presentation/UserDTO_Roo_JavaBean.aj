// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.domain.crm.presentation;

import com.bleulace.domain.crm.presentation.UserDTO;
import java.util.TimeZone;

privileged aspect UserDTO_Roo_JavaBean {
    
    public String UserDTO.getId() {
        return this.id;
    }
    
    public void UserDTO.setId(String id) {
        this.id = id;
    }
    
    public byte[] UserDTO.getImage() {
        return this.image;
    }
    
    public void UserDTO.setImage(byte[] image) {
        this.image = image;
    }
    
    public TimeZone UserDTO.getTimeZone() {
        return this.timeZone;
    }
    
    public void UserDTO.setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
    
    public String UserDTO.getUsername() {
        return this.username;
    }
    
    public void UserDTO.setUsername(String username) {
        this.username = username;
    }
    
    public String UserDTO.getFirstName() {
        return this.firstName;
    }
    
    public void UserDTO.setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String UserDTO.getLastName() {
        return this.lastName;
    }
    
    public void UserDTO.setLastName(String lastName) {
        this.lastName = lastName;
    }
    
}