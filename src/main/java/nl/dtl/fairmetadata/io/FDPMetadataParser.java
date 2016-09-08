/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.Iterator;
import java.util.List;
import nl.dtl.fairmetadata.model.FDPMetadata;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.RDFS;

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
    public FDPMetadata parse(List<Statement> statements, 
            URI fdpURI) throws MetadataExeception {
        FDPMetadata metadata = super.parse(statements, fdpURI);
        Iterator<Statement> it = statements.iterator();
        while (it.hasNext()) {
            Statement st = it.next();
            if (st.getSubject().equals(fdpURI)
                    && st.getPredicate().equals(FOAF.HOMEPAGE)) {
                URI homePage = (URI) st.getObject();
                metadata.setHomepage(homePage);
            } else if (st.getSubject().equals(fdpURI)
                    && st.getPredicate().equals(RDFS.SEEALSO)) {
                metadata.setSwaggerDoc((URI) st.getObject());
            }
        }
        return metadata;
    }
    
}
