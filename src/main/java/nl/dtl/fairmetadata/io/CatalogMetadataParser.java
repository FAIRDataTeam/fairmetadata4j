/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.Iterator;
import java.util.List;
import nl.dtl.fairmetadata.model.CatalogMetadata;
import nl.dtl.fairmetadata.utils.vocabulary.DCAT;
import org.apache.logging.log4j.LogManager;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.FOAF;



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
    public CatalogMetadata parse(List<Statement> statements, 
            URI catalogURI) throws MetadataExeception {
        LOGGER.info("Parsing catalog metadata");
        CatalogMetadata metadata = super.parse(statements, catalogURI);
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
    
    public CatalogMetadata parse(List<Statement> statements, 
            URI catalogURI, URI fdpURI) throws MetadataExeception {
        CatalogMetadata metadata = this.parse(statements, catalogURI);
        metadata.setFdpUri(fdpURI);
        return metadata;
    }
    
}
