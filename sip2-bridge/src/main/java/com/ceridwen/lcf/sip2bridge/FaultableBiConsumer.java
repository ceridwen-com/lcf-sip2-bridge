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
 * @param <S>
 * @param <T>
 */
@FunctionalInterface
public interface FaultableBiConsumer<S, T> {
    public void accept(S t, T u) throws Fault;
}
