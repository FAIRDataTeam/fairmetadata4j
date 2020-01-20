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

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import nl.dtls.fairmetadata4j.model.DatasetMetadata;
import nl.dtls.fairmetadata4j.utils.RDFUtils;
import org.apache.logging.log4j.LogManager;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.DCAT;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.rio.RDFFormat;

/**
 * Parser for dataset metadata object
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-07
 * @version 0.1
 */
public class DatasetMetadataParser extends MetadataParser<DatasetMetadata> {

    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(DatasetMetadataParser.class);

    @Override
    protected DatasetMetadata createMetadata() {
        return new DatasetMetadata();
    }

    /**
     * Parse RDF statements to create dataset metadata object
     *
     * @param statements List of RDF statement list
     * @param datasetURI Dataset URI
     * @return DatasetMetadata object
     */
    @Override
    public DatasetMetadata parse(@Nonnull List<Statement> statements, @Nonnull IRI datasetURI) {
        Preconditions.checkNotNull(datasetURI, "Dataset URI must not be null.");
        Preconditions.checkNotNull(statements, "Dataset statements must not be null.");
        LOGGER.info("Parsing dataset metadata");
        DatasetMetadata metadata = super.parse(statements, datasetURI);
        List<IRI> distributions = new ArrayList();
        ValueFactory f = SimpleValueFactory.getInstance();
        List<Literal> keywords = new ArrayList();
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            Value object = st.getObject();
            if (subject.equals(datasetURI)) {
                if (predicate.equals(DCAT.LANDING_PAGE)) {
                    metadata.setLandingPage((IRI) object);
                } else if (predicate.equals(DCAT.THEME)) {
                    metadata.getThemes().add((IRI) object);
                } else if (predicate.equals(DCAT.CONTACT_POINT)) {
                    metadata.setContactPoint((IRI) object);
                } else if (predicate.equals(DCAT.KEYWORD)) {
                    keywords.add(f.createLiteral(object.stringValue(), XMLSchema.STRING));
                } else if (predicate.equals(DCAT.HAS_DISTRIBUTION)) {
                    distributions.add((IRI) object);
                } else if (predicate.equals(DCTERMS.ISSUED)) {
                    metadata.setDatasetIssued(f.createLiteral(object.stringValue(),
                            XMLSchema.DATETIME));
                } else if (predicate.equals(DCTERMS.MODIFIED)) {
                    metadata.setDatasetModified(f.createLiteral(object.stringValue(),
                            XMLSchema.DATETIME));
                }
            }
        }
        metadata.setKeywords(keywords);
        metadata.setDistributions(distributions);
        return metadata;
    }

    /**
     * Parse RDF string to create dataset metadata object
     *
     * @param datasetMetadata Dataset metadata as a RDF string
     * @param datasetURI Dataset URI
     * @param catalogURI Catalog URI
     * @param format RDF string's RDF format
     * @return DatasetMetadata object
     * @throws MetadataParserException
     */
    public DatasetMetadata parse(@Nonnull String datasetMetadata, @Nonnull IRI datasetURI,
            IRI catalogURI, @Nonnull RDFFormat format) throws MetadataParserException {
        Preconditions.checkNotNull(datasetMetadata, "Dataset metadata string must not be null.");
        Preconditions.checkNotNull(datasetURI, "Dataset URI must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");
        Preconditions.checkArgument(!datasetMetadata.isEmpty(),
                "The dataset metadata content can't be EMPTY");
        List<Statement> statements = RDFUtils.getStatements(datasetMetadata, datasetURI, format);
        DatasetMetadata metadata = this.parse(statements, datasetURI);
        metadata.setParentURI(catalogURI);
        return metadata;
    }

    /**
     * Parse RDF string to create dataset metadata object
     *
     * @param datasetMetadata Catalog metadata as a RDF string
     * @param baseURI
     * @param format RDF string's RDF format
     * @return DatasetMetadata object
     * @throws MetadataParserException
     */
    public DatasetMetadata parse(@Nonnull String datasetMetadata, IRI baseURI,
            @Nonnull RDFFormat format) throws MetadataParserException {
        Preconditions.checkNotNull(datasetMetadata, "Dataset metadata string must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");
        Preconditions.checkArgument(!datasetMetadata.isEmpty(),
                "The dataset metadata content can't be EMPTY");
        List<Statement> statements = RDFUtils.getStatements(datasetMetadata, baseURI, format);
        IRI datasetURI = (IRI) statements.get(0).getSubject();
        DatasetMetadata metadata = this.parse(statements, datasetURI);
        metadata.setUri(null);
        return metadata;
    }

}
