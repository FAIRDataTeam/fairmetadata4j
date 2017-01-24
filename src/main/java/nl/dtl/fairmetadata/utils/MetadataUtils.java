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
package nl.dtl.fairmetadata.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import nl.dtl.fairmetadata.io.MetadataException;
import nl.dtl.fairmetadata.model.Agent;
import nl.dtl.fairmetadata.model.CatalogMetadata;
import nl.dtl.fairmetadata.model.DataRecordMetadata;
import nl.dtl.fairmetadata.model.DatasetMetadata;
import nl.dtl.fairmetadata.model.DistributionMetadata;
import nl.dtl.fairmetadata.model.FDPMetadata;
import nl.dtl.fairmetadata.model.Identifier;
import nl.dtl.fairmetadata.model.Metadata;
import nl.dtl.fairmetadata.utils.vocabulary.DCAT;
import nl.dtl.fairmetadata.utils.vocabulary.FDP;
import nl.dtl.fairmetadata.utils.vocabulary.R3D;
import org.apache.logging.log4j.LogManager;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandler;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;

/**
 * Meatadata util class to convert Metadata object to RDF statements and RDF
 * String.
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-12
 * @version 0.1
 */
public class MetadataUtils {

    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(MetadataUtils.class);

    /**
     * Get RDF statements from metadata object
     *
     * @param <T>
     * @param metadata Subclass of metadata object
     * @return List of RDF statements
     * @throws MetadataException
     */
    public static <T extends Metadata> List<Statement> getStatements(
            @Nonnull T metadata) throws MetadataException {
        Preconditions.checkNotNull(metadata,
                "Metadata object must not be null.");
        try {
            checkMandatoryProperties(metadata);
        } catch (NullPointerException ex) {
            throw (new MetadataException(ex.getMessage()));
        }
        Model model = new LinkedHashModel();
        LOGGER.info("Creating metadata rdf model");
        setCommonProperties(model, metadata);
        LOGGER.info("Adding specific metadata properties to the rdf model");
        if (metadata instanceof FDPMetadata) {
            return getStatements(model, (FDPMetadata) metadata);
        } else if (metadata instanceof CatalogMetadata) {
            return getStatements(model, (CatalogMetadata) metadata);
        } else if (metadata instanceof DatasetMetadata) {
            return getStatements(model, (DatasetMetadata) metadata);
        } else if (metadata instanceof DistributionMetadata) {
            return getStatements(model, (DistributionMetadata) metadata);
        } else if (metadata instanceof DataRecordMetadata) {
            return getStatements(model, (DataRecordMetadata) metadata);
        } else {
            String msg = "Unknown metadata object";
            LOGGER.error(msg);
            throw (new MetadataException(msg));
        }
    }

    /**
     * Convert metadata object to RDF string
     *
     * @param <T>
     * @param metadata Subclass of metadata object
     * @param format RDF format
     * @return RDF string
     * @throws MetadataException
     */
    public static <T extends Metadata> String getString(@Nonnull T metadata,
            @Nonnull RDFFormat format)
            throws MetadataException {
        Preconditions.checkNotNull(metadata,
                "Metadata object must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");
        
        StringWriter sw = new StringWriter();
        RDFWriter writer = Rio.createWriter(format, sw);
        List<Statement> statement = getStatements(metadata);
        try {
            propagateToHandler(statement, writer);
        } catch (RepositoryException | RDFHandlerException ex) {
            LOGGER.error("Error reading RDF statements");
            throw (new MetadataException(ex.getMessage()));
        }
        return sw.toString();
    }

