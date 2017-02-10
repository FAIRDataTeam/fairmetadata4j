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
package nl.dtl.fairmetadata4j.io;

import java.util.List;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;

import com.google.common.base.Preconditions;
import java.util.ArrayList;

import nl.dtl.fairmetadata4j.model.CatalogMetadata;
import nl.dtl.fairmetadata4j.utils.RDFUtils;
import nl.dtl.fairmetadata4j.utils.vocabulary.DCAT;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.rio.RDFFormat;

/**
 * Parser for catalog metadata object
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-07
 * @version 0.1
 */
public class CatalogMetadataParser extends MetadataParser<CatalogMetadata> {

    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(CatalogMetadataParser.class);

    @Override
    protected CatalogMetadata createMetadata() {
        return new CatalogMetadata();
    }

    /**
     * Parse RDF statements to create catalog metadata object
     *
     * @param statements List of RDF statement list
     * @param catalogURI Catalog URI
     * @return CatalogMetadata object
     */
    @Override
    public CatalogMetadata parse(@Nonnull List<Statement> statements,
            @Nonnull IRI catalogURI) {
        Preconditions.checkNotNull(catalogURI,
                "Catalog URI must not be null.");
        Preconditions.checkNotNull(statements,
                "Catalog statements must not be null.");
        LOGGER.info("Parsing catalog metadata");

        CatalogMetadata metadata = super.parse(statements, catalogURI);
        List<IRI> datasets = new ArrayList();
        ValueFactory f = SimpleValueFactory.getInstance();
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            Value object = st.getObject();

            if (subject.equals(catalogURI)) {
                if (predicate.equals(FOAF.HOMEPAGE)) {
                    metadata.setHomepage((IRI) object);
                } else if (predicate.equals(DCAT.THEME_TAXONOMY)) {
                    metadata.getThemeTaxonomys().add((IRI) object);
                } else if (predicate.equals(DCAT.DATASET)) {
                    datasets.add((IRI) object);
                } else if (predicate.equals(DCTERMS.ISSUED)) {
                    metadata.setCatalogIssued(f.createLiteral(object.
                            stringValue(), XMLSchema.DATETIME));
                } else if (predicate.equals(DCTERMS.MODIFIED)) {
                    metadata.setCatalogModified(f.createLiteral(object.
                            stringValue(), XMLSchema.DATETIME));
                }
            }
        }
        if(!datasets.isEmpty()) {
            metadata.setDatasets(datasets);
        }
        return metadata;
    }

    /**
     * Parse RDF string to create catalog metadata object
     *
     * @param catalogMetadata Catalog metadata as a RDF string
     * @param catalogURI Catalog URI
     * @param fdpURI FairDataPoint URI
     * @param format RDF string's RDF format
     * @return CatalogMetadata object
     * @throws MetadataParserException
     */
    public CatalogMetadata parse(@Nonnull String catalogMetadata,
            @Nonnull IRI catalogURI, IRI fdpURI, @Nonnull RDFFormat format)
            throws MetadataParserException {
        Preconditions.checkNotNull(catalogMetadata,
                "Catalog metadata string must not be null.");
        Preconditions.checkNotNull(catalogURI, "Catalog URI must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");

        Preconditions.checkArgument(!catalogMetadata.isEmpty(),
                "The catalog metadata content can't be EMPTY");
        List<Statement> statements = RDFUtils.getStatements(catalogMetadata,
                catalogURI, format);

            CatalogMetadata metadata = this.parse(statements, catalogURI);
            metadata.setParentURI(fdpURI);

            return metadata;
    }

    /**
     * Parse RDF string to create catalog metadata object
     *
     * @param catalogMetadata Catalog metadata as a RDF string
     * @param baseURI
     * @param format RDF string's RDF format
     * @return CatalogMetadata object
     * @throws MetadataParserException
     */
    public CatalogMetadata parse(@Nonnull String catalogMetadata,
            IRI baseURI, @Nonnull RDFFormat format)
            throws MetadataParserException {
        Preconditions.checkNotNull(catalogMetadata,
                "Catalog metadata string must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");

        Preconditions.checkArgument(!catalogMetadata.isEmpty(),
                "The catalog metadata content can't be EMPTY");
            List<Statement> statements = RDFUtils.getStatements(catalogMetadata,
                baseURI, format);
            IRI catalogURI = (IRI) statements.get(0).getSubject();
            CatalogMetadata metadata = this.parse(statements, catalogURI);
            metadata.setUri(null);
            return metadata;
    }

}
