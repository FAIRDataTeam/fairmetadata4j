/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.List;

import org.apache.logging.log4j.LogManager;

import nl.dtl.fairmetadata.model.Metadata;
import nl.dtl.fairmetadata.utils.vocabulary.FDP;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

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
    
    protected T parse(List<Statement> statements, IRI metadataUri)  {
        T metadata = createMetadata();
        
        metadata.setUri(metadataUri);
        
        LOGGER.info("Parse common metadata properties");
        ValueFactory f = SimpleValueFactory.getInstance();
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            Value object = st.getObject();
            
            if (subject.equals(metadataUri)) {
                if (predicate.equals(DCTERMS.HAS_VERSION)) {
                    metadata.setVersion(f.createLiteral(object.stringValue(), 
                            XMLSchema.STRING));
                } else if (predicate.equals(RDFS.LABEL) || 
                        predicate.equals(DCTERMS.TITLE)) {
                    metadata.setTitle(f.createLiteral(object.stringValue(), 
                            XMLSchema.STRING));
                } else if (predicate.equals(DCTERMS.DESCRIPTION)) {
                    metadata.setDescription(f.createLiteral(object.
                            stringValue(), XMLSchema.STRING));
                } else if (predicate.equals(DCTERMS.LICENSE)) {
                    metadata.setLicense((IRI) object);
                } else if (predicate.equals(DCTERMS.RIGHTS)) {
                    metadata.setRights((IRI) object);
                } else if (predicate.equals(DCTERMS.LANGUAGE)) {
                    metadata.setLanguage((IRI) object);
                } else if (predicate.equals(DCTERMS.PUBLISHER)) {
                    metadata.setPublisher(AgentParser.parse(statements, 
                            (IRI)object));
                } else if (predicate.equals(FDP.METADATA_IDENTIFIER)) {
                    metadata.setIdentifier(IdentifierParser.parse(statements, 
                            (IRI)object));
                } else if (predicate.equals(FDP.METADATA_ISSUED) && 
                        metadata.getIssued() == null) {
                    metadata.setIssued(f.createLiteral(object.
                            stringValue(), XMLSchema.DATETIME));
                } else if (predicate.equals(FDP.METADATA_MODIFIED) && 
                        metadata.getModified() == null) {
                    metadata.setModified(f.createLiteral(object.
                            stringValue(), XMLSchema.DATETIME));
                }
            }
        }
        return metadata;
    }
}
