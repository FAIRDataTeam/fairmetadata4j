/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.List;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.RDFS;

import nl.dtl.fairmetadata.model.FDPMetadata;

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
        
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            URI predicate = st.getPredicate();
            
            if (subject.equals(fdpURI)) {
                if (predicate.equals(FOAF.HOMEPAGE)) {
                    URI homePage = (URI) st.getObject();
                    metadata.setHomepage(homePage);
                } else if (predicate.equals(RDFS.SEEALSO)) {
                    metadata.setSwaggerDoc((URI) st.getObject());
                }
            }
        }
        return metadata;
    }
}
