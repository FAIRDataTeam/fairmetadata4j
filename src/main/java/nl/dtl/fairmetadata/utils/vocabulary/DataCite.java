/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.utils.vocabulary;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;



/**
 * DataCite vocabulary.
 * See {@link <a href="http://www.sparontologies.net/ontologies/datacite/source.html">DataCite Vocabulary</a>}.
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-11-29
 * @version 0.1
 */
public class DataCite {
    
    static final ValueFactory f = SimpleValueFactory.getInstance();
    public static final String PREFIX = "dataCite";        
    public static final String NAMESPACE = "http://purl.org/spar/datacite/";
    public static final IRI IDENTIFIER = f.createIRI(NAMESPACE + "Identifier");
    public static final IRI RESOURCE_IDENTIFIER = 
            f.createIRI(NAMESPACE + "ResourceIdentifier");
}
