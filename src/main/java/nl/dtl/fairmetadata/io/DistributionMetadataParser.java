/**
 * The MIT License
 * Copyright © 2016 DTL
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
import nl.dtl.fairmetadata.model.DistributionMetadata;
import nl.dtl.fairmetadata.utils.RDFUtils;
import nl.dtl.fairmetadata.utils.vocabulary.DCAT;
import org.apache.logging.log4j.LogManager;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.rio.RDFFormat;

/**
 * Parser for distribution metadata
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-07
 * @version 0.1
 */
public class DistributionMetadataParser extends MetadataParser<DistributionMetadata> {

    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(DatasetMetadataParser.class);

    @Override
    protected DistributionMetadata createMetadata() {
        return new DistributionMetadata();
    }

    /**
     * Parse RDF statements to distribution metadata object
     *
     * @param statements List of RDF statement list
     * @param distributionURI Distribution URI
     * @return DistributionMetadata object
     */
    @Override
    public DistributionMetadata parse(@Nonnull List<Statement> statements,
            @Nonnull IRI distributionURI) {
        Preconditions.checkNotNull(distributionURI,
                "Distribution URI must not be null.");
        Preconditions.checkNotNull(statements,
                "Distribution statements must not be null.");
        LOGGER.info("Parsing distribution metadata");
        DistributionMetadata metadata = super.parse(statements,
                distributionURI);
        ValueFactory f = SimpleValueFactory.getInstance();
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            Value object = st.getObject();

            if (subject.equals(distributionURI)) {
                if (predicate.equals(DCAT.ACCESS_URL)) {
                    metadata.setAccessURL((IRI) object);
                } else if (predicate.equals(DCAT.DOWNLOAD_URL)) {
                    metadata.setDownloadURL((IRI) object);
                } else if (predicate.equals(DCAT.FORMAT)) {
                    metadata.setFormat(f.createLiteral(object.stringValue(),
                            XMLSchema.STRING));
                } else if (predicate.equals(DCAT.BYTE_SIZE)) {
                    metadata.setByteSize(f.createLiteral(object.stringValue(),
                            XMLSchema.STRING));
                } else if (predicate.equals(DCAT.MEDIA_TYPE)) {
                    metadata.setMediaType(f.createLiteral(object.stringValue(),
                            XMLSchema.STRING));
                } else if (predicate.equals(DCTERMS.ISSUED)) {
                    metadata.setDistributionIssued(f.createLiteral(object.
                            stringValue(), XMLSchema.DATETIME));
                } else if (predicate.equals(DCTERMS.MODIFIED)) {
                    metadata.setDistributionModified(f.createLiteral(object.
                            stringValue(), XMLSchema.DATETIME));
                }
            }
        }
        return metadata;
    }

    /**
     * Parse RDF string to distribution metadata object
     *
     * @param distributionMetadata Distribution metadata as a RDF string
     * @param distributionURI Distribution URI
     * @param datasetURI Dataset URI
     * @param format RDF string's RDF format
     * @return DistributionMetadata object
     * @throws nl.dtl.fairmetadata.io.MetadataParserException
     */
    public DistributionMetadata parse(@Nonnull String distributionMetadata,
            @Nonnull IRI distributionURI, IRI datasetURI, 
            @Nonnull RDFFormat format)
            throws MetadataParserException {
        Preconditions.checkNotNull(distributionMetadata,
                "Distribution metadata string must not be null.");
        Preconditions.checkNotNull(distributionURI,
                "Distribution URI must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");

        Preconditions.checkArgument(!distributionMetadata.isEmpty(),
                "The distribution metadata content can't be EMPTY");
        List<Statement> statements = RDFUtils.getStatements(distributionMetadata,
                distributionURI, format);

        DistributionMetadata metadata = this.parse(statements,
                distributionURI);
        metadata.setParentURI(datasetURI);
        return metadata;
    }

    /**
     * Parse RDF string to dataset distributionMetadata object
     *
     * @param distributionMetadata Distribution metadata as a RDF string
     * @param baseURI
     * @param format RDF string's RDF format
     * @return DistributionMetadata object
     * @throws MetadataParserException
     */
    public DistributionMetadata parse(@Nonnull String distributionMetadata,
            IRI baseURI, @Nonnull RDFFormat format)
            throws MetadataParserException {
        Preconditions.checkNotNull(distributionMetadata,
                "Distribution metadata string must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");

        Preconditions.checkArgument(!distributionMetadata.isEmpty(),
                "The distribution metadata content can't be EMPTY");

        List<Statement> statements = RDFUtils.getStatements(
                distributionMetadata,baseURI, format);
        IRI catalogURI = (IRI) statements.get(0).getSubject();
        DistributionMetadata metadata = this.parse(statements, catalogURI);
        metadata.setUri(null);
        return metadata;
    }

}
