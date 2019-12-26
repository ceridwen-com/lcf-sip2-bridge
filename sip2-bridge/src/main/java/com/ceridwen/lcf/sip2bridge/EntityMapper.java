/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceridwen.lcf.sip2bridge;

import com.ceridwen.sip2client.faults.Fault;

/**
 *
 * @author Matthew.Dovey
 */
public class EntityMapper<R, L> {
    
    PropertyMapper<R, ?, L, ?>[] propertyMappers;
    
    public EntityMapper(PropertyMapper<R, ?, L, ?> ... propertyMapper)
    {
        this.propertyMappers = propertyMapper;
    }
    
    public R convertRightToLeft(R r, L l) throws Fault
    {
        for (PropertyMapper<R, ?, L, ?> propMapper: propertyMappers) {
            propMapper.rightToLeft(r, l);
        }
        
        return r;
    }
    
    public L convertLeftToRight(R r, L l) throws Fault
    {
        for (PropertyMapper<R, ?, L, ?> propMapper: propertyMappers) {
            propMapper.leftToRight(r, l);
        }
        
        return l;
    }   
}
