/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.model;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.vocabulary.FOAF;



/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-11-30
 * @version 0.1
 */
public class Agent {
    
    private IRI uri;
    private IRI type = FOAF.AGENT;
    private Literal name;

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
     * @return the type
     */
    public IRI getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(IRI type) {
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
