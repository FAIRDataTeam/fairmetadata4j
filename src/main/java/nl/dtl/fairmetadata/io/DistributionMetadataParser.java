/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.Iterator;
import java.util.List;
import nl.dtl.fairmetadata.model.DistributionMetadata;
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
public class DistributionMetadataParser extends MetadataParser
        <DistributionMetadata> {
    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(DatasetMetadataParser.class);
    
    @Override
    public DistributionMetadata parse(List<Statement> statements, 
            URI distributionURI) throws MetadataExeception {
        LOGGER.info("Parsing distribution metadata");
        DistributionMetadata metadata = super.parse(statements, 
                distributionURI);
        Iterator<Statement> it = statements.iterator();
        while (it.hasNext()) {
            Statement st = it.next();
            if (st.getSubject().equals(distributionURI)
                    && st.getPredicate().equals(DCAT.ACCESS_URL)) {
                URI accessURL = (URI) st.getObject();
                metadata.setAccessURL(accessURL);
            } else if (st.getSubject().equals(distributionURI)
                    && st.getPredicate().equals(DCAT.DOWNLOAD_URL)) {
                URI downloadURL = (URI) st.getObject();
                metadata.setDownloadURL(downloadURL);
            } else if (st.getSubject().equals(distributionURI)
                    && st.getPredicate().equals(DCAT.FORMAT)) {
                 Literal format = new LiteralImpl(st.getObject().
                        stringValue(), XMLSchema.STRING);
                metadata.setFormat(format);
            } else if (st.getSubject().equals(distributionURI)
                    && st.getPredicate().equals(DCAT.BYTE_SIZE)) {
                 Literal byteSize = new LiteralImpl(st.getObject().
                        stringValue(), XMLSchema.STRING);
                metadata.setByteSize(byteSize);
            } else if (st.getSubject().equals(distributionURI)
                    && st.getPredicate().equals(DCAT.MEDIA_TYPE)) {
                 Literal mediaType = new LiteralImpl(st.getObject().
                        stringValue(), XMLSchema.STRING);
                metadata.setMediaType(mediaType);
            }
        }
        if (metadata.getAccessURL() == null && 
                metadata.getDownloadURL() == null ) {
            String errMsg = 
                    "No dcat:accessURL or dcat:downloadURL URL is provided";
            LOGGER.error(errMsg);
            throw (new MetadataExeception(errMsg));
        }
        return metadata;
    }
    
    public DistributionMetadata parse(List<Statement> statements, 
            URI distributionURI, URI datasetURI) throws MetadataExeception {
        DistributionMetadata metadata = this.parse(statements, distributionURI);
        metadata.setDatasetURI(datasetURI);
        return metadata;
    }
    
}