    /**
     * Get RDF statements from FDP metadata object
     *
     * @param model RDF model with common metadata properties
     * @param metadata FDPMetadata object
     * @return List of RDF statements
     * @throws MetadataException
     */
    private static List<Statement> getStatements(Model model,
            FDPMetadata metadata)
            throws MetadataException {
        LOGGER.info("Adding FDP metadata properties to the rdf model");
        try {
            Preconditions.checkNotNull(metadata.getRepostoryIdentifier(),
                    "Repostory ID must not be null.");
            Preconditions.checkNotNull(metadata.getPublisher(),
                    "Metadata publisher must not be null.");
        } catch (NullPointerException ex) {
            throw (new MetadataException(ex.getMessage()));
        }
        ValueFactory f = SimpleValueFactory.getInstance();
        model.add(metadata.getUri(), RDF.TYPE, R3D.TYPE_REPOSTORY);
        IRI swaggerURL = f.createIRI(
                metadata.getUri().toString() + "/swagger-ui.html");
        model.add(metadata.getUri(), RDFS.SEEALSO, swaggerURL);
        Identifier id = metadata.getRepostoryIdentifier();
        model.add(metadata.getUri(), R3D.REPO_IDENTIFIER, id.getUri());
        model.add(id.getUri(), RDF.TYPE, id.getType());
        model.add(id.getUri(), DCTERMS.IDENTIFIER, id.getIdentifier());

        if (metadata.getHomepage() != null) {
            model.add(metadata.getUri(), FOAF.HOMEPAGE, metadata.getHomepage());
        }
        if (metadata.getInstitutionCountry() != null) {
            model.add(metadata.getUri(), R3D.INSTITUTION_COUNTRY,
                    metadata.getInstitutionCountry());
        }
        if (metadata.getLastUpdate() != null) {
            model.add(metadata.getUri(), R3D.REPO_LAST_UPDATE,
                    metadata.getLastUpdate());
        }
        if (metadata.getStartDate() != null) {
            model.add(metadata.getUri(), R3D.REPO_START_DATE,
                    metadata.getStartDate());
        }
        metadata.getCatalogs().stream().forEach((catalog) -> {
            model.add(metadata.getUri(), R3D.DATA_CATALOG, catalog);
        });
        if (metadata.getInstitution() != null) {
            Agent agent = metadata.getInstitution();
            model.add(metadata.getUri(), R3D.INSTITUTION, agent.getUri());
            model.add(agent.getUri(), RDF.TYPE, agent.getType());
            if (agent.getName() == null) {
                String errMsg = "No institution name provided";
                LOGGER.debug(errMsg);
            } else {
                model.add(agent.getUri(), FOAF.NAME, agent.getName());
            }
        }
        return getStatements(model);
    }

    /**
     *
     * Get RDF statements from catalog metadata object
     *
     * @param model RDF model with common metadata properties
     * @param metadata CatalogMetadata object
     * @return List of RDF statements
     * @throws MetadataException
     */
    private static List<Statement> getStatements(Model model,
            CatalogMetadata metadata)
            throws MetadataException {
        try {
            Preconditions.checkNotNull(metadata.getPublisher(),
                    "Metadata publisher must not be null.");
            Preconditions.checkNotNull(metadata.getThemeTaxonomys(),
                    "Metadata dcat:themeTaxonomy must not be null.");
            Preconditions.checkArgument(!metadata.getThemeTaxonomys().isEmpty(),
                    "Metadata dcat:themeTaxonomy must not be empty.");
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw (new MetadataException(ex.getMessage()));
        }
        LOGGER.info("Adding catalogy metadata properties to the rdf model");
        model.add(metadata.getUri(), RDF.TYPE, DCAT.TYPE_CATALOG);
        if (metadata.getHomepage() != null) {
            model.add(metadata.getUri(), FOAF.HOMEPAGE, metadata.getHomepage());
        }
        if (metadata.getCatalogIssued() != null) {
            model.add(metadata.getUri(), DCTERMS.ISSUED,
                    metadata.getCatalogIssued());
        }
        if (metadata.getCatalogModified() != null) {
            model.add(metadata.getUri(), DCTERMS.MODIFIED,
                    metadata.getCatalogModified());
        }
        metadata.getThemeTaxonomys().stream().forEach((themeTax) -> {
            model.add(metadata.getUri(), DCAT.THEME_TAXONOMY, themeTax);
        });
        metadata.getDatasets().stream().forEach((dataset) -> {
            model.add(metadata.getUri(), DCAT.DATASET, dataset);
        });
        return getStatements(model);
    }

    /**
     *
     * Get RDF statements from dataset metadata object
     *
     * @param model RDF model with common metadata properties
     * @param metadata DatasetMetadata object
     * @return List of RDF statements
     * @throws MetadataException
     */
    private static List<Statement> getStatements(Model model,
            DatasetMetadata metadata)
            throws MetadataException {
        try {
            Preconditions.checkNotNull(metadata.getPublisher(),
                    "Metadata publisher must not be null.");
            Preconditions.checkNotNull(metadata.getThemes(),
                    "Metadata dcat:theme must not be null.");
            Preconditions.checkArgument(!metadata.getThemes().isEmpty(),
                    "Metadata dcat:theme must not be empty.");
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw (new MetadataException(ex.getMessage()));
        }
        LOGGER.info("Adding dataset metadata properties to the rdf model");
        model.add(metadata.getUri(), RDF.TYPE, DCAT.TYPE_DATASET);
        if (metadata.getContactPoint() != null) {
            model.add(metadata.getUri(), DCAT.CONTACT_POINT,
                    metadata.getContactPoint());
        }
        if (metadata.getLandingPage() != null) {
            model.add(metadata.getUri(), DCAT.LANDING_PAGE,
                    metadata.getLandingPage());
        }
        if (metadata.getDatasetIssued() != null) {
            model.add(metadata.getUri(), DCTERMS.ISSUED,
                    metadata.getDatasetIssued());
        }
        if (metadata.getDatasetModified() != null) {
            model.add(metadata.getUri(), DCTERMS.MODIFIED,
                    metadata.getDatasetModified());
        }
        metadata.getThemes().stream().forEach((theme) -> {
            model.add(metadata.getUri(), DCAT.THEME, theme);
        });
        metadata.getKeywords().stream().forEach((keyword) -> {
            model.add(metadata.getUri(), DCAT.KEYWORD, keyword);
        });
        metadata.getDistributions().stream().forEach((distribution) -> {
            model.add(metadata.getUri(), DCAT.DISTRIBUTION, distribution);
        });
        return getStatements(model);
    }

