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
import nl.dtl.fairmetadata.model.CatalogMetadata;
import nl.dtl.fairmetadata.model.DataRecordMetadata;
import nl.dtl.fairmetadata.model.DatasetMetadata;
import nl.dtl.fairmetadata.model.DistributionMetadata;
import nl.dtl.fairmetadata.model.FDPMetadata;
import nl.dtl.fairmetadata.model.Metadata;
import nl.dtl.fairmetadata.utils.vocabulary.DCAT;
import nl.dtl.fairmetadata.utils.vocabulary.FDP;
import nl.dtl.fairmetadata.utils.vocabulary.R3D;
import org.apache.logging.log4j.LogManager;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.OWL;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;

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
        checkMandatoryProperties(metadata);
        org.openrdf.model.Model model = new LinkedHashModel();
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
        Preconditions.checkNotNull(format,
                "RDF format must not be null.");
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
    private static List<Statement> getStatements(org.openrdf.model.Model model,
            FDPMetadata metadata)
            throws MetadataException {
        LOGGER.info("Adding FDP metadata properties to the rdf model");
        model.add(metadata.getUri(), RDF.TYPE, R3D.TYPE_REPOSTORY);
        URI swaggerURL = new URIImpl(
                metadata.getUri().toString() + "/swagger-ui.html");
        model.add(metadata.getUri(), RDFS.SEEALSO, swaggerURL);
        if (metadata.getHomepage() != null) {
            model.add(metadata.getUri(), FOAF.HOMEPAGE, metadata.getHomepage());
        }
        metadata.getCatalogs().stream().forEach((catalog) -> {
            model.add(metadata.getUri(), R3D.DATA_CATALOG, catalog);
        });
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
    private static List<Statement> getStatements(org.openrdf.model.Model model,
            CatalogMetadata metadata)
            throws MetadataException {
        if (metadata.getThemeTaxonomy() == null
                || metadata.getThemeTaxonomy().isEmpty()) {
            String errMsg = "No dcat:themeTaxonomy provided";
            LOGGER.error(errMsg);
            throw (new MetadataException(errMsg));
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
        metadata.getThemeTaxonomy().stream().forEach((themeTax) -> {
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
    private static List<Statement> getStatements(org.openrdf.model.Model model,
            DatasetMetadata metadata)
            throws MetadataException {
        if (metadata.getThemes() == null || metadata.getThemes().isEmpty()) {
            String errMsg = "No dcat:theme provided";
            LOGGER.error(errMsg);
            throw (new MetadataException(errMsg));
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
        metadata.getDistribution().stream().forEach((distribution) -> {
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
    private static List<Statement> getStatements(org.openrdf.model.Model model,
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
    private static List<Statement> getStatements(org.openrdf.model.Model model,
            DataRecordMetadata metadata)
            throws MetadataException {
        if (metadata.getRmlURI() == null) {
            String errMsg
                    = "No fdp:rmlMapping URL is provided";
            LOGGER.error(errMsg);
            throw (new MetadataException(errMsg));
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
    private static List<Statement> getStatements(
            org.openrdf.model.Model model) {
        Iterator<Statement> it = model.iterator();
        List<Statement> statements = ImmutableList.copyOf(it);
        return statements;
    }

    /**
     * Set common metadata properties. (E.g) title, version etc
     *
     * @param model Empty RDF model
     */
    private static void setCommonProperties(org.openrdf.model.Model model,
            Metadata metadata) {
        LOGGER.info("Adding common metadata properties to the  rdf model");
        model.add(metadata.getUri(), DCTERMS.TITLE, metadata.getTitle());
        model.add(metadata.getUri(), RDFS.LABEL, metadata.getTitle());
        model.add(metadata.getUri(), DCTERMS.IDENTIFIER,
                metadata.getIdentifier());
        model.add(metadata.getUri(), DCTERMS.HAS_VERSION,
                metadata.getVersion());
        if (metadata.getIssued() != null) {
            model.add(metadata.getUri(), FDP.METADATA_ISSUED, 
                    metadata.getIssued());
        }
        if (metadata.getModified() != null) {
            model.add(metadata.getUri(), FDP.METADATA_MODIFIED,
                    metadata.getModified());
        }
        if (metadata.getLanguage() != null) {
            model.add(metadata.getUri(), DCTERMS.LANGUAGE,
                    metadata.getLanguage());
        }
        if (!metadata.getPublisher().isEmpty()) {
            metadata.getPublisher().stream().forEach((publisher) -> {
                model.add(metadata.getUri(), DCTERMS.PUBLISHER, publisher);
            });
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
        if (metadata.getVersion() == null) {
            String errMsg = "No version number provided";
            LOGGER.error(errMsg);
            throw (new MetadataException(errMsg));
        } else if (metadata.getTitle() == null) {
            String errMsg = "No title or label provided";
            LOGGER.error(errMsg);
            throw (new MetadataException(errMsg));
        } else if (metadata.getIdentifier() == null) {
            String errMsg = "No identifier provided";
            LOGGER.error(errMsg);
            throw (new MetadataException(errMsg));
        } else if (metadata.getIssued() == null) {
            String errMsg = "No issued date provided";
            LOGGER.error(errMsg);
            throw (new MetadataException(errMsg));
        } else if (metadata.getModified() == null) {
            String errMsg = "No modified date provided";
            LOGGER.error(errMsg);
            throw (new MetadataException(errMsg));
        }
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
