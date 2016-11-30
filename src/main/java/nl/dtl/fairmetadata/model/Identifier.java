/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.model;

import nl.dtl.fairmetadata.utils.vocabulary.DataCite;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-11-29
 * @version 0.1
 */
public class Identifier {
    private URI uri;
    private URI type = DataCite.IDENTIFIER;
    private Literal identifier;

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
    public URI getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(URI uri) {
        this.uri = uri;
    }
    
}
