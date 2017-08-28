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
import nl.dtl.fairmetadata4j.model.AccessRights;
import nl.dtl.fairmetadata4j.model.Agent;
import nl.dtl.fairmetadata4j.model.Authorization;
import nl.dtl.fairmetadata4j.model.CatalogMetadata;
import nl.dtl.fairmetadata4j.model.DataRecordMetadata;
import nl.dtl.fairmetadata4j.model.DatasetMetadata;
import nl.dtl.fairmetadata4j.model.DistributionMetadata;
import nl.dtl.fairmetadata4j.model.FDPMetadata;
import nl.dtl.fairmetadata4j.model.Identifier;
import nl.dtl.fairmetadata4j.model.Metadata;
import nl.dtl.fairmetadata4j.utils.vocabulary.DATADOWNLOAD;
import nl.dtl.fairmetadata4j.utils.vocabulary.FDP;
import nl.dtl.fairmetadata4j.utils.vocabulary.R3D;
import nl.dtl.fairmetadata4j.utils.vocabulary.SCHEMAORG;
import nl.dtl.fairmetadata4j.utils.vocabulary.WebAccessControl;
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
import org.eclipse.rdf4j.rio.helpers.JSONLDMode;
import org.eclipse.rdf4j.rio.helpers.JSONLDSettings;

/**
 * Utils class to convert metadata object to RDF statements or string and vice-verse
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-12
 * @version 0.1
 */
public class MetadataUtils {

    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(MetadataUtils.class);
    /**
     * To get metadata content according to dcat vocabulary
     */
    public static final int DCAT_MODEL = 0;
    /**
     * To get metadata content according to schema.org vocabulary
     */
    public static final int SCHEMA_DOT_ORG_MODEL = 1;

    /**
     * Convert Metadata object to RDF string for specific model
     *
     * @param <T> Subclasses of Metadata object
     * @param metadata Metadata object
     * @param format RDF format
     * @return RDF string RDF statements as a string object
     * @throws MetadataException This exception is thrown if the mandatory metadata properties are
     * missing
     */
    public static <T extends Metadata> String getString(@Nonnull T metadata,
            @Nonnull RDFFormat format) throws MetadataException {
        return getString(metadata, format, DCAT_MODEL, true);
    }

    /**
     * Convert Metadata object to RDF string for specific model
     *
     * @param <T> Subclasses of Metadata object
     * @param metadata Metadata object
     * @param format RDF format
     * @param modelType Type of metadata model, default is dcat model
     * @return RDF string RDF statements as a string object
     * @throws MetadataException This exception is thrown if the mandatory metadata properties are
     * missing
     */
    public static <T extends Metadata> String getString(@Nonnull T metadata,
            @Nonnull RDFFormat format, int modelType) throws MetadataException {
        return getString(metadata, format, modelType, true);
    }

    /**
     * Convert Metadata object to RDF string for specific model
     *
     * @param <T> Subclasses of Metadata object
     * @param metadata Metadata object
     * @param format RDF format
     * @param checkFlag To indicate if mandatory check need to be performed
     * @return RDF string RDF statements as a string object
     * @throws MetadataException This exception is thrown if the mandatory metadata properties are
     * missing
     */
    public static <T extends Metadata> String getString(@Nonnull T metadata,
            @Nonnull RDFFormat format, boolean checkFlag) throws MetadataException {
        return getString(metadata, format, DCAT_MODEL, checkFlag);
    }

