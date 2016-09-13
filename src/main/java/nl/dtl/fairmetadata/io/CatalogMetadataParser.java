/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.openrdf.model.Model;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.Rio;
import org.openrdf.rio.UnsupportedRDFormatException;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import nl.dtl.fairmetadata.model.CatalogMetadata;
import nl.dtl.fairmetadata.utils.vocabulary.DCAT;

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
     */
    @Override
    public CatalogMetadata parse(@Nonnull List<Statement> statements, 
            @Nonnull URI catalogURI) {
        Preconditions.checkNotNull(catalogURI, 
                "Catalog URI must not be null.");
        Preconditions.checkNotNull(statements, 
                "Catalog statements must not be null.");
        
        LOGGER.info("Parsing catalog metadata");
        
        CatalogMetadata metadata = super.parse(statements, catalogURI);
        
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            URI predicate = st.getPredicate();
            
            if (subject.equals(catalogURI)) {
                if (predicate.equals(FOAF.HOMEPAGE)) {
                    URI homePage = (URI) st.getObject();
                    metadata.setHomepage(homePage);
                } else if (predicate.equals(DCAT.THEME_TAXONOMY)) {
                    URI themeTax = (URI) st.getObject();
                    metadata.getThemeTaxonomy().add(themeTax);
                }
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
     * @throws MetadataParserException
     */
    public CatalogMetadata parse(@Nonnull String catalogMetadata, 
            @Nonnull String catalogID,
            @Nonnull URI catalogURI, URI fdpURI, @Nonnull RDFFormat format) 
            throws MetadataParserException {
        Preconditions.checkNotNull(catalogMetadata, 
                "Catalog metadata string must not be null."); 
        Preconditions.checkNotNull(catalogID, "Catalog ID must not be null.");
        Preconditions.checkNotNull(catalogURI, "Catalog URI must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");
        
        Preconditions.checkArgument(!catalogMetadata.isEmpty(), 
                "The catalog metadata content can't be EMPTY");
        Preconditions.checkArgument(!catalogID.isEmpty(), 
                "The catalog id content can't be EMPTY");        
        
        try {
            Model modelCatalog = Rio.parse(new StringReader(catalogMetadata), 
                    catalogURI.stringValue(), format);
            Iterator<Statement> it = modelCatalog.iterator();
            List<Statement> statements = ImmutableList.copyOf(it);
            
            CatalogMetadata metadata = this.parse(statements, catalogURI);
            metadata.setIdentifier(new LiteralImpl(catalogID, 
                    XMLSchema.STRING));
            metadata.setParentURI(fdpURI);
            
            return metadata;
        } catch (IOException ex) {
            String errMsg = "Error reading catalog metadata content" + 
                    ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        } catch (RDFParseException ex) {
            String errMsg = "Error parsing catalog metadata content. " + 
                    ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        } catch (UnsupportedRDFormatException ex) {
            String errMsg = "Unsuppoerted RDF format. " + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        } 
    }
    
}
