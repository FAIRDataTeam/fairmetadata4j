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
 * FairDataPoint vocabulary.
 * See {@link <a href="https://github.com/DTL-FAIRData/FDP-O">FDP vacobulary</a>}. 
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-06
 * @version 0.1
 */
public class FDP {
    private static final ValueFactory f = SimpleValueFactory.getInstance();
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