    /**
     * Convert Metadata object to RDF string for specific model
     *
     * @param <T> Subclasses of Metadata object
     * @param metadata Metadata object
     * @param format RDF format
     * @param metadataModel Type of metadata model
     * @param checkFlag To indicate if mandatory check need to be performed
     * @return RDF string RDF statements as a string object
     * @throws MetadataException This exception is thrown if the mandatory metadata properties are
     * missing
     */
    public static <T extends Metadata> String getString(@Nonnull T metadata,
            @Nonnull RDFFormat format, int metadataModel, boolean checkFlag)
            throws MetadataException {
        Preconditions.checkNotNull(metadata, "Metadata object must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");
        StringWriter sw = new StringWriter();
        RDFWriter writer = Rio.createWriter(format, sw);
        writer.getWriterConfig().set(JSONLDSettings.JSONLD_MODE, JSONLDMode.COMPACT);
        List<Statement> statement = getStatements(metadata, metadataModel, checkFlag);
        try {
            propagateToHandler(statement, writer);
        } catch (RepositoryException | RDFHandlerException ex) {
            LOGGER.error("Error reading RDF statements");
            String errMsg = ex.getMessage();
            throw new MetadataException(errMsg);
        }
        return sw.toString();
    }

    /**
     * Get RDF statements from Metadata object
     *
     * @param <T> Subclasses of Metadata object
     * @param metadata Metadata object
     * @return List of RDF statements
     * @throws MetadataException This exception is thrown if the mandatory metadata properties are
     * missing
     */
    public static <T extends Metadata> List<Statement> getStatements(@Nonnull T metadata)
            throws MetadataException {
        return getStatements(metadata, DCAT_MODEL, true);
    }

    /**
     * Get RDF statements from Metadata object
     *
     * @param <T> Subclasses of Metadata object
     * @param metadata Metadata object
     * @param metadataModel Metadata model to use
     * @return List of RDF statements
     * @throws MetadataException This exception is thrown if the mandatory metadata properties are
     * missing
     */
    public static <T extends Metadata> List<Statement> getStatements(@Nonnull T metadata,
            int metadataModel) throws MetadataException {
        return getStatements(metadata, metadataModel, true);
    }

    /**
     * Get RDF statements from Metadata object
     *
     * @param <T> Subclasses of Metadata object
     * @param metadata Metadata object
     * @param checkFlag To indicate if mandatory fields are need to checked
     * @return List of RDF statements
     * @throws MetadataException This exception is thrown if the mandatory metadata properties are
     * missing
     */
    public static <T extends Metadata> List<Statement> getStatements(@Nonnull T metadata,
            boolean checkFlag) throws MetadataException {
        return getStatements(metadata, DCAT_MODEL, checkFlag);
    }

    /**
     * Get RDF statements from Metadata object
     *
     * @param <T> Subclasses of Metadata object
     * @param metadata Metadata object
     * @param metadataModel Metadata model to use
     * @param checkFields To indicate if mandatory fields are need to checked
     * @return List of RDF statements
     * @throws MetadataException This exception is thrown if the mandatory metadata properties are
     * missing
     */
    public static <T extends Metadata> List<Statement> getStatements(@Nonnull T metadata,
            int metadataModel, boolean checkFields) throws MetadataException {
        Preconditions.checkNotNull(metadata, "Metadata object must not be null.");
        try {
            if (checkFields) {
                checkMandatoryProperties(metadata);
                checkDomainProperties(metadata);
            } else {
                LOGGER.warn("Fields check is skipped. The generated metadata stmts might not be "
                        + "complete");
            }

        } catch (NullPointerException ex) {
            String errMsg = ex.getMessage();
            throw new MetadataException(errMsg);
        }
        Model model = new LinkedHashModel();
        LOGGER.info("Creating metadata rdf model");
        List<Statement> stms = null;
        if (metadataModel == MetadataUtils.SCHEMA_DOT_ORG_MODEL) {
            if (metadata instanceof FDPMetadata) {
                stms = getStatements(model, (FDPMetadata) metadata);
            } else if (metadata instanceof CatalogMetadata) {
                stms = getStatements(model, (CatalogMetadata) metadata);
            } else if (metadata instanceof DatasetMetadata) {
                stms = getStatements(model, (DatasetMetadata) metadata,
                        MetadataUtils.SCHEMA_DOT_ORG_MODEL);
            } else if (metadata instanceof DistributionMetadata) {
                stms = getStatements(model, (DistributionMetadata) metadata,
                        MetadataUtils.SCHEMA_DOT_ORG_MODEL);
            } else if (metadata instanceof DataRecordMetadata) {
                stms = getStatements(model, (DataRecordMetadata) metadata);
            }
        } else {
            setCommonProperties(model, metadata);
            LOGGER.info("Adding specific metadata properties to the rdf model");
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
        }
        return stms;
    }

