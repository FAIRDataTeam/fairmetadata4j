/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
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
    @Override
    public CatalogMetadata parse(List<Statement> statements, 
            URI catalogURI) throws MetadataExeception {
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
        if (metadata.getThemeTaxonomy().isEmpty()) {
            String errMsg = "No dcat:themeTaxonomy provided";
            LOGGER.error(errMsg);
            throw (new MetadataExeception(errMsg));
        }
        return metadata;
    }
    
    public CatalogMetadata parse (String catalogMetadata, String catalogID,
            URI catalogURI, URI fdpURI, RDFFormat format) 
            throws MetadataExeception, 
            DatatypeConfigurationException {
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
            throw (new MetadataExeception(errMsg));
        } catch (RDFParseException ex) {
            String errMsg = "Error parsing catalog metadata content. "
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
