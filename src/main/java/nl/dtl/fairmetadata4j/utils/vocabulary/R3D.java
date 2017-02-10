/**
 * The MIT License
 * Copyright © 2017 DTL
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
 * R3d vocabulary.
 * See {@link <a href="https://github.com/re3data/ontology/blob/master/r3dOntology.ttl">R3D vacobulary</a>}. 
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-10-20
 * @version 0.1
 */
public class R3D {
    private static final ValueFactory f = SimpleValueFactory.getInstance();    
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
