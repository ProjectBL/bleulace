// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bleulace.utils.dto;

import com.bleulace.utils.dto.MappingDTOConverterFactoryBean;
import org.modelmapper.PropertyMap;

privileged aspect MappingDTOConverterFactoryBean_Roo_JavaBean {
    
    public Class<S> MappingDTOConverterFactoryBean.getSourceClass() {
        return this.sourceClass;
    }
    
    public void MappingDTOConverterFactoryBean.setSourceClass(Class<S> sourceClass) {
        this.sourceClass = sourceClass;
    }
    
    public Class<T> MappingDTOConverterFactoryBean.getDtoClass() {
        return this.dtoClass;
    }
    
    public void MappingDTOConverterFactoryBean.setDtoClass(Class<T> dtoClass) {
        this.dtoClass = dtoClass;
    }
    
    public PropertyMap<S, T> MappingDTOConverterFactoryBean.getMapping() {
        return this.mapping;
    }
    
    public void MappingDTOConverterFactoryBean.setMapping(PropertyMap<S, T> mapping) {
        this.mapping = mapping;
    }
    
}