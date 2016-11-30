/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.model;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.FOAF;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-11-30
 * @version 0.1
 */
public class Agent {
    
    private URI uri;
    private URI type = FOAF.AGENT;
    private Literal name;

    /**
     * @return the uri
     */
    public URI getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(URI uri) {
        this.uri = uri;
    }

    /**
     * @return the type
     */
    public URI getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(URI type) {
        this.type = type;
    }

    /**
     * @return the name
     */
    public Literal getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(Literal name) {
        this.name = name;
    }

    
}
