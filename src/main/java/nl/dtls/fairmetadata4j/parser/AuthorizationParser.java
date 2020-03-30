/**
 * The MIT License
 * Copyright © 2019 DTL
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtls.fairmetadata4j.parser;

import com.google.common.base.Preconditions;
import nl.dtls.fairmetadata4j.model.Agent;
import nl.dtls.fairmetadata4j.model.Authorization;
import nl.dtls.fairmetadata4j.vocabulary.WebAccessControl;
import org.apache.logging.log4j.LogManager;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.vocabulary.RDFS;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationParser {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(AuthorizationParser.class);

    public static Authorization parse(@Nonnull List<Statement> statements, @Nonnull IRI authorizationURI) {
        Preconditions.checkNotNull(authorizationURI, "Authorization URI must not be null.");
        Preconditions.checkNotNull(statements, "Authorization statements must not be null.");
        Preconditions.checkArgument(!statements.isEmpty(), "Authorization statements must not be empty.");
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
                    authorizedAgent.add(AgentParser.parse(statements, (IRI) object));
                } else if (predicate.equals(WebAccessControl.ACCESS_MODE)) {
                    accessMode.add((IRI) object);
                } else if (predicate.equals(RDFS.SEEALSO)) {
                    authorization.setRequestURI((IRI) object);
                }
            }
        }
        authorization.setAccessMode(accessMode);
        authorization.setAuthorizedAgent(authorizedAgent);
        return authorization;
    }
}
