/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.List;
import nl.dtl.fairmetadata.model.DistributionMetadata;
import nl.dtl.fairmetadata.utils.ExampleFilesUtils;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.junit.Test;
import static org.junit.Assert.*;

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
        System.out.println("Test : Parse invalid distribution content");
        IRI disURI = ExampleFilesUtils.DISTRIBUTION_URI;
        IRI dURI = ExampleFilesUtils.DATASET_URI;
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
        System.out.println("Test : Parse invalid distribution content");
        IRI disURI = ExampleFilesUtils.DISTRIBUTION_URI;
        IRI dURI = ExampleFilesUtils.DATASET_URI;
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
        System.out.println("Test : Parse invalid distribution content");
        IRI disURI = ExampleFilesUtils.DISTRIBUTION_URI;
        IRI dURI = ExampleFilesUtils.DATASET_URI;
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
        System.out.println("Test : Parse invalid distribution content");
        IRI disURI = ExampleFilesUtils.DISTRIBUTION_URI;
        IRI dURI = ExampleFilesUtils.DATASET_URI;
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
        System.out.println("Test : Parse invalid distribution content");
        IRI disURI = ExampleFilesUtils.DISTRIBUTION_URI;
        IRI dURI = ExampleFilesUtils.DATASET_URI;
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
        System.out.println("Test : Parse invalid distribution content");
        IRI disURI = ExampleFilesUtils.DISTRIBUTION_URI;
        IRI dURI = ExampleFilesUtils.DATASET_URI;
        DistributionMetadata metadata = parser.parse(
                ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE), 
                ExampleFilesUtils.DISTRIBUTION_ID, disURI, dURI, 
                ExampleFilesUtils.FILE_FORMAT);
        assertNotNull(metadata);
    }
    
    /**
     * Test null distribution URI, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testNullDistributionURI() throws Exception {
        System.out.println("Test : Missing distribution URL");
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE, 
                        ExampleFilesUtils.DISTRIBUTION_URI.toString());
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
        System.out.println("Test : Parse valid distribution content");
        IRI disURI = ExampleFilesUtils.DISTRIBUTION_URI;
        parser.parse(null, disURI);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test valid distribution rdf statements
     * @throws Exception 
     */
    @Test
    public void testParseStatements() throws Exception {
        System.out.println("Test : Parse valid distribution content");
        IRI disURI = ExampleFilesUtils.DISTRIBUTION_URI;
        DistributionMetadata metadata = parser.parse(
                ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE, 
                        ExampleFilesUtils.DISTRIBUTION_URI.toString()), disURI);
        assertNotNull(metadata);
    }
    
    /**
     * Test valid dataset metadata rdf file, with no base
     * @throws Exception 
     */
    @Test
    public void testParseFileWithNoBase() throws Exception {
        System.out.println("Test : Parse valid distribution "
                + "content with no base uri");
        DistributionMetadata metadata = parser.parse(
                ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE), null,
                ExampleFilesUtils.FILE_FORMAT);
        assertNotNull(metadata);
    }
    
}