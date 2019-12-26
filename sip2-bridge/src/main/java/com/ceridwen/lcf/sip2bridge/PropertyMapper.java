/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceridwen.lcf.sip2bridge;

import com.ceridwen.sip2client.faults.Fault;
import java.util.function.Function;

/**
 *
 * @author Matthew.Dovey
 */
public class PropertyMapper<L, LT, R, RT> {
    FaultableBiConsumer<L, LT> leftset;
    Function<L, RT> leftget;
    FaultableBiConsumer<R, RT> rightset;
    Function<R, LT> rightget;

    public PropertyMapper(FaultableBiConsumer<L, LT> leftset, Function<L, RT> leftget, FaultableBiConsumer<R, RT> rightset, Function<R, LT> rightget)
    {
        this.leftset = leftset;
        this.leftget = leftget;
        this.rightset = rightset;
        this.rightget = rightget;
    }
    
    public void leftToRight(L l, R r) throws Fault {
        this.rightset.accept(r, leftget.apply(l));
    }
    
    public void rightToLeft(L l, R r) throws Fault {
        this.leftset.accept(l, rightget.apply(r)); 
    }
}
