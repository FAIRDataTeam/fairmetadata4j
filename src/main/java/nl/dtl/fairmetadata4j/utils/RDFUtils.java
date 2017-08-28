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
package nl.dtl.fairmetadata4j.utils;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import nl.dtl.fairmetadata4j.io.MetadataParserException;
import org.apache.logging.log4j.LogManager;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;

/**
 * Utils class for manipulating rdf statements
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-06
 * @version 0.1
 */
public class RDFUtils {

    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(RDFUtils.class);

    private RDFUtils() {
    }

    /**
     * Create date literal based on current time
     *
     * @return Literal object
     * @throws DatatypeConfigurationException
     */
    public static Literal getCurrentTime() throws DatatypeConfigurationException {
        Date date = new Date();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        ValueFactory f = SimpleValueFactory.getInstance();
        Literal currentTime = f.createLiteral(xmlDate.toXMLFormat(), XMLSchema.DATETIME);
        return currentTime;
    }

    /**
     * Create List<Statements> from rdf string
     *
     * @param rdfString RDF string
     * @param baseUri Base uri
     * @param format RDF format
     * @return
     * @throws MetadataParserException
     */
    public static List<Statement> getStatements(String rdfString, IRI baseUri, RDFFormat format)
            throws MetadataParserException {
        String uri;
        if (baseUri == null) {
            uri = "http://example.com/dummyResource";
        } else {
            uri = baseUri.stringValue();
        }
        try {
            Model model = Rio.parse(new StringReader(rdfString), uri, format);
            Iterator<Statement> it = model.iterator();
            List<Statement> statements = ImmutableList.copyOf(it);
            return statements;
        } catch (RDFParseException | UnsupportedRDFormatException | IOException ex) {
            String errMsg = "Error reading dataset metadata content" + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        }
    }
    
    public static void checkNotLiterl(Value val) {
        if (val instanceof Literal) {
            throw new IllegalArgumentException(
                    "Objects of accessRights statements expected to be IRI");
        }
    }
}
