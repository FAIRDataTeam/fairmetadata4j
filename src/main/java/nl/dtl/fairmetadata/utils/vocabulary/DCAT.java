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
 * DCAT vocabulary.
 * See {@link <a href="https://www.w3.org/TR/vocab-dcat/">DCAT Vocabulary</a>}.
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-06
 * @version 0.1
 */
public class DCAT {
    static final ValueFactory f = SimpleValueFactory.getInstance();
    public static final String PREFIX = "dcat";
    public static final String NAMESPACE = "http://www.w3.org/ns/dcat#";
    public static final IRI THEME_TAXONOMY = f.createIRI(NAMESPACE + "themeTaxonomy");
    public static final IRI LANDING_PAGE = f.createIRI(NAMESPACE + "landingPage");
    public static final IRI THEME = f.createIRI(NAMESPACE + "theme");
    public static final IRI CONTACT_POINT = f.createIRI(NAMESPACE + "contactPoint");
    public static final IRI KEYWORD = f.createIRI(NAMESPACE + "keyword");
    public static final IRI TYPE_CATALOG = f.createIRI(NAMESPACE + "Catalog");
    public static final IRI TYPE_DATASET = f.createIRI(NAMESPACE + "Dataset");
    public static final IRI TYPE_DISTRIBUTION = f.createIRI
        (NAMESPACE + "Distribution");
    public static final IRI DATASET = f.createIRI(NAMESPACE + "dataset");
    public static final IRI ACCESS_URL = f.createIRI(NAMESPACE + "accessURL");
    public static final IRI DISTRIBUTION = f.createIRI
        (NAMESPACE + "distribution");
    public static final IRI DOWNLOAD_URL = f.createIRI
        (NAMESPACE + "downloadURL");
    public static final IRI MEDIA_TYPE = f.createIRI(NAMESPACE + "mediaType");
    public static final IRI FORMAT = f.createIRI(NAMESPACE + "format");
    public static final IRI BYTE_SIZE = f.createIRI(NAMESPACE + "byteSize");
    
}