    /**
     *
     * Get RDF statements from distribution metadata object
     *
     * @param model RDF model with common metadata properties
     * @param metadata DistributionMetadata object
     * @return List of RDF statements
     * @throws MetadataException
     */
    private static List<Statement> getStatements(Model model,
            DistributionMetadata metadata)
            throws MetadataException {

        if (metadata.getAccessURL() == null
                && metadata.getDownloadURL() == null) {
            String errMsg
                    = "No dcat:accessURL or dcat:downloadURL URL is provided";
            LOGGER.error(errMsg);
            throw (new MetadataException(errMsg));
        }
        LOGGER.info("Adding distrubution metadata properties to the rdf model");
        model.add(metadata.getUri(), RDF.TYPE, DCAT.TYPE_DISTRIBUTION);
        if (metadata.getAccessURL() != null) {
            model.add(metadata.getUri(), DCAT.ACCESS_URL,
                    metadata.getAccessURL());
        } else if (metadata.getDownloadURL() != null) {
            model.add(metadata.getUri(), DCAT.DOWNLOAD_URL,
                    metadata.getDownloadURL());
        }
        if (metadata.getDistributionIssued() != null) {
            model.add(metadata.getUri(), DCTERMS.ISSUED,
                    metadata.getDistributionIssued());
        }
        if (metadata.getDistributionModified() != null) {
            model.add(metadata.getUri(), DCTERMS.MODIFIED,
                    metadata.getDistributionModified());
        }
        if (metadata.getByteSize() != null) {
            model.add(metadata.getUri(), DCAT.BYTE_SIZE,
                    metadata.getByteSize());
        }
        if (metadata.getFormat() != null) {
            model.add(metadata.getUri(), DCAT.FORMAT, metadata.getFormat());
        }
        if (metadata.getMediaType() != null) {
            model.add(metadata.getUri(), DCAT.MEDIA_TYPE,
                    metadata.getMediaType());
        }
        return getStatements(model);
    }

    /**
     *
     * Get RDF statements from dataRecord metadata object
     *
     * @param model RDF model with common metadata properties
     * @param metadata DataRecordMetadata object
     * @return List of RDF statements
     * @throws MetadataException
     */
    private static List<Statement> getStatements(Model model,
            DataRecordMetadata metadata)
            throws MetadataException {
        try {
            Preconditions.checkNotNull(metadata.getRmlURI(),
                    "Metadata rml mapping uri must not be null.");
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw (new MetadataException(ex.getMessage()));
        }

        LOGGER.info("Adding dataRecord metadata properties to the rdf model");
        model.add(metadata.getUri(), RDF.TYPE, DCAT.TYPE_DISTRIBUTION);
        if (metadata.getRmlURI() != null) {
            model.add(metadata.getUri(), FDP.RML_MAPPING, metadata.getRmlURI());
        }
        if (metadata.getDistributionURI() != null) {
            model.add(metadata.getUri(), FDP.REFERS_TO,
                    metadata.getDistributionURI());
        }
        if (metadata.getDataRecordIssued() != null) {
            model.add(metadata.getUri(), DCTERMS.ISSUED,
                    metadata.getDataRecordIssued());
        }
        if (metadata.getDataRecordModified() != null) {
            model.add(metadata.getUri(), DCTERMS.MODIFIED,
                    metadata.getDataRecordModified());
        }
        return getStatements(model);
    }

    /**
     * Get statements from the RDF model
     *
     * @param model RDF model with metadata properties
     * @return List of RDF statements
     */
    private static List<Statement> getStatements(Model model) {
        Iterator<Statement> it = model.iterator();
        List<Statement> statements = ImmutableList.copyOf(it);
        return statements;
    }

