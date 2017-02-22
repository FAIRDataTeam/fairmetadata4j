/**
 * The MIT License
 * Copyright © 2017 DTL
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
package nl.dtl.fairmetadata4j.io;

import com.google.common.base.Preconditions;
import java.util.List;
import javax.annotation.Nonnull;
import nl.dtl.fairmetadata4j.model.Agent;
import org.apache.logging.log4j.LogManager;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

/**
 * Parser for agent object
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-11-30
 * @version 0.1
 */
public class AgentParser {
    
    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(AgentParser.class);
    /**
     * Create agent object
     * 
     * @param statements    List of rdf statements
     * @param agentURI      Agent uri
     * @return 
     */
    public static Agent parse(@Nonnull List<Statement> statements,
            @Nonnull IRI agentURI) {
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
            IRI predicate = st.getPredicate();
            Value object = st.getObject();

            if (subject.equals(agentURI)) {
                if (predicate.equals(RDF.TYPE)) {
                    agent.setType((IRI) object);
                } else if (predicate.equals(FOAF.NAME) || 
                        predicate.equals(RDFS.LABEL)) {
                    ValueFactory f = SimpleValueFactory.getInstance();
                    agent.setName(f.createLiteral(object.stringValue(),
                            XMLSchema.STRING));
                }
            }
        }
        return agent;
    }
    
}
