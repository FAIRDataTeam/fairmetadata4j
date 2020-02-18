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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtls.fairmetadata4j.io;

import java.util.ArrayList;
import java.util.List;

import nl.dtls.fairmetadata4j.model.Metadata;
import nl.dtls.fairmetadata4j.utils.vocabulary.FDP;
import nl.dtls.fairmetadata4j.utils.vocabulary.Sio;
import org.apache.logging.log4j.LogManager;
import nl.dtls.fairmetadata4j.model.Metric;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

/**
 * An abstract class for metadata parser
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @param <T>
 * @since 2016-09-07
 * @version 0.1
 */
public abstract class MetadataParser<T extends Metadata> {

    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(MetadataParser.class);

    protected abstract T createMetadata();

    /**
     * Parse common metadata properties
     *
     * @param statements List of rdf statements
     * @param metadataUri Metadata uri
     * @return
     */
    protected T parse(List<Statement> statements, IRI metadataUri) {
        T metadata = createMetadata();
        metadata.setUri(metadataUri);
        LOGGER.info("Parse common metadata properties");
        ValueFactory f = SimpleValueFactory.getInstance();
        List<Metric> metrics = new ArrayList();
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            Value object = st.getObject();

            if (subject.equals(metadataUri)) {
                if (predicate.equals(DCTERMS.HAS_VERSION)) {
                    metadata.setVersion(f.createLiteral(object.stringValue(), XMLSchema.STRING));
                } else if (predicate.equals(RDFS.LABEL) || predicate.equals(DCTERMS.TITLE)) {
                    metadata.setTitle(f.createLiteral(object.stringValue(), XMLSchema.STRING));
                } else if (predicate.equals(DCTERMS.DESCRIPTION)) {
                    metadata.setDescription(f.createLiteral(object.stringValue(),
                            XMLSchema.STRING));
                } else if (predicate.equals(DCTERMS.LICENSE)) {
                    metadata.setLicense((IRI) object);
                } else if (predicate.equals(DCTERMS.RIGHTS)) {
                    metadata.setRights((IRI) object);
                } else if (predicate.equals(DCTERMS.LANGUAGE)) {
                    metadata.setLanguage((IRI) object);
                } else if (predicate.equals(DCTERMS.PUBLISHER)) {
                    metadata.setPublisher(AgentParser.parse(statements, (IRI) object));
                } else if (predicate.equals(FDP.METADATAIDENTIFIER)) {
                    metadata.setIdentifier(IdentifierParser.parse(statements, (IRI) object));
                } else if (predicate.equals(FDP.METADATAISSUED) && metadata.getIssued() == null) {
                    metadata.setIssued(f.createLiteral(object.stringValue(), XMLSchema.DATETIME));
                } else if (predicate.equals(FDP.METADATAMODIFIED)
                        && metadata.getModified() == null) {
                    metadata.setModified(f.createLiteral(object.stringValue(), XMLSchema.DATETIME));
                } else if (predicate.equals(DCTERMS.IS_PART_OF)) {
                    metadata.setParentURI((IRI) object);
                } else if (predicate.equals(DCTERMS.CONFORMS_TO)) {
                    metadata.setSpecification((IRI) object);
                } else if (predicate.equals(DCTERMS.ACCESS_RIGHTS)) {
                    metadata.setAccessRights(AccessRightsParser.parse(statements, (IRI) object));
                } else if (predicate.equals(Sio.REFERS_TO)) {
                    metrics.add(MetricParser.parse(statements, (IRI) object));
                }
            }
        }
        // Set metrics
        metadata.setMetrics(metrics);
        return metadata;
    }
}