    /**
     * Set common metadata properties. (E.g) title, version etc
     *
     * @param model Empty RDF model
     */
    private static void setCommonProperties(Model model, Metadata metadata) {
        LOGGER.info("Adding common metadata properties to the  rdf model");
        model.add(metadata.getUri(), DCTERMS.TITLE, metadata.getTitle());
        model.add(metadata.getUri(), RDFS.LABEL, metadata.getTitle());
        model.add(metadata.getUri(), DCTERMS.HAS_VERSION,
                metadata.getVersion());
        if (metadata.getIssued() != null) {
            model.add(metadata.getUri(), FDP.METADATA_ISSUED,
                    metadata.getIssued());
        }
        if (metadata.getIdentifier() != null) {
            Identifier id = metadata.getIdentifier();
            model.add(metadata.getUri(), FDP.METADATA_IDENTIFIER, id.getUri());
            model.add(id.getUri(), RDF.TYPE, id.getType());
            model.add(id.getUri(), DCTERMS.IDENTIFIER, id.getIdentifier());
        }
        if (metadata.getModified() != null) {
            model.add(metadata.getUri(), FDP.METADATA_MODIFIED,
                    metadata.getModified());
        }
        if (metadata.getLanguage() != null) {
            model.add(metadata.getUri(), DCTERMS.LANGUAGE,
                    metadata.getLanguage());
        }
        if (metadata.getPublisher() != null) {
            Agent agent = metadata.getPublisher();
            model.add(metadata.getUri(), DCTERMS.PUBLISHER, agent.getUri());
            model.add(agent.getUri(), RDF.TYPE, agent.getType());
            if (agent.getName() == null) {
                String errMsg = "No publisher name provided";
                LOGGER.debug(errMsg);
            } else {
                model.add(agent.getUri(), FOAF.NAME, agent.getName());
            }
        }
        if (metadata.getLanguage() != null) {
            model.add(metadata.getUri(), DCTERMS.LANGUAGE,
                    metadata.getLanguage());
        }
        if (metadata.getDescription() != null) {
            model.add(metadata.getUri(), DCTERMS.DESCRIPTION,
                    metadata.getDescription());
        }
        if (metadata.getLicense() != null) {
            model.add(metadata.getUri(), DCTERMS.LICENSE,
                    metadata.getLicense());
        }
        if (metadata.getRights() != null) {
            model.add(metadata.getUri(), DCTERMS.RIGHTS, metadata.getRights());
        }
        if (metadata.getParentURI() != null) {
            model.add(metadata.getUri(), DCTERMS.IS_PART_OF, 
                    metadata.getParentURI());
        }
    }

    /**
     * Check if the metadata object contains mandatory metadata properties.
     *
     * @param metadata Subclass of Metadata object
     * @throws MetadataException Throws exceptions if a mandatory metadata
     * property is missing
     */
    private static void checkMandatoryProperties(Metadata metadata)
            throws MetadataException {
        Preconditions.checkNotNull(metadata.getIdentifier(),
                "Metadata ID must not be null.");
        Preconditions.checkNotNull(metadata.getIdentifier().getIdentifier(),
                "Metadata ID literal must not be null.");
        Preconditions.checkNotNull(metadata.getIdentifier().getUri(),
                "Metadata ID uri must not be null.");
        Preconditions.checkNotNull(metadata.getIdentifier().getType(),
                "Metadata ID type must not be null.");
        Preconditions.checkNotNull(metadata.getTitle(),
                "Metadata title must not be null.");
        Preconditions.checkNotNull(metadata.getVersion(),
                "Metadata version must not be null.");
        Preconditions.checkNotNull(metadata.getIssued(),
                "Metadata issued date must not be null.");
        Preconditions.checkNotNull(metadata.getModified(),
                "Metadata modified date must not be null.");
    }

    private static void propagateToHandler(List<Statement> statements,
            RDFHandler handler)
            throws RDFHandlerException, RepositoryException {
        handler.startRDF();
        handler.handleNamespace(RDF.PREFIX, RDF.NAMESPACE);
        handler.handleNamespace(RDFS.PREFIX, RDFS.NAMESPACE);
        handler.handleNamespace(DCAT.PREFIX, DCAT.NAMESPACE);
        handler.handleNamespace(XMLSchema.PREFIX, XMLSchema.NAMESPACE);
        handler.handleNamespace(OWL.PREFIX, OWL.NAMESPACE);
        handler.handleNamespace(DCTERMS.PREFIX, DCTERMS.NAMESPACE);
        handler.handleNamespace(FDP.PREFIX, FDP.NAMESPACE);
        handler.handleNamespace(R3D.PREFIX, R3D.NAMESPACE);
        handler.handleNamespace("lang",
                "http://id.loc.gov/vocabulary/iso639-1/");
        for (Statement st : statements) {
            handler.handleStatement(st);
        }
        handler.endRDF();
    }

}
