/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.utils.vocabulary;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

/**
 * R3d vocabulary.
 * See {@link <a href="https://github.com/re3data/ontology/blob/master/r3dOntology.ttl">R3D vacobulary</a>}. 
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-10-20
 * @version 0.1
 */
public class R3D {
    
    public static final String PREFIX = "r3d";
    public static final String NAMESPACE =  
            "http://www.re3data.org/schema/3-0#";
    public static final URI TYPE_REPOSTORY = new URIImpl(
            NAMESPACE + "Repository");
    public static final URI DATA_CATALOG = new URIImpl(
            NAMESPACE + "dataCatalog");
    public static final URI REPO_IDENTIFIER = new URIImpl(
            NAMESPACE + "repositoryIdentifier");
    public static final URI INSTITUTION = new URIImpl(
            NAMESPACE + "institution");
    public static final URI INSTITUTION_COUNTRY = new URIImpl(
            NAMESPACE + "institutionCountry");
    public static final URI REPO_LAST_UPDATE = new URIImpl(
            NAMESPACE + "lastUpdate");
    public static final URI REPO_START_DATE = new URIImpl(
            NAMESPACE + "startDate");
    
}
