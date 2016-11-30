/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.ArrayList;
import java.util.List;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.RDFS;

import nl.dtl.fairmetadata.model.FDPMetadata;
import nl.dtl.fairmetadata.utils.vocabulary.FDP;
import nl.dtl.fairmetadata.utils.vocabulary.R3D;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-07
 * @version 0.1
 */
public class FDPMetadataParser extends MetadataParser<FDPMetadata> {
    
    @Override
    protected FDPMetadata createMetadata() {
        return new FDPMetadata();
    }
    
    @Override
    public FDPMetadata parse(List<Statement> statements, URI fdpURI) {
        FDPMetadata metadata = super.parse(statements, fdpURI);
        List<URI> catalogs = new ArrayList();
        
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            URI predicate = st.getPredicate();
            Value object = st.getObject();
            
            if (subject.equals(fdpURI)) {
                if (predicate.equals(FOAF.HOMEPAGE)) {
                    metadata.setHomepage((URI) object);
                } else if (predicate.equals(RDFS.SEEALSO)) {
                    metadata.setSwaggerDoc((URI) object);
                } else if (predicate.equals(R3D.DATA_CATALOG)) {
                    catalogs.add((URI) object);
                } else if (predicate.equals(R3D.REPO_IDENTIFIER)) {
                    metadata.setRepostoryIdentifier(IdentifierParser.parse(
                            statements, (URI)object));
                } else if (predicate.equals(R3D.INSTITUTION_COUNTRY)) {
                    metadata.setInstitutionCountry((URI) object);
                } else if (predicate.equals(R3D.REPO_START_DATE)) {
                    metadata.setStartDate((new LiteralImpl(object.
                            stringValue(), XMLSchema.DATETIME)));
                } else if (predicate.equals(R3D.REPO_LAST_UPDATE)) {
                    metadata.setLastUpdate((new LiteralImpl(object.
                            stringValue(), XMLSchema.DATETIME)));
                } else if (predicate.equals(R3D.INSTITUTION)) {
                    metadata.setInstitution(AgentParser.parse(
                            statements, (URI)object));
                }
            }
        }
        if(!catalogs.isEmpty()) {
            metadata.setCatalogs(catalogs);
        }
        return metadata;
    }
}