    private static List<Statement> getStatements(Model model, DatasetMetadata metadata,
            int metadataModel) {
        if (metadataModel == MetadataUtils.SCHEMA_DOT_ORG_MODEL) {
            LOGGER.info("Adding schema.org based dataset metadata properties to the rdf model");
            addStatement(model, metadata.getUri(), RDF.TYPE, SCHEMAORG.DATASET);
            addStatement(model, metadata.getUri(), SCHEMAORG.NAME, metadata.getTitle());
            addStatement(model, metadata.getUri(), SCHEMAORG.DESCRIPTION,
                    metadata.getDescription());
            addStatements(model, metadata.getUri(), SCHEMAORG.KEYWORDS, metadata.getKeywords());
            addStatement(model, metadata.getUri(), SCHEMAORG.INCLUDEDINDATACATALOG,
                    metadata.getRights());
            addAgentStatements(model, metadata.getUri(), SCHEMAORG.CREATOR, metadata.getPublisher(),
                    metadataModel);
            addStatements(model, metadata.getUri(), SCHEMAORG.DISTRIBUTION,
                    metadata.getDistributions());
        }
        return getStatements(model);
    }

    private static List<Statement> getStatements(Model model, DistributionMetadata metadata,
            int metadataModel) {
        if (metadataModel == MetadataUtils.SCHEMA_DOT_ORG_MODEL) {
            LOGGER.info("Adding schema.org based distribution metadata properties to the rdf "
                    + "model");
            addStatement(model, metadata.getUri(), SCHEMAORG.NAME, metadata.getTitle());
            addStatement(model, metadata.getUri(), SCHEMAORG.DESCRIPTION,
                    metadata.getDescription());
            addStatement(model, metadata.getUri(),
                    nl.dtl.fairmetadata4j.utils.SCHEMAORG.FILE_FORMAT, metadata.getMediaType());
            /**
             * We don't have type definition for access URL's so in the current implementation we
             * have type definition only for the downloadUrl's. We use Thing as type definition for
             * accessUrl
             */
            IRI contentLocation = metadata.getDownloadURL();
            IRI type = DATADOWNLOAD.DATADOWNLOAD;
            if (contentLocation == null && metadata.getAccessURL() != null) {
                contentLocation = metadata.getDownloadURL();
                type = SCHEMAORG.THING;
            }
            addStatement(model, metadata.getUri(), RDF.TYPE, type);
            addStatement(model, metadata.getUri(), SCHEMAORG.CONTENTLOCATION, contentLocation);
            addAgentStatements(model, metadata.getUri(), SCHEMAORG.CREATOR, metadata.getPublisher(),
                    metadataModel);
        }
        return getStatements(model);
    }

    /**
     * Get RDF statements from Repository metadata object
     *
     * @param model RDF model with common metadata properties
     * @param metadata FDPMetadata object
     * @return List of RDF statements
     */
    private static List<Statement> getStatements(Model model, FDPMetadata metadata) {
        LOGGER.info("Adding FDP metadata properties to the rdf model");
        ValueFactory f = SimpleValueFactory.getInstance();
        addStatement(model, metadata.getUri(), RDF.TYPE, R3D.REPOSITORY);
        IRI swaggerURL = f.createIRI(metadata.getUri().toString() + "/swagger-ui.html");
        metadata.setSwaggerDoc(swaggerURL);
        addStatement(model, metadata.getUri(), RDFS.SEEALSO, metadata.getSwaggerDoc());
        addIdStatements(model, metadata.getUri(), R3D.REPOSITORYIDENTIFIER,
                metadata.getRepostoryIdentifier());
        addStatement(model, metadata.getUri(), nl.dtl.fairmetadata4j.utils.R3D.INSTITUTIONCOUNTRY,
                metadata.getInstitutionCountry());
        addStatement(model, metadata.getUri(), nl.dtl.fairmetadata4j.utils.R3D.LASTUPDATE,
                metadata.getLastUpdate());
        addStatement(model, metadata.getUri(), R3D.STARTDATE, metadata.getStartDate());
        addStatements(model, metadata.getUri(), R3D.DATACATALOG, metadata.getCatalogs());
        addAgentStatements(model, metadata.getUri(), R3D.HAS_INSTITUTION, metadata.getInstitution(),
                DCAT_MODEL);
        return getStatements(model);
    }

