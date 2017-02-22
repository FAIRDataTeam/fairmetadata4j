/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata4j.io;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import nl.dtl.fairmetadata4j.model.Agent;
import nl.dtl.fairmetadata4j.model.Authorization;
import nl.dtl.fairmetadata4j.utils.vocabulary.WebAccessControl;
import org.apache.logging.log4j.LogManager;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;

/**
 * Parser for authorization object
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2017-02-22
 * @version 0.1
 */
public class AuthorizationParser {
    
    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(AuthorizationParser.class);
    
    public static Authorization parse(@Nonnull List<Statement> statements,
            @Nonnull IRI authorizationURI) {
        Preconditions.checkNotNull(authorizationURI,
                "Authorization URI must not be null.");
        Preconditions.checkNotNull(statements,
                "Authorization statements must not be null.");
        Preconditions.checkArgument(!statements.isEmpty(),
                "Authorization statements must not be empty.");
        LOGGER.info("Parsing Authorization");
        Authorization authorization = new Authorization();
        authorization.setUri(authorizationURI);
        List<Agent> authorizedAgent = new ArrayList();
        List<IRI> accessMode = new ArrayList();
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            Value object = st.getObject();
            if (subject.equals(authorizationURI)) {
                if (predicate.equals(WebAccessControl.ACCESS_AGENT)) {
                    authorizedAgent.add(AgentParser.parse(
                            statements, (IRI) object));
                } else if (predicate.equals(WebAccessControl.ACCESS_MODE)) {
                    accessMode.add((IRI) object);
                }
            }
        }
        authorization.setAccessMode(accessMode);
        authorization.setAuthorizedAgent(authorizedAgent);
        return authorization;
    }
    
}
