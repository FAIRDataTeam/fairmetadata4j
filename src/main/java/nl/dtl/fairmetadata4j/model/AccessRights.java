/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata4j.model;

import org.eclipse.rdf4j.model.IRI;

/**
 * AccessRights object
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2017-02-22
 * @version 0.1
 */
public class AccessRights {
    
    private IRI uri;
    private Authorization authorization;

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
     * @return the authorization
     */
    public Authorization getAuthorization() {
        return authorization;
    }

    /**
     * @param authorization the authorization to set
     */
    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }
    
}