    /**
     *
     * Get RDF statements from catalog metadata object
     *
     * @param model RDF model with common metadata properties
     * @param metadata CatalogMetadata object
     * @return List of RDF statements
     */
    private static List<Statement> getStatements(Model model, CatalogMetadata metadata) {
        LOGGER.info("Adding catalogy metadata properties to the rdf model");
        addStatement(model, metadata.getUri(), RDF.TYPE, DCAT.CATALOG);
        addStatement(model, metadata.getUri(), FOAF.HOMEPAGE, metadata.getHomepage());
        addStatement(model, metadata.getUri(), DCTERMS.ISSUED, metadata.getCatalogIssued());
        addStatement(model, metadata.getUri(), DCTERMS.MODIFIED, metadata.getCatalogModified());
        addStatements(model, metadata.getUri(), DCAT.THEME_TAXONOMY, metadata.getThemeTaxonomys());
        addStatements(model, metadata.getUri(), DCAT.HAS_DATASET, metadata.getDatasets());
        return getStatements(model);
    }

    /**
     *
     * Get RDF statements from dataset metadata object
     *
     * @param model RDF model with common metadata properties
     * @param metadata DatasetMetadata object
     * @return List of RDF statements
     */
    private static List<Statement> getStatements(Model model, DatasetMetadata metadata) {
        LOGGER.info("Adding dcat based dataset metadata properties to the rdf model");
        addStatement(model, metadata.getUri(), RDF.TYPE, DCAT.DATASET);
        addStatement(model, metadata.getUri(), DCAT.CONTACT_POINT, metadata.getContactPoint());
        addStatement(model, metadata.getUri(), DCAT.LANDING_PAGE, metadata.getLandingPage());
        addStatement(model, metadata.getUri(), DCTERMS.ISSUED, metadata.getDatasetIssued());
        addStatement(model, metadata.getUri(), DCTERMS.MODIFIED, metadata.getDatasetModified());
        addStatements(model, metadata.getUri(), DCAT.KEYWORD, metadata.getKeywords());
        addStatements(model, metadata.getUri(), DCAT.THEME, metadata.getThemes());
        addStatements(model, metadata.getUri(), DCAT.HAS_DISTRIBUTION, metadata.getDistributions());
        return getStatements(model);
    }

    /**
     *
     * Get RDF statements from distribution metadata object
     *
     * @param model RDF model with common metadata properties
     * @param metadata DistributionMetadata object
     * @return List of RDF statements
     */
    private static List<Statement> getStatements(Model model, DistributionMetadata metadata) {
        LOGGER.info("Adding distrubution metadata properties to the rdf model");
        addStatement(model, metadata.getUri(), RDF.TYPE, DCAT.DISTRIBUTION);
        addStatement(model, metadata.getUri(), DCAT.ACCESS_URL, metadata.getAccessURL());
        addStatement(model, metadata.getUri(), DCAT.DOWNLOAD_URL, metadata.getDownloadURL());
        addStatement(model, metadata.getUri(), DCTERMS.ISSUED, metadata.getDistributionIssued());
        addStatement(model, metadata.getUri(), DCTERMS.MODIFIED,
                metadata.getDistributionModified());
        addStatement(model, metadata.getUri(), DCAT.BYTE_SIZE, metadata.getByteSize());
        addStatement(model, metadata.getUri(), DCTERMS.FORMAT, metadata.getFormat());
        addStatement(model, metadata.getUri(), DCAT.MEDIA_TYPE, metadata.getMediaType());
        return getStatements(model);
    }

