/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.model;

import nl.dtl.fairmetadata.utils.vocabulary.DataCite;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-11-29
 * @version 0.1
 */
public class Identifier {
    private IRI uri;
    private IRI type = DataCite.IDENTIFIER;
    private Literal identifier;

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
     * @return the identifier
     */
    public Literal getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(Literal identifier) {
        this.identifier = identifier;
    }

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
    
}
