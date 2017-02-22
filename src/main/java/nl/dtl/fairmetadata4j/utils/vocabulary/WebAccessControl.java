/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata4j.utils.vocabulary;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

/**
 * WebAccessControl vocabulary.
 * See {@link <a href="https://www.w3.org/wiki/WebAccessControl">WebAccessControl Vocabulary</a>}.
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2017-02-22
 * @version 0.1
 */
public class WebAccessControl {
    
    private static final ValueFactory f = SimpleValueFactory.getInstance();
    public static final String PREFIX = "acl";
    public static final String NAMESPACE = "http://www.w3.org/ns/auth/acl#";
    public static final IRI AUTHORIZATION = f.createIRI(NAMESPACE + "Authorization");
    public static final IRI ACCESS_APPEND = f.createIRI(NAMESPACE + "Append");
    public static final IRI ACCESS_WRITE = f.createIRI(NAMESPACE + "Write");
    public static final IRI ACCESS_READ = f.createIRI(NAMESPACE + "Read");
    public static final IRI ACCESS_MODE = f.createIRI(NAMESPACE + "mode");
    public static final IRI ACCESS_AGENT = f.createIRI(NAMESPACE + "agent");
    
}