    /**
     *
     * Get RDF statements from dataRecord metadata object
     *
     * @param model RDF model with common metadata properties
     * @param metadata DataRecordMetadata object
     * @return List of RDF statements
     */
    private static List<Statement> getStatements(Model model, DataRecordMetadata metadata) {
        LOGGER.info("Adding dataRecord metadata properties to the rdf model");
        addStatement(model, metadata.getUri(), RDF.TYPE, DCAT.DISTRIBUTION);
        addStatement(model, metadata.getUri(), FDP.RMLMAPPING, metadata.getRmlURI());
        addStatement(model, metadata.getUri(), FDP.RMLINPUTSOURCE, metadata.getRmlInputSourceURI());
        addStatement(model, metadata.getUri(), DCTERMS.ISSUED, metadata.getDataRecordIssued());
        addStatement(model, metadata.getUri(), DCTERMS.MODIFIED, metadata.getDataRecordModified());
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
        LOGGER.info("Adding common metadata properties to the rdf model");
        addStatement(model, metadata.getUri(), DCTERMS.TITLE, metadata.getTitle());
        addStatement(model, metadata.getUri(), RDFS.LABEL, metadata.getTitle());
        addStatement(model, metadata.getUri(), DCTERMS.HAS_VERSION, metadata.getVersion());
        addStatement(model, metadata.getUri(), FDP.METADATAISSUED, metadata.getIssued());
        addIdStatements(model, metadata.getUri(), FDP.METADATAIDENTIFIER, metadata.getIdentifier());
        addStatement(model, metadata.getUri(), FDP.METADATAMODIFIED, metadata.getModified());
        addStatement(model, metadata.getUri(), DCTERMS.LANGUAGE, metadata.getLanguage());
        addAgentStatements(model, metadata.getUri(), DCTERMS.PUBLISHER, metadata.getPublisher(),
                DCAT_MODEL);
        addAccessRightsStatements(model, metadata.getUri(), DCTERMS.ACCESS_RIGHTS,
                metadata.getAccessRights(), DCAT_MODEL);
        addStatement(model, metadata.getUri(), DCTERMS.LANGUAGE, metadata.getLanguage());
        addStatement(model, metadata.getUri(), DCTERMS.DESCRIPTION, metadata.getDescription());
        addStatement(model, metadata.getUri(), DCTERMS.LICENSE, metadata.getLicense());
        addStatement(model, metadata.getUri(), DCTERMS.RIGHTS, metadata.getRights());
        addStatement(model, metadata.getUri(), DCTERMS.IS_PART_OF, metadata.getParentURI());
        addStatement(model, metadata.getUri(), DCTERMS.CONFORMS_TO, metadata.getSpecification());
    }

    /**
     * Check if the metadata object contains mandatory metadata properties.
     *
     * @param metadata Subclass of Metadata object
     * @throws MetadataException Throws exceptions if a mandatory metadata property is missing
     */
    private static void checkMandatoryProperties(Metadata metadata) throws MetadataException {
        Preconditions.checkNotNull(metadata.getIdentifier(), "Metadata ID must not be null.");
        Preconditions.checkNotNull(metadata.getIdentifier().getIdentifier(),
                "Metadata ID literal must not be null.");
        Preconditions.checkNotNull(metadata.getIdentifier().getUri(),
                "Metadata ID uri must not be null.");
        Preconditions.checkNotNull(metadata.getIdentifier().getType(),
                "Metadata ID type must not be null.");
        Preconditions.checkNotNull(metadata.getTitle(), "Metadata title must not be null.");
        Preconditions.checkNotNull(metadata.getVersion(), "Metadata version must not be null.");
        Preconditions.checkNotNull(metadata.getIssued(), "Metadata issued date must not be null.");
        Preconditions.checkNotNull(metadata.getModified(),
                "Metadata modified date must not be null.");
        Preconditions.checkNotNull(metadata.getPublisher(), "Metadata publisher must not be null.");
    }

