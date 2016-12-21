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
 * R3d vocabulary.
 * See {@link <a href="https://github.com/re3data/ontology/blob/master/r3dOntology.ttl">R3D vacobulary</a>}. 
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-10-20
 * @version 0.1
 */
public class R3D {
    static final ValueFactory f = SimpleValueFactory.getInstance();    
    public static final String PREFIX = "r3d";
    public static final String NAMESPACE =  
            "http://www.re3data.org/schema/3-0#";
    public static final IRI TYPE_REPOSTORY = f.createIRI
        (NAMESPACE + "Repository");
    public static final IRI DATA_CATALOG = f.createIRI
        (NAMESPACE + "dataCatalog");
    public static final IRI REPO_IDENTIFIER = f.createIRI
        (NAMESPACE + "repositoryIdentifier");
    public static final IRI INSTITUTION = f.createIRI
        (NAMESPACE + "institution");
    public static final IRI INSTITUTION_COUNTRY = f.createIRI
        (NAMESPACE + "institutionCountry");
    public static final IRI REPO_LAST_UPDATE = f.createIRI
        (NAMESPACE + "lastUpdate");
    public static final IRI REPO_START_DATE = f.createIRI
        (NAMESPACE + "startDate");
    
}
