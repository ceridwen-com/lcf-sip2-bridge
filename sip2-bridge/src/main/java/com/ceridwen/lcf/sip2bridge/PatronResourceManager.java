/*
 * Copyright 2019 Ceridwen Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ceridwen.lcf.sip2bridge;

import com.ceridwen.circulation.SIP.messages.PatronInformation;
import com.ceridwen.circulation.SIP.messages.PatronInformationResponse;
import com.ceridwen.circulation.SIP.types.flagfields.SupportedMessages;
import com.ceridwen.lcf.model.authentication.AuthenticationToken;
import com.ceridwen.lcf.model.authentication.BasicAuthenticationToken;
import com.ceridwen.lcf.model.enumerations.CreationQualifier;
import com.ceridwen.lcf.model.enumerations.DirectUpdatePath;
import com.ceridwen.lcf.model.exceptions.EXC01_ServiceUnavailable;
import com.ceridwen.lcf.model.exceptions.EXC02_InvalidUserCredentials;
import com.ceridwen.lcf.model.exceptions.EXC05_InvalidEntityReference;
import com.ceridwen.lcf.server.resources.PatronResourceManagerInterface;
import com.ceridwen.lcf.server.resources.QueryResults;
import com.ceridwen.sip2client.SIP2Client;
import com.ceridwen.sip2client.faults.AuthenticationFault;
import com.ceridwen.sip2client.faults.Fault;
import com.ceridwen.sip2client.faults.NotFoundFault;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bic.ns.lcf.v1_0.LcfEntity;
import org.bic.ns.lcf.v1_0.Patron;
import org.bic.ns.lcf.v1_0.SelectionCriterion;

/**
 *
 * @author Ceridwen Limited
 */
public class PatronResourceManager implements PatronResourceManagerInterface {

    private SIP2Client getClient() {
        SIP2Client client = new SIP2Client("localhost", 55123, false, null, null, null, null, null, null, null);
        return client;
    }

    @Override
    public String Create(List<AuthenticationToken> authTokens, LcfEntity parent, Patron entity, List<CreationQualifier> qualifiers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Patron Retrieve(List<AuthenticationToken> authTokens, String identifier) {
        SIP2Client client = getClient();
        PatronInformation request = new PatronInformation();        
        request.setPatronIdentifier(identifier);
        
        for (AuthenticationToken token: authTokens) {
            switch (token.getAuthenticationCategory()) {
                case USER:
                    if (identifier.equals(((BasicAuthenticationToken)token).getUsername())) {
                        request.setPatronPassword(((BasicAuthenticationToken)token).getPassword());
                    }
                    break;
                case TERMINAL:
                    request.setTerminalPassword(((BasicAuthenticationToken)token).getPassword());
                    break;
            }
        }

        try {
            return new PatronInformationResponseToPatronMapper().convertLeftToRight(client.handleSIPMessage(request, PatronInformationResponse.class, SupportedMessages::isPatronInformation), new Patron());
        } catch (AuthenticationFault ex) {
            throw new EXC02_InvalidUserCredentials(null, null, null, ex);
        } catch (NotFoundFault ex) {
            throw new EXC05_InvalidEntityReference(null, null, null, ex);
        } catch (Fault ex) {
            Logger.getLogger(PatronResourceManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new EXC01_ServiceUnavailable(null, null, null, ex);
        }
    }

    @Override
    public Patron Modify(List<AuthenticationToken> authTokens, String identifier, Patron entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean DirectValueUpdate(List<AuthenticationToken> authTokens, String identifier, DirectUpdatePath path, String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean Delete(List<AuthenticationToken> authTokens, String identifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public QueryResults<Patron> Query(List<AuthenticationToken> authTokens, LcfEntity parent, int startIndex, int count, List<SelectionCriterion> selection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