    /**
     * Check if the metadata object contains domain specific metadata properties.
     *
     * @param metadata Subclass of Metadata object
     * @throws MetadataException Throws exceptions if a mandatory metadata property is missing
     */
    private static <T extends Metadata> void checkDomainProperties(@Nonnull T metadata)
            throws MetadataException {
        if (metadata instanceof FDPMetadata) {
            checkFDPProperties((FDPMetadata) metadata);
        } else if (metadata instanceof CatalogMetadata) {
            checkCatalogProperties((CatalogMetadata) metadata);
        } else if (metadata instanceof DatasetMetadata) {
            checkDatasetProperties((DatasetMetadata) metadata);
        } else if (metadata instanceof DistributionMetadata) {
            checkDistributionProperties((DistributionMetadata) metadata);
        } else if (metadata instanceof DataRecordMetadata) {
            checkDatarecordProperties((DataRecordMetadata) metadata);
        }
    }

    /**
     * Check fdp metadata properties
     *
     * @param metadata FDPMetadata object
     * @throws MetadataException This exception is thrown if the mandatory domain property is
     * missing
     */
    private static void checkFDPProperties(@Nonnull FDPMetadata metadata) throws MetadataException {
        try {
            Preconditions.checkNotNull(metadata.getRepostoryIdentifier(),
                    "Repostory ID must not be null.");
        } catch (NullPointerException ex) {
            String errMsg = ex.getMessage();
            throw new MetadataException(errMsg);
        }
    }

    /**
     * Check catalog metadata properties
     *
     * @param metadata CatlogMetadata object
     * @throws MetadataException This exception is thrown if the mandatory domain property is
     * missing
     */
    private static void checkCatalogProperties(@Nonnull CatalogMetadata metadata)
            throws MetadataException {
        try {
            Preconditions.checkNotNull(metadata.getThemeTaxonomys(),
                    "Metadata dcat:themeTaxonomy must not be null.");
            Preconditions.checkArgument(!metadata.getThemeTaxonomys().isEmpty(),
                    "Metadata dcat:themeTaxonomy must not be empty.");
        } catch (NullPointerException | IllegalArgumentException ex) {
            String errMsg = ex.getMessage();
            throw new MetadataException(errMsg);
        }
    }

    /**
     * Check dataset metadata properties
     *
     * @param metadata DatasetMetadata object
     * @throws MetadataException This exception is thrown if the mandatory domain property is
     * missing
     */
    private static void checkDatasetProperties(@Nonnull DatasetMetadata metadata)
            throws MetadataException {
        try {
            Preconditions.checkNotNull(metadata.getThemes(),
                    "Metadata dcat:theme must not be null.");
            Preconditions.checkArgument(!metadata.getThemes().isEmpty(),
                    "Metadata dcat:theme must not be empty.");
        } catch (NullPointerException | IllegalArgumentException ex) {
            String errMsg = ex.getMessage();
            throw new MetadataException(errMsg);
        }
    }

    /**
     * Check distribution metadata properties
     *
     * @param metadata DistributionMetadata object
     * @throws MetadataException This exception is thrown if the mandatory domain property is
     * missing
     */
    private static void checkDistributionProperties(@Nonnull DistributionMetadata metadata)
            throws MetadataException {
        if (metadata.getAccessURL() == null && metadata.getDownloadURL() == null) {
            String errMsg = "No dcat:accessURL or dcat:downloadURL URL is provided";
            LOGGER.error(errMsg);
            throw new MetadataException(errMsg);
        }
    }

