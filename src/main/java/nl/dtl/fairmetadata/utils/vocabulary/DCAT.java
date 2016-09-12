/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.dtl.fairmetadata.utils.vocabulary;


import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

/**
 * DCAT vocabulary.
 * See {@link <a href="https://www.w3.org/TR/vocab-dcat/">DCAT Vocabulary</a>}.
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-06
 * @version 0.1
 */
public class DCAT {
    public static final String PREFIX = "dcat";
    public static final String NAMESPACE = "http://www.w3.org/ns/dcat#";
    public static final URI THEME_TAXONOMY = 
            new URIImpl(NAMESPACE + "themeTaxonomy");
    public static final URI LANDING_PAGE = 
            new URIImpl(NAMESPACE + "landingPage");
    public static final URI THEME = new URIImpl(NAMESPACE + "theme");
    public static final URI CONTACT_POINT = new URIImpl(
            NAMESPACE + "contactPoint");
    public static final URI KEYWORD = new URIImpl(NAMESPACE + "keyword");
    public static final URI TYPE_CATALOG = new URIImpl(NAMESPACE + "Catalog");
    public static final URI TYPE_DATASET = new URIImpl(NAMESPACE + "Dataset");
    public static final URI TYPE_DISTRIBUTION = new URIImpl(
            NAMESPACE + "Distribution");
    public static final URI DATASET = new URIImpl(NAMESPACE + "dataset");
    public static final URI ACCESS_URL = new URIImpl(NAMESPACE + "accessURL");
    public static final URI DISTRIBUTION = new URIImpl(
            NAMESPACE + "distribution");
    public static final URI DOWNLOAD_URL = new URIImpl(
            NAMESPACE + "downloadURL");
    public static final URI MEDIA_TYPE = new URIImpl(
            NAMESPACE + "mediaType");
    public static final URI FORMAT = new URIImpl(
            NAMESPACE + "format");
    public static final URI BYTE_SIZE = new URIImpl(
            NAMESPACE + "byteSize");
    
}
