/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import com.google.common.base.Preconditions;
import java.util.List;
import javax.annotation.Nonnull;
import nl.dtl.fairmetadata.model.Agent;
import org.apache.logging.log4j.LogManager;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.XMLSchema;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-11-30
 * @version 0.1
 */
public class AgentParser {
    
    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(IdentifierParser.class);

    public static Agent parse(@Nonnull List<Statement> statements,
            @Nonnull URI agentURI) {
        Preconditions.checkNotNull(agentURI,
                "Agent URI must not be null.");
        Preconditions.checkNotNull(statements,
                "Agent statements must not be null.");
        Preconditions.checkArgument(!statements.isEmpty(),
                "Agent statements must not be empty.");
        LOGGER.info("Parsing agent");        

        Agent agent = new Agent();
        agent.setUri(agentURI);
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            URI predicate = st.getPredicate();
            Value object = st.getObject();

            if (subject.equals(agentURI)) {
                if (predicate.equals(RDF.TYPE)) {
                    agent.setType((URI) object);
                } else if (predicate.equals(FOAF.NAME)) {
                    agent.setName(new LiteralImpl(object.stringValue(),
                            XMLSchema.STRING));
                }
            }
        }
        return agent;
    }
    
}
