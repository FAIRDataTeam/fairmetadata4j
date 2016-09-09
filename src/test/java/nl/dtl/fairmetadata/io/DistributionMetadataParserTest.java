/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nl.dtl.fairmetadata.model.DatasetMetadata;
import nl.dtl.fairmetadata.model.DistributionMetadata;
import nl.dtl.fairmetadata.utils.ExampleFilesUtils;
import nl.dtl.fairmetadata.utils.vocabulary.DCAT;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.RDFS;

/**
 * Unit tests for DistributionMetadataParser.
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-09
 * @version 0.1
 */
public class DistributionMetadataParserTest {
    
    private final DistributionMetadataParser parser = 
            new DistributionMetadataParser();
    
    /**
     * Test null RDF string, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullRDFString() throws Exception {
        System.out.println("parse invalid distribution content");
        URI disURI = new URIImpl(ExampleFilesUtils.DISTRIBUTION_URI);
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        parser.parse(null, ExampleFilesUtils.DISTRIBUTION_ID, disURI, dURI, 
                ExampleFilesUtils.FILE_FORMAT);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test empty RDF string, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyRDFString() throws Exception {
        System.out.println("parse invalid distribution content");
        URI disURI = new URIImpl(ExampleFilesUtils.DISTRIBUTION_URI);
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        parser.parse("", ExampleFilesUtils.DISTRIBUTION_ID, disURI, dURI, 
                ExampleFilesUtils.FILE_FORMAT);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test null distribution ID, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullDistributionID() throws Exception {        
        System.out.println("parse invalid distribution content");
        URI disURI = new URIImpl(ExampleFilesUtils.DISTRIBUTION_URI);
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        parser.parse(ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE), null, disURI, 
                dURI, ExampleFilesUtils.FILE_FORMAT);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test empty distribution ID, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyDistributionID() throws Exception {
        System.out.println("parse invalid distribution content");
        URI disURI = new URIImpl(ExampleFilesUtils.DISTRIBUTION_URI);
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        parser.parse(ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE), "", disURI, 
                dURI, ExampleFilesUtils.FILE_FORMAT);
        fail("This test is execpeted to throw an error");
    }    
    /**
     * Test null RDFFormat, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullRDFFormat() throws Exception {
        System.out.println("parse invalid distribution content");
        URI disURI = new URIImpl(ExampleFilesUtils.DISTRIBUTION_URI);
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        parser.parse(ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE), 
                ExampleFilesUtils.DISTRIBUTION_ID, disURI, dURI, null);
        fail("This test is execpeted to throw an error");
    }
    /**
     * Test valid distribution metadata rdf file
     * @throws Exception 
     */
    @Test
    public void testParseFile() throws Exception {
        System.out.println("parse invalid distribution content");
        URI disURI = new URIImpl(ExampleFilesUtils.DISTRIBUTION_URI);
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        DistributionMetadata metadata = parser.parse(
                ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE), 
                ExampleFilesUtils.DISTRIBUTION_ID, disURI, dURI, 
                ExampleFilesUtils.FILE_FORMAT);
        assertNotNull(metadata);
    } 
    
    /**
     * Test missing distribution rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataExeception.class)
    public void testParseNoDowloadOrAccessStatement() throws Exception {
        System.out.println("Missing download or access statement");
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE, 
                        ExampleFilesUtils.DISTRIBUTION_URI);
        Iterator<Statement> it = stmts.iterator();
        List<Statement> in = new ArrayList();
        while(it.hasNext()) {
            Statement st = it.next();
            if(!st.getPredicate().equals(DCAT.ACCESS_URL) && 
                    !st.getPredicate().equals(DCAT.DOWNLOAD_URL)) {
                in.add(st);
            }
        }
        URI disURI = new URIImpl(ExampleFilesUtils.DISTRIBUTION_URI);
        parser.parse(in , disURI);
       fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test null distribution URI, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testNullDistributionURI() throws Exception {
        System.out.println("Missing dataset URL");
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE, 
                        ExampleFilesUtils.DISTRIBUTION_URI);
        parser.parse(stmts , null);
       fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test null statements, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testNullStatements() throws Exception {
        System.out.println("parse valid dataset content");
        URI disURI = new URIImpl(ExampleFilesUtils.DISTRIBUTION_URI);
        parser.parse(null, disURI);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test valid distribution rdf statements
     * @throws Exception 
     */
    @Test
    public void testParseStatements() throws Exception {
        System.out.println("parse valid distribution content");
        URI disURI = new URIImpl(ExampleFilesUtils.DISTRIBUTION_URI);
        DistributionMetadata metadata = parser.parse(
                ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE, 
                        ExampleFilesUtils.DISTRIBUTION_URI), disURI);
        assertNotNull(metadata);
    }
    
}