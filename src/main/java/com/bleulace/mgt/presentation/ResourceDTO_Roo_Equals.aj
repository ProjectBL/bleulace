// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.mgt.presentation;

import com.bleulace.mgt.presentation.ResourceDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

privileged aspect ResourceDTO_Roo_Equals {
    
    public boolean ResourceDTO.equals(Object obj) {
        if (!(obj instanceof ResourceDTO)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ResourceDTO rhs = (ResourceDTO) obj;
        return new EqualsBuilder().append(complete, rhs.complete).append(id, rhs.id).append(title, rhs.title).isEquals();
    }
    
    public int ResourceDTO.hashCode() {
        return new HashCodeBuilder().append(complete).append(id).append(title).toHashCode();
    }
    
}