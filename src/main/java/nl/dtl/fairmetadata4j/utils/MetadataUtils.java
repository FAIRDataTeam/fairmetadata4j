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

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import nl.dtl.fairmetadata4j.io.MetadataException;
import nl.dtl.fairmetadata4j.model.Agent;
import nl.dtl.fairmetadata4j.model.CatalogMetadata;
import nl.dtl.fairmetadata4j.model.DataRecordMetadata;
import nl.dtl.fairmetadata4j.model.DatasetMetadata;
import nl.dtl.fairmetadata4j.model.DistributionMetadata;
import nl.dtl.fairmetadata4j.model.FDPMetadata;
import nl.dtl.fairmetadata4j.model.Identifier;
import nl.dtl.fairmetadata4j.model.Metadata;
import nl.dtl.fairmetadata4j.utils.vocabulary.FDP;
import nl.dtl.fairmetadata4j.utils.vocabulary.R3D;
import nl.dtl.fairmetadata4j.utils.vocabulary.SCHEMAORG;
import org.apache.logging.log4j.LogManager;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.DCAT;
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
 * Utils class to convert metadata object to RDF statements or string and
 * vice-verse
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-12
 * @version 0.1
 */
public class MetadataUtils {

    public static IRI R3D_INSTITUTIONCOUNTRY = SimpleValueFactory.getInstance()
            .createIRI(R3D.NAMESPACE, "institutionCountry");
    public static IRI R3D_LASTUPDATE = SimpleValueFactory.getInstance()
            .createIRI(R3D.NAMESPACE, "lastUpdate");
    public static IRI FDP_METADATAISSUED = SimpleValueFactory.getInstance()
            .createIRI(FDP.NAMESPACE, "metadataIssued");
    public static IRI FDP_METADATAMODIFIED = SimpleValueFactory.getInstance()
            .createIRI(FDP.NAMESPACE, "metadataModified");
    public static IRI FDP_METADATAIDENTIFIER = SimpleValueFactory.getInstance()
            .createIRI(FDP.NAMESPACE, "metadataIdentifier");
    public static IRI SCHEMAORG_FILE_FORMAT = SimpleValueFactory.getInstance()
            .createIRI(SCHEMAORG.NAMESPACE, "encodingFormat");

    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(MetadataUtils.class);

    /**
     * Get RDF statements from Metadata object
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
        List<Statement> stms = null;
        if (metadata instanceof FDPMetadata) {
            stms = getStatements(model, (FDPMetadata) metadata);
        } else if (metadata instanceof CatalogMetadata) {
            stms = getStatements(model, (CatalogMetadata) metadata);
        } else if (metadata instanceof DatasetMetadata) {
            stms = getStatements(model, (DatasetMetadata) metadata);
        } else if (metadata instanceof DistributionMetadata) {
            stms = getStatements(model, (DistributionMetadata) metadata);
        } else if (metadata instanceof DataRecordMetadata) {
            stms = getStatements(model, (DataRecordMetadata) metadata);
        }
        return stms;
    }

    /**
     * Convert Metadata object to RDF string
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
     * Convert Metadata object to RDF string for specific model
     *
     * @param <T>
     * @param metadata Subclass of metadata object
     * @param format RDF format
     * @param metadataModel Type of metadata model
     * @return RDF string
     * @throws MetadataException
     */
    public static <T extends Metadata> String getString(@Nonnull T metadata,
            @Nonnull RDFFormat format, int metadataModel)
            throws MetadataException {
        Preconditions.checkNotNull(metadata,
                "Metadata object must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");
        StringWriter sw = new StringWriter();
        RDFWriter writer = Rio.createWriter(format, sw);
        List<Statement> statement = getStatements((DatasetMetadata) metadata,
                metadataModel);
        try {
            propagateToHandler(statement, writer);
        } catch (RepositoryException | RDFHandlerException ex) {
            LOGGER.error("Error reading RDF statements");
            throw (new MetadataException(ex.getMessage()));
        }
        return sw.toString();
    }

