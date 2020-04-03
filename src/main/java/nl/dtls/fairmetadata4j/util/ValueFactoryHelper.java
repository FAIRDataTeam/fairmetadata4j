/**
 * The MIT License
 * Copyright © 2019 DTL
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
package nl.dtls.fairmetadata4j.util;

import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Shortcuts to simplify code that needs to create IRIs, Literals, Statements, etc.
 */
public class ValueFactoryHelper {

    private static final ValueFactory VF = SimpleValueFactory.getInstance();

    public static IRI i(String iri) {
        if (iri == null) {
            return null;
        }
        return VF.createIRI(iri);
    }

    public static IRI i(Optional<String> oIri) {
        if (oIri.isEmpty()) {
            return null;
        }
        return i(oIri.get());
    }

    public static IRI i(String iri, Model m) {
        if (iri != null) {
            // URI: ':title'
            if (iri.startsWith(":")) {
                Optional<Namespace> oNamespace = m.getNamespace("");
                String prefix = oNamespace.get().getName();
                return i(prefix + iri.substring(1));
            }

            // URI: 'rda:title'
            String[] splitted = iri.split(":");
            if (splitted.length == 2 && splitted[1].charAt(0) != '/') {
                Optional<Namespace> oNamespace = m.getNamespace(splitted[0]);
                String prefix = oNamespace.get().getName();
                return i(prefix + splitted[1]);
            }

            // URI: 'http://schema.org/person#title'
            if (iri.contains("://")) {
                return i(iri);
            }

        }
        return null;
    }

    public static IRI i(Value value) {
        if (value == null) {
            return null;
        }
        return i(value.stringValue());
    }

    public static Literal l(String literal) {
        return VF.createLiteral(literal);
    }

    public static Literal l(Optional<String> oLiteral) {
        if (oLiteral.isEmpty()) {
            return null;
        }
        return l(oLiteral.get());
    }

    public static Literal l(float literal) {
        return VF.createLiteral(literal);
    }

    public static Literal l(LocalDateTime literal) {
        return VF.createLiteral(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(literal));
    }

    public static Literal l(Value value) {
        if (value == null) {
            return null;
        }
        return l(value.stringValue());
    }

    public static Statement s(Resource subject, IRI predicate, Value object) {
        return VF.createStatement(subject, predicate, object);
    }

    public static Statement s(Resource subject, IRI predicate, Value object, Resource context) {
        return VF.createStatement(subject, predicate, object, context);
    }
}
