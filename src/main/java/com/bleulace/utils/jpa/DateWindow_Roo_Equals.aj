// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.utils.jpa;

import com.bleulace.utils.jpa.DateWindow;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

privileged aspect DateWindow_Roo_Equals {
    
    public boolean DateWindow.equals(Object obj) {
        if (!(obj instanceof DateWindow)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        DateWindow rhs = (DateWindow) obj;
        return new EqualsBuilder().append(end, rhs.end).append(start, rhs.start).isEquals();
    }
    
    public int DateWindow.hashCode() {
        return new HashCodeBuilder().append(end).append(start).toHashCode();
    }
    
}