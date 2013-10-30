// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.domain.management.model;

import com.bleulace.domain.management.model.ManagementAssignment;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

privileged aspect ManagementAssignment_Roo_Equals {
    
    public boolean ManagementAssignment.equals(Object obj) {
        if (!(obj instanceof ManagementAssignment)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ManagementAssignment rhs = (ManagementAssignment) obj;
        return new EqualsBuilder().append(account, rhs.account).append(resource, rhs.resource).append(role, rhs.role).isEquals();
    }
    
    public int ManagementAssignment.hashCode() {
        return new HashCodeBuilder().append(account).append(resource).append(role).toHashCode();
    }
    
}
