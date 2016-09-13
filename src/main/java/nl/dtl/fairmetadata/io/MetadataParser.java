/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.XMLSchema;

import nl.dtl.fairmetadata.model.Metadata;

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
    
    protected abstract T createMetadata();
    
    protected T parse(List<Statement> statements, URI metadataUri)  {
        T metadata = createMetadata();
        
        metadata.setUri(metadataUri);
        
        LOGGER.info("Parse common metadata properties");
        
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            URI predicate = st.getPredicate();
            
            if (subject.equals(metadataUri)) {
                if (predicate.equals(DCTERMS.HAS_VERSION)) {
                    Literal version = new LiteralImpl(st.getObject().stringValue(), XMLSchema.FLOAT);
                    metadata.setVersion(version);
                } else if (predicate.equals(RDFS.LABEL) || predicate.equals(DCTERMS.TITLE)) {
                    Literal title = new LiteralImpl(st.getObject().stringValue(), XMLSchema.STRING);
                    metadata.setTitle(title);
                } else if (predicate.equals(DCTERMS.DESCRIPTION)) {
                    Literal description = new LiteralImpl(st.getObject().stringValue(), XMLSchema.STRING);
                    metadata.setDescription(description);
                } else if (predicate.equals(DCTERMS.LICENSE)) {
                    URI license = (URI) st.getObject();
                    metadata.setLicense(license);
                } else if (predicate.equals(DCTERMS.RIGHTS)) {
                    URI rights = (URI) st.getObject();
                    metadata.setRights(rights);
                } else if (predicate.equals(DCTERMS.PUBLISHER)) {
                    URI publisher = (URI) st.getObject();
                    metadata.getPublisher().add(publisher);
                } else if (predicate.equals(DCTERMS.LANGUAGE)) {
                    URI language = (URI) st.getObject();
                    metadata.setLanguage(language);
                } else if (predicate.equals(DCTERMS.IDENTIFIER) && metadata.getIdentifier() == null) {
                    metadata.setIdentifier((Literal) st.getObject());
                } else if (predicate.equals(DCTERMS.ISSUED) && metadata.getIssued() == null) {
                    metadata.setIssued((Literal) st.getObject());
                } else if (predicate.equals(DCTERMS.MODIFIED) && metadata.getModified() == null) {
                    metadata.setModified((Literal) st.getObject());
                }
            }
        }
        return metadata;
    }
}
