/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.dtl.fairmetadata.utils.vocabulary;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

/**
 * LDP vocabulary.
 * See {@link <a href="https://www.w3.org/TR/ldp/">LDP doc</a>}.
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-06
 * @version 0.1
 */
public class LDP {
    public static final String NAMESPACE = "http://www.w3.org/ns/ldp#";
    public static final String PREFIX = "ldp";
    public static final URI CONTAINER = new URIImpl(NAMESPACE + "Container" );
    public static final URI CONTAINS = new URIImpl(NAMESPACE + "contains" );
}
