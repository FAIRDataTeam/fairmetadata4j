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
package nl.dtl.fairmetadata.io;

import com.google.common.base.Preconditions;
import java.util.List;
import javax.annotation.Nonnull;
import nl.dtl.fairmetadata.model.Identifier;
import org.apache.logging.log4j.LogManager;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-11-30
 * @version 0.1
 */
public class IdentifierParser {

    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(IdentifierParser.class);

    public static Identifier parse(@Nonnull List<Statement> statements,
            @Nonnull IRI identifierURI) {
        Preconditions.checkNotNull(identifierURI,
                "Identifier URI must not be null.");
        Preconditions.checkNotNull(statements,
                "Identifier statements must not be null.");
        Preconditions.checkArgument(!statements.isEmpty(),
                "Identifier statements must not be empty.");
        LOGGER.info("Parsing identifier");  

        Identifier id = new Identifier();
        id.setUri(identifierURI);
        ValueFactory f = SimpleValueFactory.getInstance();
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            Value object = st.getObject();

            if (subject.equals(identifierURI)) {
                if (predicate.equals(RDF.TYPE)) {
                    id.setType((IRI) object);
                } else if (predicate.equals(DCTERMS.IDENTIFIER)) {
                    id.setIdentifier(f.createLiteral(object.stringValue(),
                            XMLSchema.STRING));
                }
            }
        }
        Preconditions.checkNotNull(id.getUri(), "Identifier uri can't be null.");
        Preconditions.checkNotNull(id.getIdentifier(), 
                "Identifier value can't be null.");
        return id;
    }
}
