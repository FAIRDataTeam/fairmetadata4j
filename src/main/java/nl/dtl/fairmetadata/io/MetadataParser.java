/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.Iterator;
import java.util.List;
import nl.dtl.fairmetadata.model.Metadata;
import org.apache.logging.log4j.LogManager;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.XMLSchema;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @param <T>
 * @since 2016-09-07
 * @version 0.1
 */
public abstract class MetadataParser<T extends Metadata>  {
    
    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(MetadataParser.class);
    
    protected T parse(List<Statement> statements, 
            URI metadataUri) throws MetadataExeception {
        Metadata metadata = new Metadata();
        Iterator<Statement> it = statements.iterator();
        metadata.setUri(metadataUri);
        while (it.hasNext()) {
            Statement st = it.next();
            if (st.getSubject().equals(metadataUri)
                    && st.getPredicate().equals(DCTERMS.HAS_VERSION)) {
                Literal version = new LiteralImpl(st.getObject().stringValue(),
                        XMLSchema.FLOAT);
                metadata.setVersion(version);
            } else if (st.getSubject().equals(metadataUri)
                    && (st.getPredicate().equals(RDFS.LABEL)
                    || st.getPredicate().equals(DCTERMS.TITLE))) {
                Literal title = new LiteralImpl(st.getObject().stringValue(),
                        XMLSchema.STRING);
                metadata.setTitle(title);
            } else if (st.getSubject().equals(metadataUri)
                    && st.getPredicate().equals(DCTERMS.DESCRIPTION)) {
                Literal description = new LiteralImpl(st.getObject().
                        stringValue(), XMLSchema.STRING);
                metadata.setDescription(description);
            } else if (st.getSubject().equals(metadataUri)
                    && st.getPredicate().equals(DCTERMS.LICENSE)) {
                URI license = (URI) st.getObject();
                metadata.setLicense(license);
            } else if (st.getSubject().equals(metadataUri)
                    && st.getPredicate().equals(DCTERMS.RIGHTS)) {
                URI rights = (URI) st.getObject();
                metadata.setRights(rights);
            } else if (st.getSubject().equals(metadataUri)
                    && st.getPredicate().equals(DCTERMS.PUBLISHER)) {
                URI publisher = (URI) st.getObject();
                metadata.getPublisher().add(publisher);
            } else if (st.getSubject().equals(metadataUri)
                    && st.getPredicate().equals(DCTERMS.LANGUAGE)) {
                URI language = (URI) st.getObject();
                metadata.setLanguage(language);
            } else if (st.getSubject().equals(metadataUri)
                    && st.getPredicate().equals(DCTERMS.IDENTIFIER)
                    && metadata.getIdentifier() == null) {
                metadata.setIdentifier((Literal) st.getObject());
            } else if (st.getSubject().equals(metadataUri)
                    && st.getPredicate().equals(DCTERMS.ISSUED)
                    && metadata.getIssued() == null) {
                metadata.setIssued((Literal) st.getObject());
            } else if (st.getSubject().equals(metadataUri)
                    && st.getPredicate().equals(DCTERMS.MODIFIED)
                    && metadata.getModified() == null) {
                metadata.setModified((Literal) st.getObject());
            }
        }
        checkMetadata(metadata);
        return (T) metadata;
    }

    protected void checkMetadata(Metadata metadata) throws MetadataExeception {
        if (metadata.getVersion() == null) {
            String errMsg = "No version number provided";
            LOGGER.error(errMsg);
            throw (new MetadataExeception(errMsg));
        } else if (metadata.getTitle() == null) {
            String errMsg = "No title or label provided";
            LOGGER.error(errMsg);
            throw (new MetadataExeception(errMsg));
        }
    }
    
}
