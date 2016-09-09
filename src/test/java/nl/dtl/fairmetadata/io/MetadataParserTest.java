/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nl.dtl.fairmetadata.model.Metadata;
import nl.dtl.fairmetadata.utils.ExampleFilesUtils;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.RDFS;

/**
 * Unit tests for MetadataParser.
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-09
 * @version 0.1
 */
public class MetadataParserTest {
    
    private final MetadataParser parser = new MetadataParser() {
        @Override
        protected Metadata createMetadata() {
            return new Metadata();
        }
    };
    
     /**
     * Test missing rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataExeception.class)
    public void testParseNoVersionStatement() throws Exception {
        System.out.println("Missing version statement");
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI);
        Iterator<Statement> it = stmts.iterator();
        while(it.hasNext()) {
            Statement st = it.next();
            if(st.getPredicate().equals(DCTERMS.HAS_VERSION)) {
                stmts.remove(st);
                break;
            }
        }
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        parser.parse(stmts , fURI);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test missing rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataExeception.class)
    public void testParseNoTitleStatement() throws Exception {
        System.out.println("Missing title statement");
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI);
        Iterator<Statement> it = stmts.iterator();
        List<Statement> in = new ArrayList();
        while(it.hasNext()) {
            Statement st = it.next();
            if(!st.getPredicate().equals(RDFS.LABEL) && 
                    !st.getPredicate().equals(DCTERMS.TITLE)) {
                in.add(st);
            }
        }
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        parser.parse(in , fURI);       
        fail("This test is execpeted to throw an error");
    }
    
}
