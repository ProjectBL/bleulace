// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.domain.management.model;

import com.bleulace.domain.management.model.ProgressValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

privileged aspect ProgressValue_Roo_Equals {
    
    public boolean ProgressValue.equals(Object obj) {
        if (!(obj instanceof ProgressValue)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ProgressValue rhs = (ProgressValue) obj;
        return new EqualsBuilder().append(completed, rhs.completed).append(total, rhs.total).isEquals();
    }
    
    public int ProgressValue.hashCode() {
        return new HashCodeBuilder().append(completed).append(total).toHashCode();
    }
    
}
