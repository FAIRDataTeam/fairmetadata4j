/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.datatype.DatatypeConfigurationException;
import nl.dtl.fairmetadata.model.DatasetMetadata;
import nl.dtl.fairmetadata.utils.vocabulary.DCAT;
import org.apache.logging.log4j.LogManager;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.Rio;
import org.openrdf.rio.UnsupportedRDFormatException;

/**
 * Parser for dataset metadata
 * 
 * @author Rajaram Kaliyaperumal
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
     * Parse RDF statements to dataset metadata object
     * 
     * @param statements    List of RDF statement list 
     * @param datasetURI    Dataset URI
     * @return              DatasetMetadata object
     * @throws MetadataExeception 
     */
    @Override
    public DatasetMetadata parse(@Nonnull List<Statement> statements, 
            @Nonnull URI datasetURI) throws MetadataExeception {
        Preconditions.checkNotNull(datasetURI, "Dataset URI must not be null.");
        Preconditions.checkNotNull(statements, 
                "Dataset statements must not be null.");
        LOGGER.info("Parsing dataset metadata");
        DatasetMetadata metadata = super.parse(statements, datasetURI);
        Iterator<Statement> it = statements.iterator();
        while (it.hasNext()) {
            Statement st = it.next();
            if (st.getSubject().equals(datasetURI)
                    && st.getPredicate().equals(DCAT.LANDING_PAGE)) {
                URI landingPage = (URI) st.getObject();
                metadata.setLandingPage(landingPage);
            } else if ( st.getPredicate().equals(DCAT.THEME)) {
                URI theme = (URI) st.getObject();
                metadata.getThemes().add(theme);
            } else if ( st.getPredicate().equals(DCAT.CONTACT_POINT)) {
                URI contactPoint = (URI) st.getObject();
                metadata.setContactPoint(contactPoint);
            } else if (st.getSubject().equals(datasetURI)
                    && st.getPredicate().equals(DCAT.KEYWORD)) {
                Literal keyword = new LiteralImpl(st.getObject().
                        stringValue(), XMLSchema.STRING);
                metadata.getKeywords().add(keyword);
            }
        }
        return metadata;
    }
    
    /**
     * Parse RDF string to dataset metadata object
     * @param datasetMetadata   Dataset metadata as a RDF string
     * @param datasetID         Dataset ID  
     * @param datasetURI        Dataset URI
     * @param catalogURI        Catalog URI
     * @param format            RDF string's RDF format
     * @return                  DatasetMetadata object
     * @throws MetadataExeception
     * @throws DatatypeConfigurationException 
     */
    public DatasetMetadata parse (@Nonnull String datasetMetadata, 
            @Nonnull String datasetID, @Nonnull URI datasetURI, URI catalogURI, 
            @Nonnull RDFFormat format) 
            throws MetadataExeception, 
            DatatypeConfigurationException {
        Preconditions.checkNotNull(datasetMetadata, 
                "Dataset metadata string must not be null."); 
        Preconditions.checkNotNull(datasetID, "Dataset ID must not be null.");
        Preconditions.checkNotNull(datasetURI, "Dataset URI must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");
        if (datasetMetadata.isEmpty()) {
            String errorMsg = "The dataset metadata content "
                    + "can't be EMPTY";
            LOGGER.error(errorMsg);
            throw (new IllegalArgumentException(errorMsg));
        }        
        if (datasetID.isEmpty()) {
            String errorMsg = "The dataset id content "
                    + "can't be EMPTY";
            LOGGER.error(errorMsg);
            throw (new IllegalArgumentException(errorMsg));
        }        
        StringReader reader = new StringReader(datasetMetadata);
        org.openrdf.model.Model modelDataset;
        DatasetMetadata metadata;
        try {
            modelDataset = Rio.parse(reader, datasetURI.stringValue(), format);
            Iterator<Statement> it = modelDataset.iterator();
            List<Statement> statements = ImmutableList.copyOf(it);
            metadata = this.parse(statements, datasetURI);
            metadata.setIdentifier(new LiteralImpl(datasetID, 
                    XMLSchema.STRING));
            metadata.setParentURI(catalogURI);
            
        } catch (IOException ex) {
            String errMsg = "Error reading dataset metadata content"
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataExeception(errMsg));
        } catch (RDFParseException ex) {
            String errMsg = "Error parsing dataset metadata content. "
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataExeception(errMsg));
        } catch (UnsupportedRDFormatException ex) {
            String errMsg = "Unsuppoerted RDF format. " + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataExeception(errMsg));
        } 
        return metadata;
    }
    
}
