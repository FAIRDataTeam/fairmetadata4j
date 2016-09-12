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
import nl.dtl.fairmetadata.model.CatalogMetadata;
import nl.dtl.fairmetadata.utils.vocabulary.DCAT;
import org.apache.logging.log4j.LogManager;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.Rio;
import org.openrdf.rio.UnsupportedRDFormatException;

/**
 * Parser for catalog metadata
 * 
 * @author Rajaram Kaliyaperumal
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
     * Parse RDF statements to catalog metadata object
     * 
     * @param statements    List of RDF statement list 
     * @param catalogURI    Catalog URI
     * @return              CatalogMetadata object
     * @throws MetadataException 
     */
    @Override
    public CatalogMetadata parse(@Nonnull List<Statement> statements, 
            @Nonnull URI catalogURI) throws MetadataException {       
        Preconditions.checkNotNull(catalogURI, "Catalog URI must not be null.");
        Preconditions.checkNotNull(statements, 
                "Catalog statements must not be null.");
        LOGGER.info("Parsing catalog metadata");
        CatalogMetadata metadata = (CatalogMetadata) super.parse(statements, 
                catalogURI);
        Iterator<Statement> it = statements.iterator();
        while (it.hasNext()) {
            Statement st = it.next();
            if (st.getSubject().equals(catalogURI)
                    && st.getPredicate().equals(FOAF.HOMEPAGE)) {
                URI homePage = (URI) st.getObject();
                metadata.setHomepage(homePage);
            } else if ( st.getPredicate().equals(DCAT.THEME_TAXONOMY)) {
                URI themeTax = (URI) st.getObject();
                metadata.getThemeTaxonomy().add(themeTax);
            }
        }
        return metadata;
    }
    
    /**
     * Parse RDF string to catalog metadata object
     * 
     * @param catalogMetadata   Catalog metadata as a RDF string
     * @param catalogID         Catalog ID  
     * @param catalogURI        Catalog URI 
     * @param fdpURI            FairDataPoint URI
     * @param format            RDF string's RDF format
     * @return                  CatalogMetadata object
     * @throws MetadataException
     * @throws DatatypeConfigurationException 
     */
    public CatalogMetadata parse (@Nonnull String catalogMetadata, 
            @Nonnull String catalogID, @Nonnull URI catalogURI, URI fdpURI, 
            @Nonnull RDFFormat format) 
            throws MetadataException, 
            DatatypeConfigurationException {
        Preconditions.checkNotNull(catalogMetadata, 
                "Catalog metadata string must not be null."); 
        Preconditions.checkNotNull(catalogID, "Catalog ID must not be null.");
        Preconditions.checkNotNull(catalogURI, "Catalog URI must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");
        if (catalogMetadata.isEmpty()) {
            String errorMsg = "The catalog metadata content "
                    + "can't be EMPTY";
            LOGGER.error(errorMsg);
            throw (new IllegalArgumentException(errorMsg));
        }        
        if (catalogID.isEmpty()) {
            String errorMsg = "The catalog id content "
                    + "can't be EMPTY";
            LOGGER.error(errorMsg);
            throw (new IllegalArgumentException(errorMsg));
        }        
        StringReader reader = new StringReader(catalogMetadata);
        org.openrdf.model.Model modelCatalog;
        CatalogMetadata metadata;
        try {
            modelCatalog = Rio.parse(reader, catalogURI.stringValue(), format);
            Iterator<Statement> it = modelCatalog.iterator();
            List<Statement> statements = ImmutableList.copyOf(it);
            metadata = this.parse(statements, catalogURI);
            metadata.setIdentifier(new LiteralImpl(catalogID, 
                    XMLSchema.STRING));
            metadata.setParentURI(fdpURI);
            
        } catch (IOException ex) {
            String errMsg = "Error reading catalog metadata content"
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataException(errMsg));
        } catch (RDFParseException ex) {
            String errMsg = "Error parsing catalog metadata content. "
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataException(errMsg));
        } catch (UnsupportedRDFormatException ex) {
            String errMsg = "Unsuppoerted RDF format. " + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataException(errMsg));
        } 
        return metadata;
    }
    
}