    /**
     * Check datarecord metadata properties
     *
     * @param metadata DataRecordMetadata object
     * @throws MetadataException This exception is thrown if the mandatory domain property is
     * missing
     */
    private static void checkDatarecordProperties(@Nonnull DataRecordMetadata metadata)
            throws MetadataException {
        try {
            Preconditions.checkNotNull(metadata.getRmlURI(),
                    "Metadata rml mapping uri must not be null.");
        } catch (NullPointerException ex) {
            String errMsg = ex.getMessage();
            throw new MetadataException(errMsg);
        }
    }

    private static void propagateToHandler(List<Statement> statements, RDFHandler handler)
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
        handler.handleNamespace("lang", "http://id.loc.gov/vocabulary/iso639-1/");
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
    private static void addIdStatements(Model model, IRI subj, IRI pred, Identifier objc) {
        if (objc != null) {
            addStatement(model, subj, pred, objc.getUri());
            addStatement(model, objc.getUri(), RDF.TYPE, objc.getType());
            addStatement(model, objc.getUri(), DCTERMS.IDENTIFIER, objc.getIdentifier());
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
    private static void addAgentStatements(Model model, IRI subj, IRI pred, Agent objc,
            int metadataModel) {
        if (objc != null) {
            addStatement(model, subj, pred, objc.getUri());
            if (metadataModel == MetadataUtils.SCHEMA_DOT_ORG_MODEL) {
                addStatement(model, objc.getUri(), SCHEMAORG.NAME, objc.getName());
                IRI type = SCHEMAORG.THING;
                if (objc.getType() == FOAF.PERSON) {
                    type = nl.dtl.fairmetadata4j.utils.SCHEMAORG.PERSON;
                } else if (objc.getType() == FOAF.ORGANIZATION) {
                    type = nl.dtl.fairmetadata4j.utils.SCHEMAORG.ORGANIZATION;
                }
                addStatement(model, objc.getUri(), RDF.TYPE, type);
            } else {
                addStatement(model, objc.getUri(), RDF.TYPE, objc.getType());
                if (objc.getName() == null) {
                    String errMsg = "No publisher name provided";
                    LOGGER.info(errMsg);
                } else {
                    addStatement(model, objc.getUri(), FOAF.NAME, objc.getName());
                }
            }
        }
    }

    // We are using this method to reduce the NPath complexity 
    /**
     * Add accessRights rdf statements
     *
     * @param model
     * @param subj
     * @param pred
     * @param objc
     */
    private static void addAccessRightsStatements(Model model, IRI subj, IRI pred,
            AccessRights objc, int metadataModel) {
        if (objc != null) {
            addStatement(model, subj, pred, objc.getUri());
            addStatement(model, objc.getUri(), RDF.TYPE, DCTERMS.RIGHTS_STATEMENT);
            addAuthorizationStatements(model, objc.getUri(), DCTERMS.IS_PART_OF,
                    objc.getAuthorization(), metadataModel);
        }
    }

    // We are using this method to reduce the NPath complexity 
    /**
     * Add authorization rdf statements
     *
     * @param model
     * @param subj
     * @param pred
     * @param objc
     */
    private static void addAuthorizationStatements(Model model, IRI subj, IRI pred,
            Authorization objc, int metadataModel) {
        if (objc != null) {
            addStatement(model, subj, pred, objc.getUri());
            addStatement(model, objc.getUri(), RDF.TYPE, WebAccessControl.AUTHORIZATION);
            objc.getAccessMode().stream().forEach((mode) -> {
                addStatement(model, objc.getUri(), WebAccessControl.ACCESS_MODE, mode);
            });
            objc.getAuthorizedAgent().stream().forEach((agent) -> {
                addAgentStatements(model, objc.getUri(), WebAccessControl.ACCESS_AGENT, agent,
                        metadataModel);
            });
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

    // We are using this method to reduce the NPath complexity 
    /**
     * Add list of rdf statements
     *
     * @param model
     * @param subj
     * @param pred
     * @param objcs
     */
    private static void addStatements(Model model, IRI subj, IRI pred, List objcs) {
        if (objcs != null) {
            objcs.forEach((objc) -> {
                addStatement(model, subj, pred, (Value) objc);
            });
        }
    }

}