    /**
     * Get RDF statements from Metadata object
     *
     * @param <T>
     * @param metadata Subclass of metadata object
     * @param metadataModel Metadata transformation type
     * @return List of RDF statements
     * @throws MetadataException
     */
    public static <T extends Metadata> List<Statement> getStatements(
            @Nonnull T metadata, int metadataModel) throws MetadataException {
        Preconditions.checkNotNull(metadata,
                "Metadata object must not be null.");
        try {
            checkMandatoryProperties(metadata);
        } catch (NullPointerException ex) {
            throw (new MetadataException(ex.getMessage()));
        }
        Model model = new LinkedHashModel();
        LOGGER.info("Creating metadata rdf model");
        List<Statement> stms = null;
        if (metadata instanceof FDPMetadata) {
            stms = getStatements(model, (FDPMetadata) metadata);
        } else if (metadata instanceof CatalogMetadata) {
            stms = getStatements(model, (CatalogMetadata) metadata);
        } else if (metadata instanceof DatasetMetadata) {
            stms = getStatements(model, (DatasetMetadata) metadata,
                    metadataModel);
        } else if (metadata instanceof DistributionMetadata) {
            stms = getStatements(model, (DistributionMetadata) metadata, 
                    metadataModel);
        } else if (metadata instanceof DataRecordMetadata) {
            stms = getStatements(model, (DataRecordMetadata) metadata);
        }
        return stms;
    }

    private static List<Statement> getStatements(Model model, 
            DatasetMetadata metadata, int metadataModel) {
        if (metadataModel == MetadataModels.SCHEMA_DOT_ORG) {
            LOGGER.info("Adding schema.org based dataset metadata "
                    + "properties to the rdf model");
            addStatement(model, metadata.getUri(), RDF.TYPE, SCHEMAORG.DATASET);
            addStatement(model, metadata.getUri(), SCHEMAORG.NAME,
                    metadata.getTitle());
            addStatement(model, metadata.getUri(), SCHEMAORG.DESCRIPTION,
                    metadata.getDescription());
            addStatement(model, metadata.getUri(), SCHEMAORG.URL,
                    metadata.getUri());
            metadata.getKeywords().stream().forEach((keyword) -> {
                addStatement(model, metadata.getUri(), SCHEMAORG.KEYWORDS,
                        keyword);
            });
            addStatement(model, metadata.getUri(),
                    SCHEMAORG.INCLUDEDINDATACATALOG, metadata.getRights());
            addAgentStatements(model, metadata.getUri(), SCHEMAORG.CREATOR,
                    metadata.getPublisher());
            metadata.getDistributions().stream().forEach((distribution) -> {
                addStatement(model, metadata.getUri(), SCHEMAORG.DISTRIBUTION,
                        distribution);
            });
        }
        return getStatements(model);
    }
    
    private static List<Statement> getStatements(Model model, 
            DistributionMetadata metadata, int metadataModel) {
        if (metadataModel == MetadataModels.SCHEMA_DOT_ORG) {
            LOGGER.info("Adding schema.org based distribution metadata "
                    + "properties to the rdf model");
            // Add type later on
            addStatement(model, metadata.getUri(), SCHEMAORG.NAME,
                    metadata.getTitle());
            addStatement(model, metadata.getUri(), SCHEMAORG.DESCRIPTION,
                    metadata.getDescription());
            addStatement(model, metadata.getUri(), SCHEMAORG.URL,
                    metadata.getUri());
            addStatement(model, metadata.getUri(), SCHEMAORG_FILE_FORMAT,
                    metadata.getMediaType());
            IRI contentLocation = metadata.getDownloadURL();
            if(metadata.getAccessURL() != null) {
                contentLocation = metadata.getAccessURL();
            }
            addStatement(model, metadata.getUri(), SCHEMAORG.CONTENTLOCATION,
                    contentLocation);
            addAgentStatements(model, metadata.getUri(), SCHEMAORG.CREATOR,
                    metadata.getPublisher());
        }
        return getStatements(model);
    }

