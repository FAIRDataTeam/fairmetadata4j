/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.utils.vocabulary;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

/**
 * DataCite vocabulary.
 * See {@link <a href="http://www.sparontologies.net/ontologies/datacite/source.html">DataCite Vocabulary</a>}.
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-11-29
 * @version 0.1
 */
public class DataCite {
     
    public static final String PREFIX = "dataCite";        
    public static final String NAMESPACE = "http://purl.org/spar/datacite/";
    public static final URI IDENTIFIER = 
            new URIImpl(NAMESPACE + "Identifier");
    public static final URI RESOURCE_IDENTIFIER = 
            new URIImpl(NAMESPACE + "ResourceIdentifier");
}
