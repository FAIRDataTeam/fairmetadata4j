/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.XMLSchema;

import nl.dtl.fairmetadata.model.Metadata;
import nl.dtl.fairmetadata.utils.vocabulary.FDP;
import org.openrdf.model.Value;
import org.openrdf.model.impl.URIImpl;

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
            Value object = st.getObject();
            
            if (subject.equals(metadataUri)) {
                if (predicate.equals(DCTERMS.HAS_VERSION)) {
                    metadata.setVersion(new LiteralImpl(object.stringValue(), 
                            XMLSchema.STRING));
                } else if (predicate.equals(RDFS.LABEL) || 
                        predicate.equals(DCTERMS.TITLE)) {
                    metadata.setTitle(new LiteralImpl(object.stringValue(), 
                            XMLSchema.STRING));
                } else if (predicate.equals(DCTERMS.DESCRIPTION)) {
                    metadata.setDescription(new LiteralImpl(object.
                            stringValue(), XMLSchema.STRING));
                } else if (predicate.equals(DCTERMS.LICENSE)) {
                    metadata.setLicense((URI) object);
                } else if (predicate.equals(DCTERMS.RIGHTS)) {
                    metadata.setRights((URI) object);
                } else if (predicate.equals(DCTERMS.PUBLISHER)) {
                    metadata.getPublisher().add((URI) object);
                } else if (predicate.equals(DCTERMS.LANGUAGE)) {
                    metadata.setLanguage((URI) object);
                } else if (predicate.equals(FDP.METADATA_IDENTIFIER) && 
                        metadata.getIdentifier() == null) {
                    metadata.setIdentifier(IdentifierParser.parse(statements, 
                            (URI)object));
                } else if (predicate.equals(FDP.METADATA_ISSUED) && 
                        metadata.getIssued() == null) {
                    metadata.setIssued(new LiteralImpl(object.
                            stringValue(), XMLSchema.DATETIME));
                } else if (predicate.equals(FDP.METADATA_MODIFIED) && 
                        metadata.getModified() == null) {
                    metadata.setModified(new LiteralImpl(object.
                            stringValue(), XMLSchema.DATETIME));
                }
            }
        }
        return metadata;
    }
}