    /**
     * Get RDF statements from Repository metadata object
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
        addStatement(model, metadata.getUri(), RDF.TYPE, R3D.REPOSITORY);
        IRI swaggerURL = f.createIRI(
                metadata.getUri().toString() + "/swagger-ui.html");
        metadata.setSwaggerDoc(swaggerURL);
        addStatement(model, metadata.getUri(), RDFS.SEEALSO,
                metadata.getSwaggerDoc());
        addIdStatements(model, metadata.getUri(), R3D.REPOSITORYIDENTIFIER,
                metadata.getRepostoryIdentifier());
        addStatement(model, metadata.getUri(), R3D_INSTITUTIONCOUNTRY,
                metadata.getInstitutionCountry());
        addStatement(model, metadata.getUri(), R3D_LASTUPDATE,
                metadata.getLastUpdate());
        addStatement(model, metadata.getUri(), R3D.STARTDATE,
                metadata.getStartDate());
        metadata.getCatalogs().stream().forEach((catalog) -> {
            addStatement(model, metadata.getUri(), R3D.DATACATALOG, catalog);
        });
        addAgentStatements(model, metadata.getUri(), R3D.HAS_INSTITUTION,
                metadata.getInstitution());
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
        addStatement(model, metadata.getUri(), RDF.TYPE, DCAT.CATALOG);
        addStatement(model, metadata.getUri(), FOAF.HOMEPAGE,
                metadata.getHomepage());
        addStatement(model, metadata.getUri(), DCTERMS.ISSUED,
                metadata.getCatalogIssued());
        addStatement(model, metadata.getUri(), DCTERMS.MODIFIED,
                metadata.getCatalogModified());
        metadata.getThemeTaxonomys().stream().forEach((themeTax) -> {
            addStatement(model, metadata.getUri(), DCAT.THEME_TAXONOMY,
                    themeTax);
        });
        metadata.getDatasets().stream().forEach((dataset) -> {
            addStatement(model, metadata.getUri(), DCAT.DATASET, dataset);
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
        LOGGER.info("Adding dcat based dataset metadata "
                + "properties to the rdf model");
        addStatement(model, metadata.getUri(), RDF.TYPE, DCAT.DATASET);
        addStatement(model, metadata.getUri(), DCAT.CONTACT_POINT,
                metadata.getContactPoint());
        addStatement(model, metadata.getUri(), DCAT.LANDING_PAGE,
                metadata.getLandingPage());
        addStatement(model, metadata.getUri(), DCTERMS.ISSUED,
                metadata.getDatasetIssued());
        addStatement(model, metadata.getUri(), DCTERMS.MODIFIED,
                metadata.getDatasetModified());
        metadata.getThemes().stream().forEach((theme) -> {
            addStatement(model, metadata.getUri(), DCAT.THEME, theme);
        });
        metadata.getKeywords().stream().forEach((keyword) -> {
            addStatement(model, metadata.getUri(), DCAT.KEYWORD, keyword);
        });
        metadata.getDistributions().stream().forEach((distribution) -> {
            addStatement(model, metadata.getUri(), DCAT.DISTRIBUTION,
                    distribution);
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
        addStatement(model, metadata.getUri(), RDF.TYPE,
                DCAT.DISTRIBUTION);
        addStatement(model, metadata.getUri(), DCAT.ACCESS_URL,
                metadata.getAccessURL());
        addStatement(model, metadata.getUri(), DCAT.DOWNLOAD_URL,
                metadata.getDownloadURL());
        addStatement(model, metadata.getUri(), DCTERMS.ISSUED,
                metadata.getDistributionIssued());
        addStatement(model, metadata.getUri(), DCTERMS.MODIFIED,
                metadata.getDistributionModified());
        addStatement(model, metadata.getUri(), DCAT.BYTE_SIZE,
                metadata.getByteSize());
        addStatement(model, metadata.getUri(), DCTERMS.FORMAT,
                metadata.getFormat());
        addStatement(model, metadata.getUri(), DCAT.MEDIA_TYPE,
                metadata.getMediaType());
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
        addStatement(model, metadata.getUri(), RDF.TYPE,
                DCAT.DISTRIBUTION);
        addStatement(model, metadata.getUri(), FDP.RMLMAPPING,
                metadata.getRmlURI());
        addStatement(model, metadata.getUri(), FDP.REFERSTO,
                metadata.getDistributionURI());
        addStatement(model, metadata.getUri(), DCTERMS.ISSUED,
                metadata.getDataRecordIssued());
        addStatement(model, metadata.getUri(), DCTERMS.MODIFIED,
                metadata.getDataRecordModified());
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
        addStatement(model, metadata.getUri(), DCTERMS.TITLE,
                metadata.getTitle());
        addStatement(model, metadata.getUri(), RDFS.LABEL, metadata.getTitle());
        addStatement(model, metadata.getUri(), DCTERMS.HAS_VERSION,
                metadata.getVersion());
        addStatement(model, metadata.getUri(), FDP_METADATAISSUED,
                metadata.getIssued());
        addIdStatements(model, metadata.getUri(), FDP_METADATAIDENTIFIER,
                metadata.getIdentifier());
        addStatement(model, metadata.getUri(), FDP_METADATAMODIFIED,
                metadata.getModified());
        addStatement(model, metadata.getUri(), DCTERMS.LANGUAGE,
                metadata.getLanguage());
        addAgentStatements(model, metadata.getUri(), DCTERMS.PUBLISHER,
                metadata.getPublisher());
        addStatement(model, metadata.getUri(), DCTERMS.LANGUAGE,
                metadata.getLanguage());
        addStatement(model, metadata.getUri(), DCTERMS.DESCRIPTION,
                metadata.getDescription());
        addStatement(model, metadata.getUri(), DCTERMS.LICENSE,
                metadata.getLicense());
        addStatement(model, metadata.getUri(), DCTERMS.RIGHTS,
                metadata.getRights());
        addStatement(model, metadata.getUri(), DCTERMS.IS_PART_OF,
                metadata.getParentURI());
        addStatement(model, metadata.getUri(), DCTERMS.CONFORMS_TO,
                metadata.getSpecification());
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

    // We are using this method to reduce the NPath complexity 
    /**
     * Add id instance's rdf statements
     *
     * @param model
     * @param subj
     * @param pred
     * @param objc
     */
    private static void addIdStatements(Model model, IRI subj, IRI pred,
            Identifier objc) {
        if (objc != null) {
            addStatement(model, subj, pred, objc.getUri());
            addStatement(model, objc.getUri(), RDF.TYPE, objc.getType());
            addStatement(model, objc.getUri(), DCTERMS.IDENTIFIER,
                    objc.getIdentifier());
        }
    }

