/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.Iterator;
import java.util.List;
import nl.dtl.fairmetadata.model.CatalogMetadata;
import nl.dtl.fairmetadata.model.DatasetMetadata;
import nl.dtl.fairmetadata.utils.vocabulary.DCAT;
import org.apache.logging.log4j.LogManager;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;

/**
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
    
    @Override
    public DatasetMetadata parse(List<Statement> statements, 
            URI datasetURI) throws MetadataExeception {
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
        if (metadata.getThemes().isEmpty()) {
            String errMsg = "No dcat:theme provided";
            LOGGER.error(errMsg);
            throw (new MetadataExeception(errMsg));
        }
        return metadata;
    }
    
}
