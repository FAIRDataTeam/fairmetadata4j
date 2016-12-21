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
 * FairDataPoint vocabulary.
 * See {@link <a href="https://github.com/DTL-FAIRData/FDP-O">FDP vacobulary</a>}. 
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-06
 * @version 0.1
 */
public class FDP {
    static final ValueFactory f = SimpleValueFactory.getInstance();
    public static final String PREFIX = "fdp-o";
    public static final String NAMESPACE =  
            "http://rdf.biosemantics.org/ontologies/fdp-o#";    
    public static final IRI DATA_RECORD = 
            f.createIRI(NAMESPACE + "dataRecord");
    public static final IRI TYPE_DATA_RECORD = 
            f.createIRI(NAMESPACE + "DataRecord");
    public static final IRI REFERS_TO= 
            f.createIRI(NAMESPACE + "refersTo");
    public static final IRI METADATA_ISSUED= 
            f.createIRI(NAMESPACE + "metadataIssued");
    public static final IRI METADATA_MODIFIED= 
            f.createIRI(NAMESPACE + "metadataModified");    
    public static final IRI METADATA_IDENTIFIER= 
            f.createIRI(NAMESPACE + "metadataIdentifier");
    public static final IRI RML_MAPPING= 
            f.createIRI(NAMESPACE + "rmlMapping");
}
