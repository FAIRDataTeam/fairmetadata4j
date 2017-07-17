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
import static org.junit.Assert.fail;
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

    /**
     * Test null publisher uri, this test is expected to throw exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullPublisherUri() throws Exception {
        System.out.println("Test : Parse null publisher uri");
        List<Statement> statements = ExampleFilesUtils.
                getFileContentAsStatements(ExampleFilesUtils.FDP_METADATA_FILE,
                        ExampleFilesUtils.FDP_URI.toString());
        IRI agentURI = null;
        AgentParser.parse(statements, agentURI);
        fail("This test is execpeted to throw an error");
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
        fail("This test is execpeted to throw an error");
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
        fail("This test is execpeted to throw an error");
    }

    /**
     * Test valid agent uri.
     */
    @Test
    public void testValidMetadataID() {
        System.out.println("Parse valid publisher ID");
        List<Statement> statements = ExampleFilesUtils.
                getFileContentAsStatements(ExampleFilesUtils.FDP_METADATA_FILE,
                        ExampleFilesUtils.FDP_URI.toString());
        IRI agentURI = ExampleFilesUtils.FDP_METADATA_PUBLISHER_URI;
        Agent result = AgentParser.parse(statements, agentURI);
        assertNotNull(result);
    }

    /**
     * Test valid agent uri.
     */
    @Test
    public void testValidPublisherUri() {
        System.out.println("Parse fdp publisher uri");
        List<Statement> statements = ExampleFilesUtils.
                getFileContentAsStatements(ExampleFilesUtils.FDP_METADATA_FILE,
                        ExampleFilesUtils.FDP_URI.toString());
        IRI identifierURI = ExampleFilesUtils.FDP_METADATA_PUBLISHER_URI;
        Agent result = AgentParser.parse(statements, identifierURI);
        assertNotNull(result);
    }

    /**
     * Test valid wrong publisher type.
     */
    @Test
    public void testInValidPublishertype() {
        System.out.println("Parse invalid publisher type");
        List<Statement> statements = ExampleFilesUtils.
                getFileContentAsStatements(ExampleFilesUtils.FDP_METADATA_FILE,
                        ExampleFilesUtils.FDP_URI.toString());
        IRI identifierURI = ExampleFilesUtils.FDP_METADATA_PUBLISHER_URI;
        List<Statement> stmts4Parser = new ArrayList();

        for (Statement st : statements) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            if (subject.equals(identifierURI)) {
                if (predicate.equals(RDF.TYPE)) {
                    ValueFactory f = SimpleValueFactory.getInstance();
                    stmts4Parser.add(f.createStatement(subject, RDF.TYPE, 
                            FOAF.ACCOUNT));
                }
            } else {
                stmts4Parser.add(st);
            }
            Agent result = AgentParser.parse(stmts4Parser, identifierURI);
            assertTrue(result.getType().equals(FOAF.AGENT));
        }
    }  
}
