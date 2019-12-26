/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceridwen.lcf.sip2bridge;

import com.ceridwen.circulation.SIP.messages.PatronInformationResponse;
import com.ceridwen.sip2client.faults.AuthenticationFault;
import org.bic.ns.lcf.v1_0.Patron;

/**
 *
 * @author Matthew.Dovey
 */
public class PatronInformationResponseToPatronMapper extends EntityMapper<PatronInformationResponse, Patron> {
    
    public PatronInformationResponseToPatronMapper()
    {
        super(
                new PropertyMapper<>
                        (
                            (o, v) -> o.setPatronIdentifier(v),
                            o -> o.getPatronIdentifier(),
                            (o, v) -> o.setIdentifier(v),
                            o -> o.getIdentifier()
                        ),
                new PropertyMapper<>
                        (
                            (o, v) -> o.setPersonalName(v),
                            o -> o.getPersonalName(),
                            (o, v) -> o.setName(v),
                            o -> o.getName()
                        ),
                new PropertyMapper<>
                        (
                            (o, v) -> o.setValidPatronPassword(v),
                            o -> o.isValidPatronPassword(),
                            (o, v) -> {if (v == null || !v) throw new AuthenticationFault("", false);},
                            o -> true
                        ),
                new PropertyMapper<>
                        (
                            (o, v) -> o.setValidPatron(v),
                            o -> o.isValidPatron(),
                            (o, v) -> {if (v == null || !v) throw new AuthenticationFault("", false);},
                            o -> true
                        ),
                new PropertyMapper<>
                        (
                            (o, v) -> o.setChargedItemsCount(v),
                            o -> o.getChargedItemsCount(),
                            (o, v) -> o.setOnLoanItems(v),
                            o -> o.getOnLoanItems()
                        )
            );
    }
}
