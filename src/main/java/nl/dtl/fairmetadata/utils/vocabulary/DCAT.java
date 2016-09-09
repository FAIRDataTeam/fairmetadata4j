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
    public static final String NS = "http://www.w3.org/ns/dcat#";
    public static final URI THEME_TAXONOMY = 
            new URIImpl(NS + "themeTaxonomy");
    public static final URI LANDING_PAGE = 
            new URIImpl(NS + "landingPage");
    public static final URI THEME = new URIImpl(NS + "theme");
    public static final URI CONTACT_POINT = new URIImpl(
            NS + "contactPoint");
    public static final URI KEYWORD = new URIImpl(NS + "keyword");
    public static final URI TYPE_CATALOG = new URIImpl(NS + "Catalog");
    public static final URI TYPE_DATASET = new URIImpl(NS + "Dataset");
    public static final URI TYPE_DISTRIBUTION = new URIImpl(
            NS + "Distribution");
    public static final URI DATASET = new URIImpl(NS + "dataset");
    public static final URI ACCESS_URL = new URIImpl(NS + "accessURL");
    public static final URI DISTRIBUTION = new URIImpl(
            NS + "distribution");
    public static final URI DOWNLOAD_URL = new URIImpl(
            NS + "downloadURL");
    public static final URI MEDIA_TYPE = new URIImpl(
            NS + "mediaType");
    public static final URI FORMAT = new URIImpl(
            NS + "format");
    public static final URI BYTE_SIZE = new URIImpl(
            NS + "byteSize");
    
}