    // We are using this method to reduce the NPath complexity 
    /**
     * Add agent instance's rdf statements
     *
     * @param model
     * @param subj
     * @param pred
     * @param objc
     */
    private static void addAgentStatements(Model model, IRI subj, IRI pred,
            Agent objc, int... metadataModel) {
        if (objc != null) {
            addStatement(model, subj, pred, objc.getUri());
            if (metadataModel.length > 0 && metadataModel[0] == 
                    MetadataModels.SCHEMA_DOT_ORG) {
                addStatement(model, objc.getUri(), SCHEMAORG.URL,
                        objc.getUri());
                if (objc.getName() != null) {
                    addStatement(model, objc.getUri(), SCHEMAORG.NAME,
                            objc.getName());
                }
            } else {
                addStatement(model, objc.getUri(), RDF.TYPE, objc.getType());
                if (objc.getName() == null) {
                    String errMsg = "No publisher name provided";
                    LOGGER.info(errMsg);
                } else {
                    addStatement(model, objc.getUri(), FOAF.NAME,
                            objc.getName());
                }
            }
        }
    }

    // We are using this method to reduce the NPath complexity 
    /**
     * Add rdf statement
     *
     * @param model
     * @param subj
     * @param pred
     * @param objc
     */
    private static void addStatement(Model model, IRI subj, IRI pred, Value objc) {
        if (objc != null) {
            model.add(subj, pred, objc);
        }
    }

}
