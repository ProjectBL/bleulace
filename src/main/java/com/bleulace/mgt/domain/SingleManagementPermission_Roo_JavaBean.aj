// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.mgt.domain;

import com.bleulace.mgt.domain.ManagementAssignment;
import com.bleulace.mgt.domain.Project;
import com.bleulace.mgt.domain.SingleManagementPermission;

privileged aspect SingleManagementPermission_Roo_JavaBean {
    
    public ManagementAssignment SingleManagementPermission.getAssignment() {
        return this.assignment;
    }
    
    public Project SingleManagementPermission.getProject() {
        return this.project;
    }
    
}
