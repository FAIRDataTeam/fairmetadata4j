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
package nl.dtl.fairmetadata4j.io;

import java.util.ArrayList;
import java.util.List;
import nl.dtl.fairmetadata4j.model.Agent;
import nl.dtl.fairmetadata4j.utils.ExampleFilesUtils;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Unit tests for AgentParser.
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2017-07-17
 * @version 0.1
 */
public class AgentParserTest {

    private final List<Statement> STMTS = ExampleFilesUtils.getFileContentAsStatements(
            ExampleFilesUtils.FDP_METADATA_FILE, ExampleFilesUtils.FDP_URI.toString());

    /**
     * Test null publisher uri, this test is expected to throw exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullPublisherUri() throws Exception {
        System.out.println("Test : Parse null publisher uri");
        IRI agentURI = null;
        AgentParser.parse(STMTS, agentURI);
    }

    /**
     * Test null rdf statements, this test is expected to throw exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullStatements() throws Exception {
        System.out.println("Test : Parse null publisher statements");
        List<Statement> statements = null;
        IRI agentURI = ExampleFilesUtils.FDP_METADATA_PUBLISHER_URI;
        AgentParser.parse(statements, agentURI);
    }

    /**
     * Test empty rdf statements, this test is expected to throw exception
     *
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyStatements() throws Exception {
        System.out.println("Test : Parse empty publisher statements");
        List<Statement> statements = new ArrayList();
        IRI agentURI = ExampleFilesUtils.FDP_METADATA_PUBLISHER_URI;
        AgentParser.parse(statements, agentURI);
    }

    /**
     * Test valid agent uri.
     */
    @Test
    public void testValidMetadataID() {
        System.out.println("Parse valid publisher ID");
        IRI agentURI = ExampleFilesUtils.FDP_METADATA_PUBLISHER_URI;
        Agent result = AgentParser.parse(STMTS, agentURI);
        assertNotNull(result);
    }

    /**
     * Test valid agent uri.
     */
    @Test
    public void testValidPublisherUri() {
        System.out.println("Parse fdp publisher uri");
        IRI identifierURI = ExampleFilesUtils.FDP_METADATA_PUBLISHER_URI;
        Agent result = AgentParser.parse(STMTS, identifierURI);
        assertNotNull(result);
    }

    /**
     * Test valid wrong publisher type.
     */
    @Test
    public void testInValidPublishertype() {
        System.out.println("Parse invalid publisher type");
        IRI identifierURI = ExampleFilesUtils.FDP_METADATA_PUBLISHER_URI;
        List<Statement> stmts4Parser = new ArrayList();
        for (Statement st : STMTS) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            if (subject.equals(identifierURI)) {
                if (predicate.equals(RDF.TYPE)) {
                    ValueFactory f = SimpleValueFactory.getInstance();
                    stmts4Parser.add(f.createStatement(subject, RDF.TYPE, FOAF.ACCOUNT));
                }
            } else {
                stmts4Parser.add(st);
            }
            Agent result = AgentParser.parse(stmts4Parser, identifierURI);
            assertTrue(result.getType().equals(FOAF.AGENT));
        }
    }
}
