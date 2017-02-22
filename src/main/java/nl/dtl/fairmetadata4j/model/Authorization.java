/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata4j.model;

import java.util.List;
import org.eclipse.rdf4j.model.IRI;

/**
 * Authorization object
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2017-02-22
 * @version 0.1
 */
public class Authorization {
    
    private IRI uri;
    private List<IRI> accessMode;
    private List<Agent> authorizedAgent;

    /**
     * @return the uri
     */
    public IRI getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(IRI uri) {
        this.uri = uri;
    }

    /**
     * @return the accessMode
     */
    public List<IRI> getAccessMode() {
        return accessMode;
    }

    /**
     * @param accessMode the accessMode to set
     */
    public void setAccessMode(List<IRI> accessMode) {
        this.accessMode = accessMode;
    }

    /**
     * @return the authorizedAgent
     */
    public List<Agent> getAuthorizedAgent() {
        return authorizedAgent;
    }

    /**
     * @param authorizedAgent the authorizedAgent to set
     */
    public void setAuthorizedAgent(List<Agent> authorizedAgent) {
        this.authorizedAgent = authorizedAgent;
    }
    
}
